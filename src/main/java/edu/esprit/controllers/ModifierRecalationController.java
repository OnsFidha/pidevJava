package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.service.ReclamationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class ModifierRecalationController  implements Initializable {

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
                alert.setContentText("La réclamation n'est pas modifié!");

                alert.showAndWait();
            }else {

                rec.setDescription(description.getText());
                rec.setType(comb.getSelectionModel().getSelectedItem());
                ReclamationService sr = new ReclamationService();
                try{
                    sr.modifier(rec);
                    System.out.println("ok");}
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                System.out.println("Modification terminé");}



            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification terminée avec succès.");
            alert.setHeaderText(null);
            alert.setContentText("Votre réclamation a été modifié avec succés.");
            alert.showAndWait();

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
        Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        circle.setFill(new ImagePattern(img));

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
