package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Publication;
import edu.esprit.service.CommentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;

public class AjouterCom {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Circle circle;

    @FXML
    private TextArea text;
    private Publication p;
    @FXML
    void addCom(ActionEvent event) {
        Commentaire commentaire = new Commentaire();
        commentaire.setId_publication(this.p.getId());
        commentaire.setText(text.getText());

        CommentaireService commentaireService = new CommentaireService();
        try {
            commentaireService.ajouter(commentaire);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le commentaire a été ajouté avec succès");
            alert.show();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ListPub.fxml"));
            try {
                Parent root = loader.load();
                text.getScene().setRoot(root);
            }
            catch (IOException e) {
                Alert alert1=new Alert(Alert.AlertType.ERROR);
                alert1.setContentText(e.getMessage());
                alert1.show();
            }
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de l'ajout du commentaire : " + e.getMessage());
            alert.show();
       }
    }

    public void initData(Publication p) {
        this.p = p;
    }
    @FXML
    void initialize() {

    }

}
