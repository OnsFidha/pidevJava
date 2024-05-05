package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.service.ReclamationService;
import edu.esprit.utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class ModifierRecalationController  implements Initializable {
    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();

    @FXML
    private Label logedUsernamee;


    @FXML
        private Button Reclamlistbtn;

        @FXML
        private Circle circle;

        @FXML
        private ComboBox<String> comb;

        @FXML
        private TextArea description;

        @FXML
        private Button modifierbtn;

        Reclamation rec=new Reclamation();

    @FXML
    void ModifierReclam(ActionEvent event) {
        if (rec == null) {
            System.out.println("Choisir une réclamation");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modifier réclamation");
            alert.setHeaderText(null);
            alert.setContentText("La réclamation n'est pas modifiée!");
            alert.showAndWait();
        } else {
            rec.setDescription( description.getText());
            rec.setType(comb.getSelectionModel().getSelectedItem());
            rec.setEtat(false);
            // Appel de la méthode modifier avec les champs à mettre à jour
            ReclamationService sr = new ReclamationService();
            try {
                sr.modifier(rec);
                System.out.println("Modification terminée");
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Modification terminée avec succès.");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Votre réclamation a été modifiée avec succès.");
                successAlert.showAndWait();
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la modification: " + ex.getMessage());
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur lors de la modification.");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Une erreur s'est produite lors de la modification de votre réclamation.");
                errorAlert.showAndWait();
            }
        }
    }


    @FXML
        void Selecttype(ActionEvent event) {
            String selectedItem = comb.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + selectedItem);

        }

        @FXML
        void rrtourListReclam(ActionEvent event) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("artistool - list Reclamation");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load image from resources
        //Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        //circle.setFill(new ImagePattern(img));
        logedUsernamee.setText(nameP);
        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        circle.setFill(new ImagePattern(image));


        ObservableList<String> items = FXCollections.observableArrayList(
                "Publication non visible sur la plateforme",
                "Difficulté à publier du contenu",
                "Difficulté à trouver des collaborations appropriées",
                "Problèmes de communication avec les collaborateurs",
                "Autre"
        );
        comb.setItems(items);

    }

    void setData(int id, String sub , String T) {
        rec.setId(id);
        description.setText(sub);
        comb.setAccessibleText(T);





    }
}
