
package edu.esprit.controllers;

import edu.esprit.utils.SessionManager;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
        import edu.esprit.entities.Reclamation;
        import edu.esprit.service.ReclamationService;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.image.Image;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
        import javafx.scene.paint.ImagePattern;
        import javafx.scene.shape.Circle;
        import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.util.List;
        import java.util.ResourceBundle;
        import javax.swing.JOptionPane;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;


public class AfficherReclamationController implements Initializable {
    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();

    int id=SessionManager.getId_user();

    @FXML
    private GridPane reclamationsContainer;

    @FXML
    private Label logedUsernamee;






    @FXML
    private TableView<Reclamation> tableauReclam;
    @FXML
    private TableColumn<?, ?> DateReclam;
    ObservableList myList ;
    @FXML
    private Button ModifierReclam;

    @FXML
    private Button addReclam;

    @FXML
    private Circle circle;

    @FXML
    private TableColumn<?, ?> descReclam;

    @FXML
    private TableColumn<Reclamation, Boolean> etatReclam;

    @FXML
    private Button refresh;

    @FXML
    private Button supprimerReclam;
    @FXML
    private HBox home;
    @FXML
    private TableColumn<?, ?> typeReclam;

    @FXML
    private Button verifier;
    public static Reclamation selectedReclamation;
    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getHome();
            }
        };

        // Ajouter l'EventHandler au HBox
        home.setOnMouseClicked(clickHandler);
        logedUsernamee.setText(nameP);
        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        circle.setFill(new ImagePattern(image));


        // Initialize ReclamationService
        ReclamationService sr = new ReclamationService();
        List<Reclamation> reclam;
        try {
            // Get all reclamations from the service
            reclam = sr.getAll2(id);

            int column = 0;
            int row = 0;

            // Iterate over the list of reclamations
            for (Reclamation reclamation : reclam) {
                // Load the reclam card FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReclamCard.fxml"));
                AnchorPane card = loader.load();


                // Pass the data to the controller of the card
                ReclamCardController controller = loader.getController();
                controller.setData(reclamation);


                // Add the card to the GridPane container
                reclamationsContainer.add(card, column, row);

                // Add mouse event handler to the card
                card.setOnMouseClicked(event -> {
                    // Set the color of all cards to white
                    for (Node node : reclamationsContainer.getChildren()) {
                        node.setStyle("-fx-background-color: white;");
                    }

                    // Highlight the selected card
                    card.setStyle("-fx-background-color: lightblue;");

                    // Retrieve the corresponding reclamation object
                    Reclamation selectedReclamation = (Reclamation) card.getUserData();

                    // Perform action based on the selected card
                    // For example:
                    // If event.getClickCount() == 2, it's a double click
                    // You can perform an action here, like opening a new window
                    // Or you can check the button pressed if it's a single click
                    // (event.isPrimaryButtonDown() for left click, event.isSecondaryButtonDown() for right click)
                });

                // Set the reclamation object as user data for the card
                card.setUserData(reclamation);

                // Increment row and reset column if needed
                column++;
                if (column == 1) {
                    column = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    private void getHome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
        try {

            Parent root = loader.load();
            circle.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void mailer(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EnvoyerEmail.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool - Ajout Reclamation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void addReclamation(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool - Ajout Reclamation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteReclamation(ActionEvent event) throws SQLException {
        // Récupérer la carte sélectionnée dans le GridPane
        AnchorPane selectedCard = (AnchorPane) reclamationsContainer.getChildren().stream()
                .filter(node -> node.getStyle().contains("-fx-background-color: lightblue;"))
                .findFirst().orElse(null);

        if (selectedCard != null) {
            // Récupérer la réclamation associée à la carte
            Reclamation selectedReclamation = (Reclamation) selectedCard.getUserData();

            // Supprimer la réclamation
            ReclamationService sr = new ReclamationService();
            sr.supprimer(selectedReclamation.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            try {
                if(JOptionPane.showConfirmDialog(null,"Attention, vous allez supprimer votre réclamation. Êtes-vous sûr ?"
                        ,"Supprimer réclamation",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)

                    if(!selectedReclamation.getDescription().isEmpty()){

                        alert.setContentText("Votre réclamation a été supprimée avec succès.");
                        JOptionPane.showMessageDialog(null,"Réclamation supprimée");
                    } else {
                        JOptionPane.showMessageDialog(null,"Veuillez remplir le champ ID !");
                    }
            } catch (Exception e){
                JOptionPane.showMessageDialog(null,"Erreur lors de la suppression :\n" + e.getMessage());
            }
        } else {
            // Aucune carte sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune réclamation sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réclamation à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    void refreshReclamation(ActionEvent event) {

        ReclamationService sr = new ReclamationService();
        List<Reclamation> reclam;
        try {
            // Get all reclamations from the service
            reclam = sr.getAll2(id);

            int column = 0;
            int row = 0;

            // Iterate over the list of reclamations
            for (Reclamation reclamation : reclam) {
                // Load the reclam card FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReclamCard.fxml"));
                AnchorPane card = loader.load();


                // Pass the data to the controller of the card
                ReclamCardController controller = loader.getController();
                controller.setData(reclamation);


                // Add the card to the GridPane container
                reclamationsContainer.add(card, column, row);

                // Add mouse event handler to the card
                card.setOnMouseClicked(MouseEvent -> {
                    // Set the color of all cards to white
                    for (Node node : reclamationsContainer.getChildren()) {
                        node.setStyle("-fx-background-color: white;");
                    }

                    // Highlight the selected card
                    card.setStyle("-fx-background-color: lightblue;");

                    // Retrieve the corresponding reclamation object
                    Reclamation selectedReclamation = (Reclamation) card.getUserData();

                    // Perform action based on the selected card
                    // For example:
                    // If event.getClickCount() == 2, it's a double click
                    // You can perform an action here, like opening a new window
                    // Or you can check the button pressed if it's a single click
                    // (event.isPrimaryButtonDown() for left click, event.isSecondaryButtonDown() for right click)
                });

                // Set the reclamation object as user data for the card
                card.setUserData(reclamation);

                // Increment row and reset column if needed
                column++;
                if (column == 1) {
                    column = 0;
                    row++;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void updateReclamation(ActionEvent event) {
        // Récupérer la carte sélectionnée dans le GridPane
        AnchorPane selectedCard = (AnchorPane) reclamationsContainer.getChildren().stream()
                .filter(node -> node.getStyle().contains("-fx-background-color: lightblue;"))
                .findFirst().orElse(null);

        if(selectedCard == null) {
            System.out.println("Aucune réclamation sélectionnée");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucune réclamation sélectionnée");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamation.fxml"));
                Parent root = loader.load();

                // Récupérer la réclamation associée à la carte
                Reclamation selectedReclamation = (Reclamation) selectedCard.getUserData();



                ModifierRecalationController mr = loader.getController();
                mr.setData(selectedReclamation.getId(), selectedReclamation.getDescription(), selectedReclamation.getType());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Modifier Réclamation");
                stage.show();

                // Hide the current window
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }



    @FXML
    void verifierReponseReclamation(ActionEvent event) {
        // Récupérer la carte sélectionnée dans le GridPane
        AnchorPane selectedCard = (AnchorPane) reclamationsContainer.getChildren().stream()
                .filter(node -> node.getStyle().contains("-fx-background-color: lightblue;"))
                .findFirst().orElse(null);

        // Vérifier si aucune carte n'a été sélectionnée
        if (selectedCard == null) {
            System.out.println("Aucune réclamation sélectionnée");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucune réclamation sélectionnée");
            alert.showAndWait();
            return; // Sortir de la méthode car aucune réclamation n'a été sélectionnée
        }

        // Récupérer la réclamation associée à la carte
        Reclamation selectedReclamation = (Reclamation) selectedCard.getUserData();
        System.out.println(selectedReclamation);

        // Charger le contrôleur ReponseClientController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReponseClient.fxml"));

        try {
            Parent root1 = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root1);
            stage.setScene(scene);
            stage.show();

            // Mettre à jour la réclamation sélectionnée dans le contrôleur ReponseClientController
            ReponseClientController controller = loader.getController();
            controller.setSelectedReclamation(selectedReclamation);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }







    private void afficherReclam() throws SQLException {
      /* ServiceReclamation sr = new ServiceReclamation();
        ObservableList<Reclamation> o = FXCollections.observableArrayList(sr.afficherReclamation());*/
        ReclamationService sr = new ReclamationService();
        List<Reclamation> reclam = sr.getAll();
        myList = FXCollections.observableList(reclam);
        tableauReclam.setItems(myList);

        descReclam.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeReclam.setCellValueFactory(new PropertyValueFactory<>("type"));


        etatReclam.setCellValueFactory(new PropertyValueFactory<>("etat"));
        // Définition de la cellFactory pour la colonne d'état
        // Configuration de la cellule d'état
        etatReclam.setCellFactory(column -> new TableCell<Reclamation, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Traité" : "Non traité");
                }
            }
        });

        DateReclam.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

    }


    @FXML
    void RetourProduit(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front/produits/FrontTemplate.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void RetourPublication(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void retourEvent(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenements.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void retourUtilisateur(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
