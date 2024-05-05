package edu.esprit.controllers.admin.categories;

import edu.esprit.controllers.AdminContentPanel;
import edu.esprit.entities.Categorie;
import edu.esprit.service.IService;
import edu.esprit.service.Servicecategorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterCategorie extends AdminContentPanel {

    @FXML
    private TextArea TFdescription;

    @FXML
    private TextField TFnom;

    IService<Categorie> serviceCategorie = Servicecategorie.getInstance();

    @FXML
    void ajoutercategorieaction(ActionEvent event) {
        try {
            if(serviceCategorie.getOneByName(TFnom.getText()) != null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Catégorie existe déjà");
                alert.showAndWait();
                return;
            }
            serviceCategorie.ajouter(new Categorie(TFnom.getText(),TFdescription.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Catégorie ajoutée avec succès");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/categories/AfficherCategories.fxml"));
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
}