package edu.esprit.controllers.categories;

import edu.esprit.entities.Categorie;
import edu.esprit.service.IService;
import edu.esprit.service.Servicecategorie;
import edu.esprit.utils.CommonUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifierCategorie{

    @FXML
    private Label TFlabelIdCategorie;

    @FXML
    private TextField TFnom;

    @FXML
    private TextArea TFdescription;

    @FXML
    private Button btnModifierCategorie;

    IService<Categorie> serviceCategorie = Servicecategorie.getInstance();

    @FXML
    void modifierCategorieAction(ActionEvent event) {
        try {
            serviceCategorie.modifier(new Categorie(Integer.parseInt(TFlabelIdCategorie.getText()), TFnom.getText(),TFdescription.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Catégorie modifiée avec succès");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        CommonUtils.redirectToAnotherWindow(getClass().getResource("/categories/afficherCategories.fxml"), btnModifierCategorie,
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

    public void setCategorieDetails(int idCategorie) {
        try {
            Categorie categorie = serviceCategorie.getOneById(idCategorie);
            TFlabelIdCategorie.setText(Objects.toString(idCategorie));
            TFnom.setText(categorie.getNom());
            TFdescription.setText(categorie.getDescription());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
