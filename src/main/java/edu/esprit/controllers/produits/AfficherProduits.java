package edu.esprit.controllers.produits;

import edu.esprit.controllers.categories.ModifierCategorie;
import edu.esprit.entities.Produit;
import edu.esprit.service.IService;
import edu.esprit.service.Serviceproduit;
import edu.esprit.utils.CommonUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import static edu.esprit.utils.CommonUtils.createGridHeaderLabel;

public class AfficherProduits implements Initializable {
    @FXML
    private GridPane gridProduits;

    @FXML
    private Button ajouterProduitBtn;

    @FXML
    private BorderPane borderPanel;

    IService<Produit> serviceProduit = Serviceproduit.getInstance();

    public void setBorderPanel(BorderPane borderPanel) {
        this.borderPanel = borderPanel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Set<Produit> produits = serviceProduit.getAll();
            int index=0;
            gridProduits.addRow(index, createGridHeaderLabel("Nom"),
                    createGridHeaderLabel("Catégorie"), createGridHeaderLabel("Prix"),
                    createGridHeaderLabel("Quantité"), createGridHeaderLabel("Description"));
            index++;
            for (Produit produit: produits){
                Button btnModifier = getUpdateButton(produit);
                Button btnSupprimer = getDeleteButton(url, resourceBundle, produit);
                HBox hbox = new HBox(10); // spacing between nodes
                hbox.getChildren().addAll(btnModifier, btnSupprimer);
                gridProduits.addRow(index, new Label(produit.getNom()), new Label(produit.getCategorie().getNom()),
                        new Label(Objects.toString(produit.getPrix())), new Label(Objects.toString(produit.getQuantite())),
                        new Label(produit.getDescription()), hbox);
                index++;
            }
        } catch (SQLException e) {
            displayAlertErreure("Error", "Il y a un problème lors de l'affichage des produits");
        }
    }

    private Button getDeleteButton(URL url, ResourceBundle resourceBundle, Produit produit) {
        Button btnSupprimer = new Button("Supprimer");
        btnSupprimer.getStyleClass().add("btn-delete");
        EventHandler<ActionEvent> btnSupprimerHandler = event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Voulez-vous supprimer le produit: "+ produit.getNom());
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        serviceProduit.supprimer(produit.getId());
                        gridProduits.getChildren().clear();
                        initialize(url, resourceBundle);
                    } catch (SQLException e) {
                        displayAlertErreure("Error", "Il y a un problème lors de la suppression du produit");
                    }
                }
            });
        };
        btnSupprimer.setOnAction(btnSupprimerHandler);
        return btnSupprimer;
    }

    private Button getUpdateButton(Produit produit) {
        Button btn = new Button("Modifier");
        btn.getStyleClass().add("btn-update");
        EventHandler<ActionEvent> btnHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Voulez-vous modifier le produit: " + produit.getNom());
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            // Load the AnotherView.fxml file
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/produits/modifierProduit.fxml"));
                            Parent root = loader.load();

                            // Access the controller of the AnotherView
                            ModifierProduit controller = loader.getController();

                            // Pass parameters to the controller of AnotherView
                            controller.setProduitDetails(produit.getId());

                            // Create a new scene with the loaded FXML file
                            Scene scene = new Scene(root);
                            scene.getStylesheets().addAll(getClass().getResource("/css/styles.css").toExternalForm());
                            // Get the stage from the button and set the new scene
                            Stage stage = (Stage) btn.getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            displayAlertErreure("Error", "Il y a un problème lors de la suppression du produit");
                        }
                    }
                });
            }
        };
        btn.setOnAction(btnHandler);
        return btn;
    }

    @FXML
    void goToAddProductView(ActionEvent event) {
        // Load the AnotherView.fxml file
        try {
            Parent root =  FXMLLoader.load(getClass().getResource("/produits/ajouterProduit.fxml"));
            borderPanel.setCenter(root);
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
