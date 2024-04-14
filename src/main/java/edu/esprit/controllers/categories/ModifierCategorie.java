package edu.esprit.controllers.categories;

import edu.esprit.entities.Categorie;
import edu.esprit.service.IService;
import edu.esprit.service.Servicecategorie;
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
                        // Load the AnotherView.fxml file
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/categories/afficherCategories.fxml"));
                        Parent root = loader.load();

                        // Create a new scene with the loaded FXML file
                        Scene scene = new Scene(root);

                        // Get the stage from the button and set the new scene
                        Stage stage = (Stage) btnModifierCategorie.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
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
