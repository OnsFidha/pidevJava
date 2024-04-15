package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Publication;
import edu.esprit.service.CommentaireService;
import edu.esprit.service.PublicationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;

public class AfficherPub {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label dateCreationPub;

    @FXML
    private Label dateModificationPub;

    @FXML
    private Label lieuPub;

    @FXML
    private Label textPub;

    @FXML
    private Label typePub;
    private Publication p;
    @FXML
    void initialize() {

    }
    public void initData(Publication publication) {
        this.p = publication;}
    public void setDateCreationPub(Date dateCreationPub) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(dateCreationPub);
        this.dateCreationPub.setText(formattedDate);
    }

    public void setDateModificationPub(Date dateModificationPub) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (dateModificationPub != null) {
            String formattedDate = dateFormat.format(dateModificationPub);
            this.dateModificationPub.setText(formattedDate);
        } else {
            this.dateModificationPub.setText("Pas de modification");
        }
    }


    public void setLieuPub(String lieuPub) {
        this.lieuPub.setText(lieuPub);

    }

    public void setTextPub(String textPub) {
        this.textPub.setText(textPub);

    }

    public void setTypePub(String typePub) {
        this.typePub.setText(typePub);

    }
    @FXML
    void retour(ActionEvent event) {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/ListPub.fxml"));
        try{
        Parent root=loader.load();
        lieuPub.getScene().setRoot(root);}
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modify(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPub.fxml"));
            Parent root = loader.load();

            // Pass the selected event to the edit controller
            ModifierPub modifierPub = loader.getController();
            modifierPub.initData(this.p);

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception gracefully
        }
    }
    @FXML
    void delete(ActionEvent event) throws SQLException {

        // Supprimer la réclamation
        PublicationService p = new PublicationService();
        p.supprimer(this.p.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            if(JOptionPane.showConfirmDialog(null,"Attention, vous allez supprimer votre Publication. Êtes-vous sûr ?"
                    ,"Supprimer ",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)

                alert.setContentText("Votre Publication a été supprimée avec succès.");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ListPub.fxml"));
            try{
                Parent root=loader.load();
                lieuPub.getScene().setRoot(root);}
            catch (IOException e) {
                throw new RuntimeException(e);
                }
            }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Erreur lors de la suppression :\n" + e.getMessage());
        }
    }
    @FXML
    void addC(ActionEvent event) {

    }
//    @FXML
//    void addC(ActionEvent event) {
//        Commentaire commentaire = new Commentaire();
//        commentaire.setId_publication(idPublication);
//        commentaire.setText(texteCommentaire);
//
//        // Appeler le service de commentaire pour ajouter le commentaire
//        CommentaireService commentaireService = new CommentaireService();
//        try {
//            commentaireService.ajouter(commentaire);
//            // Afficher une alerte ou effectuer d'autres actions si l'ajout réussit
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText("Le commentaire a été ajouté avec succès");
//            alert.show();
//        } catch (SQLException e) {
//            // Afficher une alerte en cas d'erreur lors de l'ajout du commentaire
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("Erreur lors de l'ajout du commentaire : " + e.getMessage());
//            alert.show();
//        }
//    }

}
