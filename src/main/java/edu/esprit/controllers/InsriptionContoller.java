package edu.esprit.controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceUtilisateur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.sql.Connection;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class InsriptionContoller {
    @FXML
    private TextField nomreg;
    @FXML
    private TextField prenomreg;
    @FXML
    private TextField emailreg;
    @FXML
    private TextField mdpreg;
    @FXML
    private TextField numtelreg;
    @FXML
    private TextField imagereg;
    @FXML
    private Label reginfo;
    @FXML
    private Button imagebtn;

    private final ServiceUtilisateur UserS = new ServiceUtilisateur();
    private Connection cnx;
    public static final String ACCOUNT_SID = "AC5cb6bfb17d0f2b520920f31cfb9ac577";
    //public static final String ACCOUNT_SID = "VA27841fdfc632f93bf590271ee938fb50";
    public static final String AUTH_TOKEN = "89b7f4c7d183f01a284696af0d5edc41";
    public static final String TWILIO_PHONE_NUMBER = "+14159916143";
    public String verificationCode;

    public String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
    private void sendVerificationCode(String toPhoneNumber, String code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String fullPhoneNumber = "+216" + toPhoneNumber;
        Message.creator(
                new PhoneNumber(fullPhoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                "Your verification code is: " + code
        ).create();
    }

    @FXML
    public void inscription(javafx.event.ActionEvent actionEvent) {
        int NUMTEL = Integer.parseInt(numtelreg.getText());
        String NOM = nomreg.getText();
        String PRENOM = prenomreg.getText();
        String EMAIL = emailreg.getText();
        String MDP = mdpreg.getText();
        String IMAGE = imagereg.getText();
        try {
            if (!UserS.isValidEmail(emailreg.getText())) {
                reginfo.setText("Email est invalide");
            } else if (!(UserS.isValidPhoneNumber(Integer.parseInt(numtelreg.getText())))) {
                reginfo.setText("N° Telephone est invalide");
            } else if (UserS.checkUserExists(EMAIL)) {
                reginfo.setText("Email déjà existe");
            } else {
                this.verificationCode = generateVerificationCode();
                sendVerificationCode(String.valueOf(NUMTEL), this.verificationCode);
                boolean isCodeVerified = false;
                while (!isCodeVerified) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Verification Code");
                    dialog.setHeaderText("Entrez le code de vérification envoyé à votre téléphone:");
                    dialog.setContentText("Code:");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        String inputCode = result.get();
                        if (inputCode.equals(this.verificationCode)) {

                            MessageDigest md = MessageDigest.getInstance("SHA-256");
                            byte[] hash = md.digest(mdpreg.getText().getBytes());
                            //String mdpHash = Utility.toHexString(hash);
                            StringBuilder hexString = new StringBuilder();
                            for (byte hashByte : hash) {
                                String hex = Integer.toHexString(0xff & hashByte);
                                if (hex.length() == 1) {
                                    hexString.append('0');
                                }
                                hexString.append(hex);
                            }
                            String mdpHash = hexString.toString();
                            isCodeVerified = true;
                            UserS.Add(new Utilisateur(0, NOM, PRENOM, EMAIL, mdpHash, NUMTEL,"User", IMAGE));
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                                Parent root = loader.load();
                                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setTitle("artistool - Connection");
                                stage.show();
                            } catch (IOException e) {
                                System.out.println(e.getMessage());;
                            }
                        } else {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setHeaderText("Code est incorrect");
                            errorAlert.setContentText("Le code de vérification que vous avez entré est incorrect. Veuillez réessayer.");
                            errorAlert.showAndWait();
                        }
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    private void uploadImage(javafx.event.ActionEvent actionEvent) {
        String imagePath = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        Stage stage = (Stage) nomreg.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                Path destinationFolder = Paths.get("src/main/resources/assets/uploads");
                if (!Files.exists(destinationFolder)) {
                    Files.createDirectories(destinationFolder);
                }
                String fileName = UUID.randomUUID().toString() + "_" + selectedFile.getName();
                Path destinationPath = destinationFolder.resolve(fileName);
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                imagePath = destinationPath.toString();
                System.out.println("Image uploaded successfully: " + imagePath);
                imagereg.setText(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void cnscene(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool - Connection");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
