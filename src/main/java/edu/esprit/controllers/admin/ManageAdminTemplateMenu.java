package edu.esprit.controllers.admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ManageAdminTemplateMenu {

    public static void loadProducts(HBox item){
        try {
            FXMLLoader loader = new FXMLLoader(ManageAdminTemplateMenu.class.getResource("/admin/AdminTemplate.fxml"));
            Parent root = loader.load();
            AdminTemplate adminTemplate = loader.getController();
            adminTemplate.showProducts(null);
            item.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadEvents(HBox item){
        loadNewPage(item, "/baseAdminEvent.fxml");
    }

    public static void loadHome(Label item){
        loadNewPage(item, "/stat.fxml");
    }

    public static void loadPub(HBox item){
        loadNewPage(item, "/AfficherPubAd.fxml");
    }

    public static void loadReclamations(HBox item){
        loadNewPage(item, "/AfficherReclamationAdmin.fxml");
    }

    public static void loadResponses(HBox item) {
        loadNewPage(item, "/AfficherReponseAdmin.fxml");
    }

    public static void loadUsers(HBox item) {
        loadNewPage(item, "/AdminUser.fxml");
    }

    private static void loadNewPage(Node item, String pagePath) {
        try {
            FXMLLoader loader = new FXMLLoader(ManageAdminTemplateMenu.class.getResource(pagePath));
            Parent root = loader.load();
            item.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
