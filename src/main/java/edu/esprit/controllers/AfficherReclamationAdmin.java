package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

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

public class AfficherReclamationAdmin {

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

    static Reclamation selected;

    @FXML
    void chercherReclam(ActionEvent event) {

    }

    @FXML
    void initialize() {
        // Load image from resources
        Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        circle.setFill(new ImagePattern(img));
        // TODO
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

        selected=tableauReclam.getSelectionModel().getSelectedItem();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReponse.fxml"));
            Parent root = loader.load();

            //AjouterReponseController mr = loader.getController();
            //.setData(selected.getId(), selected.getDescription(), selected.getType());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Réclamation");
            stage.show();

            // Hide the current window
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

}


