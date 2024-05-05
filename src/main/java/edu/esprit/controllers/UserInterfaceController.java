package edu.esprit.controllers;

import edu.esprit.controllers.front.produits.ManageFrontTemplateMenu;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceUtilisateur;
import edu.esprit.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserInterfaceController implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private GridPane userContainer;
    @FXML
    private TextField usersearch;
    @FXML
    private ImageView logedUserimage;

    @FXML
    private Label logedUsernamee;
    @FXML
    private HBox pub;
    @FXML
    private HBox home;
    @FXML
    private HBox recla;
    @FXML
    private HBox produit;
    @FXML
    private HBox event;
    @FXML
    private Button AdminP;
    private final ServiceUtilisateur UserS = new ServiceUtilisateur();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (SessionManager.getRoles().equals("User")) {
            AdminP.setVisible(false);
        }
        if (SessionManager.getRoles().equals("Admin")) {
            AdminP.setVisible(true);
        }

        EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getListPub();
            }
        };

        // Ajouter l'EventHandler au HBox
        pub.setOnMouseClicked(clickHandler);
        load();
        ////////////
        EventHandler<MouseEvent> clickHandler2 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getHome();
            }
        };

        // Ajouter l'EventHandler au HBox
        home.setOnMouseClicked(clickHandler);
        load();
        /////////////
        EventHandler<MouseEvent> clickHandler4 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ManageFrontTemplateMenu.loadProducts(produit);
            }
        };

        // Ajouter l'EventHandler au HBox
        produit.setOnMouseClicked(clickHandler);
        load();
        /////////////////
        EventHandler<MouseEvent> clickHandler5 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getListEvent();
            }
        };

        // Ajouter l'EventHandler au HBox
        event.setOnMouseClicked(clickHandler5);
        load();
        /////////////////////
        EventHandler<MouseEvent> clickHandler7 = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getListRecla();
            }
        };

        // Ajouter l'EventHandler au HBox
        recla.setOnMouseClicked(clickHandler7);
        load();
    }
    private void getListEvent() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenements.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void getListRecla() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void getListPub(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void getHome(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();

    public void load() {
        int column = 0;
        int row = 1;
        try {
            for (Utilisateur user : UserS.afficher()) {
                logedUsernamee.setText(nameP);
                File file = new File(imagePath);
                FileInputStream inputStream = new FileInputStream(file);
                Image image = new Image(inputStream);
                logedUserimage.setImage(image);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userPane = fxmlLoader.load();
                CardUser1Controller cardC = fxmlLoader.getController();
                cardC.setData1(user);
                if (column == 2) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userPane, column++, row);
                GridPane.setMargin(userPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
    public void TriName(ActionEvent actionEvent) {
        int column = 0;
        int row = 1;
        try {
            for (Utilisateur user : UserS.TriparName()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userPane = fxmlLoader.load();
                CardUser1Controller cardC = fxmlLoader.getController();
                cardC.setData1(user);
                if (column == 2) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userPane, column++, row);
                GridPane.setMargin(userPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void refresh(ActionEvent actionEvent) {
        load();
    }
    @FXML
    public void TriEmail(ActionEvent actionEvent) {
        int column = 0;
        int row = 1;
        try {
            for (Utilisateur user : UserS.TriparEmail()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userPane = fxmlLoader.load();
                CardUser1Controller cardC = fxmlLoader.getController();
                cardC.setData1(user);
                if (column == 2) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userPane, column++, row);
                GridPane.setMargin(userPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void RechercheName(ActionEvent actionEvent) {
        int column = 0;
        int row = 1;
        String recherche = usersearch.getText();
        try {
            userContainer.getChildren().clear();
            for (Utilisateur user : UserS.Rechreche(recherche)){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userPane = fxmlLoader.load();
                CardUser1Controller cardC = fxmlLoader.getController();
                cardC.setData1(user);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userPane, column++, row);
                GridPane.setMargin(userPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void Menu(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("aristool - Accueil");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
