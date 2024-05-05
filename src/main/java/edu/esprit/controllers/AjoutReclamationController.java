package edu.esprit.controllers;

import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import edu.esprit.entities.Reclamation;
import edu.esprit.service.ReclamationService;


import edu.esprit.utils.JavaMailUtil;
import edu.esprit.utils.SessionManager;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.math.BigDecimal;
import java.util.concurrent.Future;

import com.microsoft.cognitiveservices.speech.*;
import javafx.concurrent.Task;

public class AjoutReclamationController implements Initializable {
    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();




    @FXML
    private Label logedUsernamee;

    int id= SessionManager.getId_user();

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
    public static final String AUTH_TOKEN = "144c7ae996f32cd5efd81738130912ef";

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
        //Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        //circle.setFill(new ImagePattern(img));
        logedUsernamee.setText(nameP);
        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        circle.setFill(new ImagePattern(image));



        comb.setItems(FXCollections.observableArrayList("Publication non visible sur la plateforme",
                "Difficulté à publier du contenu",
                "Difficulté à trouver des collaborations appropriées",
                "Problèmes de communication avec les collaborateurs",
                "Autre"));
    }


    public Boolean ValidateFields() {
        if (description.getText().isEmpty() || comb.getSelectionModel().isEmpty()  ) {
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
    void ajouterReclam(ActionEvent event) throws Exception {

        if(ValidateFields() ){
            Reclamation r = new Reclamation();
            r.setDescription(description.getText());
            r.setType(comb.getSelectionModel().getSelectedItem());

            // Initialiser la date de création à la date actuelle
            r.setDate_creation(new Date());
            r.setId_user(id);

            ReclamationService pst = new ReclamationService();
            pst.ajouter(r);

            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("whatsapp:+21624171676"),
                            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                            SessionManager.getName() +" a envoyee une Reclamation , vous pouvez le contacter sur : "+ SessionManager.getPhone())
                    .create();

            System.out.println("msg whatsapp envoyee");
            new Thread(()->{
                try {
                    JavaMailUtil.sendEmail( SessionManager.getEmail(), "Bonjour cher Artiste  ,  \n Votre demande sera prise en compte et nous vous répondrons dans les meilleurs délais. \n Vous serez notifiés via une maill les details de traitement de votre reclamation \n Merci !! ");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }).start();




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

    @FXML
    void stt(ActionEvent event) {
        Task<String> recognitionTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                SpeechConfig speechConfig = SpeechConfig.fromSubscription("9060f63ba0654e7b8533abfa8e407a6e", "westeurope");

                try (SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, AudioConfig.fromDefaultMicrophoneInput())) {
                    System.out.println("Speak into your microphone.");

                    Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
                    SpeechRecognitionResult result = task.get();

                    if (result.getReason() == ResultReason.Canceled) {
                        System.out.println("Cancellation detected.");
                        return null;
                    } else if (result.getReason() == ResultReason.NoMatch) {
                        System.out.println("No speech recognized.");
                        return null;
                    } else {
                        String recognizedText = result.getText();
                        return recognizedText;
                    }
                }
            }
        };

        recognitionTask.setOnSucceeded(event1 -> {
            String transcribedText = recognitionTask.getValue();
            if (transcribedText != null) {
                description.setText(transcribedText); // Update the UI element with the transcribed text
            }
        });

        recognitionTask.setOnFailed(event1 -> {
            Throwable exception = recognitionTask.getException();
            System.err.println("Speech recognition failed: " + exception.getMessage());
        });

        new Thread(recognitionTask).start();
    }

}


