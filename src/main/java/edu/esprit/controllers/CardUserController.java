package edu.esprit.controllers;

import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CardUserController {
    @FXML
    private Label cardemail;
    @FXML
    private ImageView cardimage;
    @FXML
    private Label cardnameprename;
    @FXML
    private Label cardnumtel;
    @FXML
    private Label cardrole;
    @FXML
    private Pane Card;
    private final ServiceUtilisateur UserS = new ServiceUtilisateur();

    int uid,unumtel;
    String unom, uprenom, uemail, umdp, urole,uimage;

    private String[] colors = {"#CDB4DB", "#FFC8DD", "#FFAFCC", "#BDE0FE", "#A2D2FF",
            "#F4C2D7", "#FFD4E2", "#FFB7D0", "#A6D9FF", "#8BC8FF",
            "#E6A9CB", "#FFBFD3", "#FFA7C1", "#9AC2FF", "#74AFFA",
            "#D8B6D8", "#FFC9D7", "#FFB3C8", "#B0E1FF", "#8DCFFD",
            "#D3AADB", "#FFBEDF", "#FFA9CC", "#AFD5FF", "#93C5FF"};


    public void setData(Utilisateur user) {
        String userImagePath = user.getImage(); // Supposons que user.getImage() retourne le nom du fichier image
        String imageUrl = "C:/Users/HP/Desktop/projetIntegration/pidev/public/uplaods/" + userImagePath;

        try {
            File file = new File(imageUrl);
            FileInputStream inputStream = new FileInputStream(file);
            Image image = new Image(inputStream);
            cardimage.setImage(image);
        } catch (FileNotFoundException e) {
            System.err.println("Image file not found: " + imageUrl);
        }
        cardnameprename.setText(user.getName() + " " + user.getPrename());
        cardemail.setText(user.getEmail());
        cardrole.setText(user.getRoles());
        cardnumtel.setText(String.valueOf(user.getPhone()));
        Card.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));
        Card.setStyle("-fx-border-radius: 5px;-fx-border-color:#808080");

        uprenom = user.getPrename();
        uid = user.getId();
        unom = user.getName();
        uemail = user.getEmail();
        umdp = user.getPassword();
        urole = user.getRoles();
        unumtel = user.getPhone();
        uimage = user.getImage();
    }

    public void suppuser(ActionEvent actionEvent) throws IOException {
        UserS.DeleteByID(uid);
    }

    public void modifuser(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            AdminUserController AUC = loader.getController();
            AUC.idtf.setText(String.valueOf(uid));
            AUC.phonetf.setText(String.valueOf(unumtel));
            AUC.nametf.setText(unom);
            AUC.prenametf.setText(uprenom);
            AUC.emailtf.setText(uemail);
            AUC.mdptf.setText(umdp);
            AUC.rolescb.setValue(urole);
            // Corriger le chemin de l'image
            String imageUrl = "C:/Users/HP/Desktop/projetIntegration/pidev/public/uploads/" + uimage;
            try {
                File file = new File(imageUrl);
                FileInputStream inputStream = new FileInputStream(file);
                Image image = new Image(inputStream);
                AUC.imagepdp.setImage(image);
            } catch (FileNotFoundException e) {
                System.err.println("Image file not found: " + imageUrl);
            }

            AUC.pdptf.setText(uimage);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
