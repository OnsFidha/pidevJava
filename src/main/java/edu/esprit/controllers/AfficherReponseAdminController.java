package edu.esprit.controllers;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.service.ReclamationService;
import edu.esprit.service.ReponseService;
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

import javax.swing.*;

public class AfficherReponseAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnRecherche;

    @FXML
    private Button ModifierReponse;

    @FXML
    private Button addReponse;

    @FXML
    private Circle circle;

    @FXML
    private TableColumn<?, ?> dateReponse;

    @FXML
    private TextField rechercheText;

    @FXML
    private Button refresh;

    @FXML
    private TableColumn<?, ?> reponse;

    @FXML
    private Button supprimerReponse;


    @FXML
    private TableView<Reponse> tableauRep;

    @FXML
    void reclamationSideBar(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationAdmin.fxml"));
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
    void deleteReponse(ActionEvent event) throws SQLException {
        ReponseService sr = new ReponseService();
        Reponse r = (Reponse) tableauRep.getSelectionModel().getSelectedItem();

        // Obtenez la réclamation associée à la réponse
        ReclamationService reclamationService = new ReclamationService();
        Reclamation rec = reclamationService.getOneById(r.getRelation_id());


        // Mettez l'état de la réclamation à "non traité" (false)
        rec.setEtat(false);
        // Mettez à jour l'état de la réclamation en base de données
        reclamationService.modifier(rec);
        System.out.println(rec);
        sr.supprimer(r.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            if(JOptionPane.showConfirmDialog(null,"attention vous allez supprimer votre reponse,est ce que tu et sure?"
                    ,"supprimer reponse",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)

                if(!r.getReponse().isEmpty()){

                    alert.setContentText("Votre reponse a ete bien supprime");
                    JOptionPane.showMessageDialog(null,"reponse supprime");
                }//ca est pour recharger la list des stagiaire
                else { JOptionPane.showMessageDialog(null,"veuillez remplire le champ id !");}

        }catch (Exception e){JOptionPane.showMessageDialog(null,"erreur de supprimer \n"+e.getMessage());}


    }

    @FXML
    void refreshReponse(ActionEvent event) {
        ReponseService sr = new ReponseService();
        List<Reponse> rep = sr.refreshRep();
        ObservableList<Reponse> myList = FXCollections.observableList(rep);
        tableauRep.setItems(myList);

        reponse.setCellValueFactory(new PropertyValueFactory<>("reponse"));
        dateReponse.setCellValueFactory(new PropertyValueFactory<>("date_reponse"));
        tableauRep.setItems(myList);


    }

    @FXML
    void updateReponse(ActionEvent event) {
        Reponse r = tableauRep.getSelectionModel().getSelectedItem();

        if(r == null) {
            System.out.println("Aucune réponse est sélectionnée");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucune réponse est  sélectionnée");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReponse.fxml"));
                Parent root = loader.load();

                ModifierReponseController mr = loader.getController();
                mr.setData(r.getId(), r.getReponse());

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

    @FXML
    void initialize() {
        // Load image from resources
        Image img = new Image("/img/sanaPic.jpg");
        // Set image as fill for the circle
        circle.setFill(new ImagePattern(img));
        // TODO
        try {
            afficherReponse();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void afficherReponse() throws SQLException {
      /* ServiceReclamation sr = new ServiceReclamation();
        ObservableList<Reclamation> o = FXCollections.observableArrayList(sr.afficherReclamation());*/
        ReponseService sr = new ReponseService();
        List<Reponse> rep = sr.getAll();
        ObservableList<Reponse> myList = FXCollections.observableList(rep);
        tableauRep.setItems(myList);

        reponse.setCellValueFactory(new PropertyValueFactory<>("reponse"));
        dateReponse.setCellValueFactory(new PropertyValueFactory<>("date_reponse"));


    }


}
