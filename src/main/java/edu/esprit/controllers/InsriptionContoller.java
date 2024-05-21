package edu.esprit.controllers;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import com.github.sarxos.webcam.Webcam;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceUtilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.sql.Connection;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;
public class InsriptionContoller {
    @FXML
    private TextField namereg;
    @FXML
    private TextField prenamereg;
    @FXML
    private TextField emailreg;
    @FXML
    private TextField mdpreg;
    @FXML
    private TextField phonereg;
    @FXML
    private TextField imagereg;
    @FXML
    private Label reginfo;
    @FXML
    private Button imagebtn;
    @FXML
    public ImageView imagepdp;
    @FXML
    private Button TakePic;
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
        int PHONE = Integer.parseInt(phonereg.getText());
        String NAME = namereg.getText();
        String PRENAME = prenamereg.getText();
        String EMAIL = emailreg.getText();
        String MDP = mdpreg.getText();
        String IMAGE = imagereg.getText();
        try {
            if (!UserS.isValidEmail(emailreg.getText())) {
                reginfo.setText("Email est invalide");
            } else if (!(UserS.isValidPhoneNumber(Integer.parseInt(phonereg.getText())))) {
                reginfo.setText("N° Telephone est invalide");
            } else if (UserS.checkUserExists(EMAIL)) {
                //chercher si l'email existe deja
                reginfo.setText("Email déjà existe");
            } else if (!UserS.isValidPassword(mdpreg.getText())) {
                //chercher si le mot depasse est faile
                reginfo.setText("mot de passe faible");
            } else {
                this.verificationCode = generateVerificationCode();
                sendVerificationCode(String.valueOf(PHONE), this.verificationCode);
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
                            // Hasher le mot de passe avec Bcrypt
                            String hashedPassword = BCrypt.hashpw(MDP, BCrypt.gensalt());

                            isCodeVerified = true;
                            UserS.Add(new Utilisateur(0, NAME, PRENAME, EMAIL, hashedPassword, PHONE,"[\"User\"]", IMAGE));
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                                Parent root = loader.load();
                                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setTitle("artistool - Connection");
                                stage.show();
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
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
        Stage stage = (Stage) namereg.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                Path destinationFolder = Paths.get("src/main/resources/assets/uploads");
                if (!Files.exists(destinationFolder)) {
                    Files.createDirectories(destinationFolder);
                }
                String fileName = selectedFile.getName(); // Obtient le nom du fichier avec l'extension
                Path destinationPath = destinationFolder.resolve(fileName);
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                imagePath = destinationPath.getFileName().toString(); // Obtient le nom du fichier sans le chemin complet
                System.out.println("Image uploaded successfully: " + imagePath);
                imagereg.setText(imagePath); // Seulement le nom du fichier sans le chemin complet
                if (imagePath != null) {
                    try {
                        // Utilisez le chemin complet pour afficher l'image
                        FileInputStream inputStream = new FileInputStream(destinationPath.toFile());
                        Image image = new Image(inputStream);
                        imagepdp.setImage(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void TakePic(ActionEvent event) throws IOException {

        Webcam webcam = Webcam.getDefault();
        webcam.open();

// get image
        BufferedImage image = webcam.getImage();

// Save image to PNG file in the first location
        File file1 = new File("src/main/resources/assets/uploads/1.png");
        ImageIO.write(image, "PNG", file1);

// Copy the file to the second location
        Path destinationFolder = Paths.get("C:/Users/HP/Desktop/projetIntegration/pidev/public/uplaods");
        Files.createDirectories(destinationFolder);
        Path destinationPath = destinationFolder.resolve("1.png");
        Files.copy(file1.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        webcam.close();
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
