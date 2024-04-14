package edu.esprit.controllers.categories;

import edu.esprit.entities.Categorie;
import edu.esprit.service.IService;
import edu.esprit.service.Servicecategorie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AfficherCategories  implements Initializable {

    @FXML
    private GridPane gridCategories;

    @FXML
    private Button btnAjouterCategorie;

    IService<Categorie> serviceCategorie = Servicecategorie.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Set<Categorie> categories = serviceCategorie.getAll();
            int index=0;
            gridCategories.addRow(index, new Label("Nom"), new Label("Description"));
            index++;
            for (Categorie categorie: categories){
                Button btnModifier = getUpdateButton(categorie);
                Button btnSupprimer = getDeleteButton(url, resourceBundle, categorie);
                HBox hbox = new HBox(5); // spacing between nodes
                hbox.getChildren().addAll(btnModifier, btnSupprimer);
                gridCategories.addRow(index, new Label(categorie.getNom()), new Label(categorie.getDescription()), hbox);
                index++;
            }
        } catch (SQLException e) {
            displayAlertErreure("Error", "Il y a un problème lors de l'affichage des catégories");
        }
    }

    private Button getDeleteButton(URL url, ResourceBundle resourceBundle, Categorie categorie) {
        Button btnSupprimer = new Button("Supprimer");
        EventHandler<ActionEvent> btnSupprimerHandler = event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Voulez-vous supprimer la catégorie: " + categorie.getNom());
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        serviceCategorie.supprimer(categorie.getId());
                        gridCategories.getChildren().clear();
                        initialize(url, resourceBundle);
                    } catch (SQLException e) {
                        displayAlertErreure("Error", "Il y a un problème lors de la suppression de la catégorie");
                    }
                }
            });
        };
        btnSupprimer.setOnAction(btnSupprimerHandler);
        return btnSupprimer;
    }

    private Button getUpdateButton(Categorie categorie) {
        Button btn = new Button("Modifier");
        EventHandler<ActionEvent> btnHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Voulez-vous modifier la catégorie: "+ categorie.getNom());
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            // Load the AnotherView.fxml file
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/categories/modifierCategorie.fxml"));
                            Parent root = loader.load();

                            // Access the controller of the AnotherView
                            ModifierCategorie controller = loader.getController();

                            // Pass parameters to the controller of AnotherView
                            controller.setCategorieDetails(categorie.getId());

                            // Create a new scene with the loaded FXML file
                            Scene scene = new Scene(root);

                            // Get the stage from the button and set the new scene
                            Stage stage = (Stage) btn.getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            displayAlertErreure("Error", "Il y a un problème lors de la suppression de la catégorie");
                        }
                    }
                });
            }
        };
        btn.setOnAction(btnHandler);
        return btn;
    }

    @FXML
    void goToAddCategoryView(ActionEvent event) {
        // Load the AnotherView.fxml file
        try {
            // Load the AnotherView.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/categories/ajouterCategorie.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);

            // Get the stage from the button and set the new scene
            Stage stage = (Stage) btnAjouterCategorie.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            displayAlertErreure("Error", "Il y a un problème lors de la redirection vers la bonne interface");
        }
    }

    private void displayAlertErreure(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
