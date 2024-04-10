package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.service.ReclamationService;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.math.BigDecimal;

public class AjoutReclamationController implements Initializable {



    @FXML
    private Button Reclamlistbtn;

    @FXML
    private Circle circle;


    @FXML
    private TextArea description;

    @FXML
    private Button enregistrerbtn;



    @FXML
    private ComboBox<String> comb;

    public static final String ACCOUNT_SID = "AC0caafa1f675f3f429a7df499c1e754b7";
    public static final String AUTH_TOKEN = "***********************************";

    @FXML
    void Selecttype(ActionEvent event) {
        String selectedItem = comb.getSelectionModel().getSelectedItem();
        System.out.println("Selected item: " + selectedItem);

    }

    @FXML
    void Select(ActionEvent event) {
        String selectedItem = comb.getSelectionModel().getSelectedItem();
        System.out.println("Selected item: " + selectedItem);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load image from resources
        Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        circle.setFill(new ImagePattern(img));


        comb.setItems(FXCollections.observableArrayList("Publication non visible sur la plateforme",
                "Difficulté à publier du contenu",
                "Difficulté à trouver des collaborations appropriées",
                "Problèmes de communication avec les collaborateurs",
                "Autre"));
    }


    public Boolean ValidateFields() {
        if (description.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate fields");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Into The Fields");
            alert.showAndWait();
            return false;
        }

        return true;

    }
    @FXML
    void ajouterReclam(ActionEvent event) throws SQLException {

        if(ValidateFields() ){
            Reclamation r = new Reclamation();
            r.setDescription(description.getText());
            r.setType(comb.getSelectionModel().getSelectedItem());

            // Initialiser la date de création à la date actuelle
            r.setDate_creation(new Date());

            ReclamationService pst = new ReclamationService();
            pst.ajouter(r);

            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("whatsapp:+21624171676"),
                            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                            "Sana khiari a envoyee une Reclamation , vous pouvez le contacter sur : 24171676")
                    .create();

            System.out.println("msg whatsapp envoyee");




            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("réclamer");
            alert.setHeaderText(null);
            alert.setContentText("Votre réclamation a ete bien ajoute");
            alert.showAndWait();
        }

    }

    @FXML
    void rrtourListReclam(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
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
}


