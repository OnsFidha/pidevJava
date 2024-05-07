package edu.esprit.controllers;

import edu.esprit.utils.SessionManager;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import edu.esprit.entities.Reclamation;
import edu.esprit.service.ReclamationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


public class AfficherReclamationAdmin {
    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();



    @FXML
    private Label logedUsernamee;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnRecherche;

    @FXML
    private TableColumn<?, ?> DateReclam;

    @FXML
    private Button bouttonRepondre;

    @FXML
    private Button bouttonStat;


    @FXML
    private Circle circle;

    @FXML
    private TableColumn<?, ?> descReclam;

    @FXML
    private TableColumn<Reclamation, Boolean> etatReclam;

    @FXML
    private TextField rechercheText;

    @FXML
    private TableView<Reclamation> tableauReclam;

    @FXML
    private TableColumn<?, ?> typeReclam;

    @FXML
    private Button bouttonPdf;

    static Reclamation selected;

    @FXML
    void PdfReclamation(ActionEvent event) {
        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a content stream for writing to the PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Load your logo image
            File logoFile = new File("C:\\Users\\HP\\IdeaProjects\\ons+mehdi+sana\\src\\main\\resources\\img\\Copie_de_Beige_Watercolor_Project_Presentation-removebg-preview.png");
            PDImageXObject logoImage = PDImageXObject.createFromFileByContent(logoFile, document);

            // Draw your logo on the page
            // Draw your logo on the page
            float logoWidth = 100; // Adjust this value to your logo's width
            float logoHeight = 50; // Adjust this value to your logo's height
            float startX = page.getMediaBox().getWidth() - logoWidth - 10; // 10 is the margin from the right
            float startY = page.getMediaBox().getHeight() - logoHeight - 10; // 10 is the margin from the top
            contentStream.drawImage(logoImage, startX, startY, logoWidth, logoHeight);


            // Set font and font size
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            // Set custom colors

            contentStream.beginText();
            contentStream.newLineAtOffset(200, 700);
            contentStream.showText("Liste des Reclamations");
            contentStream.newLineAtOffset(0, -20);

            // Write each reclamation to the PDF
            ObservableList<Reclamation> reclamations = tableauReclam.getItems();
            for (Reclamation reclamation : reclamations) {

                // Convert java.sql.Date to java.util.Date
                java.util.Date utilDate = new java.util.Date(reclamation.getDate_creation().getTime());
                Instant instant = utilDate.toInstant();

                // Convert Instant to LocalDate
                LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();

                // Format the LocalDate using DateTimeFormatter with the desired pattern
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Type: " + reclamation.getType());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Description: " + reclamation.getDescription());
                contentStream.newLineAtOffset(0, -20);
                String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                contentStream.showText("Date: " + formattedDate);
                contentStream.newLineAtOffset(0, -20);
                // Set the text color based on the reclamation's state
                if (reclamation.isEtat()) {
                    // If treated, set text color to black
                    contentStream.setNonStrokingColor(0, 0, 0); // Black color
                } else {
                    // If untreated, set text color to red
                    contentStream.setNonStrokingColor(1, 0, 0); // Red color
                }
                contentStream.showText("État: " + (reclamation.isEtat() ? "Traité" : "Non traité"));
                contentStream.newLineAtOffset(0, -20);
                contentStream.newLineAtOffset(0, -10);

            }

            // End text writing
            contentStream.endText();

            contentStream.close();

            // Save the document to a file
            document.save("Reclamations.pdf");
            document.close();

            // Inform the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Export");
            alert.setHeaderText(null);
            alert.setContentText("PDF generated successfully.");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("PDF Export Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while generating PDF.");
            alert.showAndWait();
        }

    }


    @FXML
    void StatReclamation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/stat.fxml"));
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
    void chercherReclam(ActionEvent event) {
        String recherche = rechercheText.getText();
        ReclamationService sr = new ReclamationService();
        // Efface les éléments précédents de la TableView
        tableauReclam.getItems().clear();
        // Effectue la recherche et récupère les réclamations trouvées
        List<Reclamation> reclamations = sr.RechrecheRec(recherche);
        // Ajoute les réclamations trouvées à la TableView
        tableauReclam.getItems().addAll(reclamations);

    }

    @FXML
    void initialize() {
        logedUsernamee.setText(nameP);
        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        circle.setFill(new ImagePattern(image));


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
        ObservableList<Reclamation> myList = FXCollections.observableList(reclam);
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
    void repondreReclamation(ActionEvent event) {

        // Récupérer la réclamation sélectionnée
        selected = tableauReclam.getSelectionModel().getSelectedItem();

        // Vérifier si une réclamation est sélectionnée
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReponse.fxml"));
                Parent root = loader.load();

                //AjouterReponseController mr = loader.getController();
                //mr.setData(selected.getId(), selected.getDescription(), selected.getType(),selected.isEtat());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Répondre à une Réclamation");
                stage.show();

                // Cacher la fenêtre actuelle
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        } else {
            // Afficher un message si aucune réclamation n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Réclamation Sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réclamation pour répondre.");
            alert.showAndWait();
        }
    }

    @FXML
    void reponseSideBar(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReponseAdmin.fxml"));
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
    void TrierParType(ActionEvent event) {
        ObservableList<Reclamation> reclamations = tableauReclam.getItems();
        tableauReclam.setItems(reclamations.stream()
                .sorted(Comparator.comparing(Reclamation::getType))
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }


    @FXML
    void trierParDate(ActionEvent event) {
        ObservableList<Reclamation> reclamations = tableauReclam.getItems();
        tableauReclam.setItems(reclamations.stream()
                .sorted(Comparator.comparing(Reclamation::getDate_creation))
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    @FXML
    void trierParEtat(ActionEvent event) {
        ObservableList<Reclamation> reclamations = tableauReclam.getItems();
        tableauReclam.setItems(reclamations.stream()
                .sorted(Comparator.comparing(Reclamation::isEtat))
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    @FXML
    void RetourEvent(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/baseAdminEvent.fxml"));
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
    void RetourProduit(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/AdminTemplate.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminListPub.fxml"));
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
    void RetourUtilisateur(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


