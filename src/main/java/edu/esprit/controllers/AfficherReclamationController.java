
package edu.esprit.controllers;

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
    private TableColumn<?, ?> typeReclam;

    @FXML
    private Button verifier;

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
        ReclamationService sr = new ReclamationService();
        Reclamation r = (Reclamation) tableauReclam.getSelectionModel().getSelectedItem();
        sr.supprimer(r.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            if(JOptionPane.showConfirmDialog(null,"attention vous allez supprimer votre reclamation,est ce que tu et sure?"
                    ,"supprimer reclamation",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)

                if(!r.getDescription().isEmpty()){

                    alert.setContentText("Votre réclamation a ete bien supprime");
                    JOptionPane.showMessageDialog(null,"reclamation supprime");
                }//ca est pour recharger la list des stagiaire
                else { JOptionPane.showMessageDialog(null,"veuillez remplire le champ id !");}

        }catch (Exception e){JOptionPane.showMessageDialog(null,"erreur de supprimer \n"+e.getMessage());}

    }

    @FXML
    void refreshReclamation(ActionEvent event) {

        ReclamationService sr = new ReclamationService();
        List<Reclamation> reclam = sr.refreshReclam();
        myList = FXCollections.observableList(reclam);
        tableauReclam.setItems(myList);

        descReclam.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeReclam.setCellValueFactory(new PropertyValueFactory<>("type"));


        etatReclam.setCellValueFactory(new PropertyValueFactory<>("etat"));
        DateReclam.setCellValueFactory(new PropertyValueFactory<>("date_creation"));

        tableauReclam.setItems(myList);

    }


    @FXML
    void updateReclamation(ActionEvent event) {
        Reclamation r = tableauReclam.getSelectionModel().getSelectedItem();

        if(r == null) {
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

                ModifierRecalationController mr = loader.getController();
                mr.setData(r.getId(), r.getDescription(), r.getType());

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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load image from resources
        Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        circle.setFill(new ImagePattern(img));
            // TODO
        try {
            afficherReclam();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}
