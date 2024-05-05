package edu.esprit.controllers.admin.categories;

import edu.esprit.controllers.AdminContentPanel;
import edu.esprit.entities.Categorie;
import edu.esprit.service.IService;
import edu.esprit.service.Servicecategorie;
import edu.esprit.utils.CommonUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static edu.esprit.utils.CommonUtils.createGridHeaderLabel;

public class AfficherCategories extends AdminContentPanel{

    @FXML
    private GridPane gridCategories;

    IService<Categorie> serviceCategorie = Servicecategorie.getInstance();

    public void showData() {
        try {
            List<Categorie> categories =  serviceCategorie.getAll();
            int index=0;
            gridCategories.addRow(index, createGridHeaderLabel("Nom"), createGridHeaderLabel("Description"));
            index++;
            for (Categorie categorie: categories){
                Button btnModifier = getUpdateButton(categorie);
                Button btnSupprimer = getDeleteButton(categorie);
                HBox hbox = new HBox(10); // spacing between nodes
                hbox.getChildren().addAll(btnModifier, btnSupprimer);
                gridCategories.addRow(index, new Label(categorie.getNom()), new Label(categorie.getDescription()), hbox);
                index++;
            }
        } catch (SQLException e) {
            displayAlertErreure("Error", "Il y a un problème lors de l'affichage des catégories");
        }
    }

    private Button getDeleteButton(Categorie categorie) {
        Button btnSupprimer = new Button("Supprimer", new ImageView(new Image("/icons/delete.png", 15, 15, true, true)));
        btnSupprimer.getStyleClass().add("btn-delete");
        EventHandler<ActionEvent> btnSupprimerHandler = event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Voulez-vous supprimer la catégorie: " + categorie.getNom());
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        serviceCategorie.supprimer(categorie.getId());
                        gridCategories.getChildren().clear();
                        showData();
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
        Button btn = new Button("Modifier", new ImageView(new Image("/icons/edit.png", 15, 15, true, true)));
        btn.getStyleClass().add("btn-update");
        AnchorPane tmpAdminContentPanel = super.getAdminPanelContent();
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
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/categories/ModifierCategorie.fxml"));
                            Parent root = loader.load();

                            ModifierCategorie controller = loader.getController();
                            controller.setAdminPanelContent(tmpAdminContentPanel);
                            tmpAdminContentPanel.getChildren().clear();
                            tmpAdminContentPanel.getChildren().setAll(root);
                            controller.setCategorieDetails(categorie.getId());
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
            CommonUtils.redirectToAnotherWindow(getClass().getResource("/admin/categories/AjouterCategorie.fxml"), super.getAdminPanelContent());
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
