package edu.esprit.controllers.front.produits;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ManageFrontTemplateMenu {

    public static void loadProducts(HBox item){
        loadNewPage(item, "/front/produits/FrontTemplate.fxml");
    }

    public static void loadHome(HBox item){
        loadNewPage(item, "/MainPage.fxml");
    }
    public static void loadPub(HBox item){
        loadNewPage(item, "/ListPub.fxml");
    }
    public static void loadEvents(HBox item){
        loadNewPage(item, "/AfficherEvenements.fxml");
    }
    public static void loadReclamations(HBox item){
        loadNewPage(item, "/AfficherReclamation.fxml");
    }

    private static void loadNewPage(HBox item, String pagePath) {
        try {
            FXMLLoader loader = new FXMLLoader(ManageFrontTemplateMenu.class.getResource(pagePath));
            Parent root = loader.load();
            item.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
