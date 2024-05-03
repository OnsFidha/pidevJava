package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class AdminContentPanel {

    @FXML
    private AnchorPane adminPanelContent;

    public AnchorPane getAdminPanelContent() {
        return adminPanelContent;
    }

    public void setAdminPanelContent(AnchorPane adminPanelContent) {
        this.adminPanelContent = adminPanelContent;
    }

}
