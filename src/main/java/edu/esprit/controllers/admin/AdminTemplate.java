package edu.esprit.controllers.admin;

import edu.esprit.controllers.AdminContentPanel;
import edu.esprit.controllers.admin.categories.AfficherCategories;
import edu.esprit.controllers.admin.commandes.AfficherCommandes;
import edu.esprit.controllers.admin.produits.AfficherProduits;
import edu.esprit.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminTemplate extends AdminContentPanel implements Initializable {

    @FXML
    private Button BtnRecherche;

    @FXML
    private HBox categoriesHbox;

    @FXML
    private Circle circle;

    @FXML
    private Label loggedUsername;

    @FXML
    private HBox productsHbox;

    @FXML
    private HBox adminEventLabel;

    @FXML
    private HBox adminPubLabel;

    @FXML
    private HBox adminReclamationsLabel;

    @FXML
    private HBox adminResponsesLabel;

    @FXML
    private HBox adminusersLabel;

    @FXML
    private HBox categoriesLabel;

    @FXML
    private HBox commandesLabel;

    @FXML
    private Label homeLabel;

    @FXML
    private HBox productsLabel;

    @FXML
    void showCategories(MouseEvent event) {
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
            throw new RuntimeException(e);
        }
    }

    @FXML
    void showProducts(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/produits/AfficherProduits.fxml"));
            Parent root = loader.load();
            // Access the controller of the AnotherView
            AfficherProduits controller = loader.getController();
            controller.setAdminPanelContent(super.getAdminPanelContent());
            controller.showData();
            super.getAdminPanelContent().getChildren().clear();
            super.getAdminPanelContent().getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void showCommandes(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/commandes/AfficherCommandes.fxml"));
            Parent root = loader.load();
            // Access the controller of the AnotherView
            AfficherCommandes controller = loader.getController();
            controller.showData();
            super.getAdminPanelContent().getChildren().clear();
            super.getAdminPanelContent().getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void showAdminEvents(MouseEvent event) {
        ManageAdminTemplateMenu.loadEvents(adminEventLabel);
    }

    @FXML
    void showAdminHome(MouseEvent event) {
        ManageAdminTemplateMenu.loadHome(homeLabel);
    }

    @FXML
    void showAdminPub(MouseEvent event) {
        ManageAdminTemplateMenu.loadPub(adminPubLabel);
    }

    @FXML
    void showAdminReclamations(MouseEvent event) {
        ManageAdminTemplateMenu.loadReclamations(adminReclamationsLabel);
    }

    @FXML
    void showAdminResponses(MouseEvent event) {
        ManageAdminTemplateMenu.loadResponses(adminResponsesLabel);
    }

    @FXML
    void showUsers(MouseEvent event) {
        ManageAdminTemplateMenu.loadUsers(adminusersLabel);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedUsername.setText(SessionManager.getName()+" "+SessionManager.getPrename());
        if (SessionManager.getImage()!=null){
            int img = SessionManager.getImage().lastIndexOf("\\");
            String nomFichier = SessionManager.getImage().substring(img + 1);
            Image image = new Image("assets/uploads/"+nomFichier);
            circle.setFill(new ImagePattern(image));
        }
    }
}
