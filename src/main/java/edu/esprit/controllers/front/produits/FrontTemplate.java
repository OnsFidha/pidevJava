package edu.esprit.controllers.front.produits;

import edu.esprit.controllers.FrontContentPanel;
import edu.esprit.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FrontTemplate extends FrontContentPanel implements Initializable {

    @FXML
    private Circle circle;

    @FXML
    private Label logedUsernamee;

    @FXML
    private HBox eventsLabel;

    @FXML
    private HBox homeLabel;

    @FXML
    private HBox pubLabel;

    @FXML
    private HBox reclamationsLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addUserInfo();
        showProducts(null);
    }

    private void addUserInfo(){
        logedUsernamee.setText(SessionManager.getName()+" "+SessionManager.getPrename());
        int img = SessionManager.getImage().lastIndexOf("\\");
        String nomFichier = SessionManager.getImage().substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        circle.setFill(new ImagePattern(image));
    }

    void showProducts(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front/produits/ListeProduits.fxml"));
            Parent root = loader.load();
            // Access the controller of the AnotherView
            ListeProduits controller = loader.getController();
            controller.setBaseFrontContentPanel(super.getBaseFrontContentPanel());
            controller.showProducts();
            super.getBaseFrontContentPanel().getChildren().clear();
            super.getBaseFrontContentPanel().getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showEvents(MouseEvent event) {
        ManageFrontTemplateMenu.loadEvents(eventsLabel);
    }

    @FXML
    void showHome(MouseEvent event) {
        ManageFrontTemplateMenu.loadHome(homeLabel);
    }

    @FXML
    void showPub(MouseEvent event) {
        ManageFrontTemplateMenu.loadPub(pubLabel);
    }

    @FXML
    void showReclamation(MouseEvent event) {
        ManageFrontTemplateMenu.loadReclamations(reclamationsLabel);
    }
}
