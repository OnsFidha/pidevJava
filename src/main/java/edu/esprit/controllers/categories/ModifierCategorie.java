package edu.esprit.controllers.categories;

import edu.esprit.controllers.AdminContentPanel;
import edu.esprit.entities.Categorie;
import edu.esprit.service.IService;
import edu.esprit.service.Servicecategorie;
import edu.esprit.utils.CommonUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ModifierCategorie extends AdminContentPanel {

    @FXML
    private Label TFlabelIdCategorie;

    @FXML
    private TextField TFnom;

    @FXML
    private TextArea TFdescription;

    IService<Categorie> serviceCategorie = Servicecategorie.getInstance();

    @FXML
    void modifierCategorieAction(ActionEvent event) {
        try {
            Categorie categorieInDb = serviceCategorie.getOneByName(TFnom.getText());
            if(categorieInDb != null && categorieInDb.getId() != Integer.parseInt(TFlabelIdCategorie.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Catégorie existe déjà");
                alert.showAndWait();
                return;
            }
            serviceCategorie.modifier(new Categorie(Integer.parseInt(TFlabelIdCategorie.getText()), TFnom.getText(),TFdescription.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Catégorie modifiée avec succès");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/categories/afficherCategories.fxml"));
                        Parent root = loader.load();
                        // Access the controller of the AnotherView

                        AfficherCategories controller = loader.getController();
                        controller.setAdminPanelContent(super.getAdminPanelContent());
                        controller.showData();
                        super.getAdminPanelContent().getChildren().clear();
                        super.getAdminPanelContent().getChildren().setAll(root);
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
