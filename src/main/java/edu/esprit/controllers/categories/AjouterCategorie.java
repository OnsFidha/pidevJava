package edu.esprit.controllers.categories;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.Produit;
import edu.esprit.service.IService;
import edu.esprit.service.Servicecategorie;
import edu.esprit.service.Serviceproduit;
import edu.esprit.utils.CommonUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterCategorie {

    @FXML
    private TextArea TFdescription;

    @FXML
    private TextField TFnom;

    @FXML
    private Button TFbtnAjouterCategorie;

    IService<Categorie> serviceCategorie = Servicecategorie.getInstance();

    @FXML
    void ajoutercategorieaction(ActionEvent event) {
        try {
            serviceCategorie.ajouter(new Categorie(TFnom.getText(),TFdescription.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Catégorie ajoutée avec succès");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        CommonUtils.redirectToAnotherWindow(getClass().getResource("/categories/afficherCategories.fxml"), TFbtnAjouterCategorie,
                                List.of(getClass().getResource("/css/styles.css").toExternalForm()));
                    } catch (IOException e) {
                        displayAlertErreure("Error", "Il y a un problème lors de la suppression de la catégorie");
                    }
                }
            });
        } catch (SQLException | IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void displayAlertErreure(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}