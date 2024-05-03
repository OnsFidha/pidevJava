package edu.esprit.utils;

import edu.esprit.controllers.AdminContentPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class CommonUtils {

    public static Label createGridHeaderLabel(String text){
        Label label = new Label(text);
        label.getStyleClass().add("header-node");
        return label;
    }
    public static void redirectToAnotherWindow(URL url, AnchorPane adminContentPanel) throws IOException {
        // Load the AnotherView.fxml file
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        AdminContentPanel controller = loader.getController();
        controller.setAdminPanelContent(adminContentPanel);
        adminContentPanel.getChildren().clear();
        adminContentPanel.getChildren().setAll(root);
    }
}
