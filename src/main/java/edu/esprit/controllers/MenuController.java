package edu.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import edu.esprit.utils.SessionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button AdminP;
    @FXML
    private ImageView logedUserimage;

    @FXML
    private Label logedUsernamee;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (SessionManager.getRoles().equals("User")) {
            AdminP.setVisible(false);
        }
        if (SessionManager.getRoles().equals("Admin")) {
            AdminP.setVisible(true);
        }
        String imagePath = SessionManager.getImage();
        String nameP= SessionManager.getName()+" "+SessionManager.getPrename();

        logedUsernamee.setText(nameP);
        if (imagePath != null) {
            try {
                File file = new File(imagePath);
                FileInputStream inputStream = new FileInputStream(file);
                Image image = new Image(inputStream);
                logedUserimage.setImage(image);

            } catch (FileNotFoundException e) {
                System.err.println("Image file not found: " + imagePath);
            }
        } else {
            System.err.println("Image path is null for");
        }

    }

    @FXML
    public void UserInterface(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserInterface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool - Utilisateurs");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void PanelAdmin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool - Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Deconnection(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            SessionManager.cleanUserSession();
            stage.setTitle("artistool - Connection");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
