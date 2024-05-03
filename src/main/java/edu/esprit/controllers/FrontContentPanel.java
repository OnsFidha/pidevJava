package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class FrontContentPanel {

    @FXML
    private AnchorPane baseFrontContentPanel;

    public AnchorPane getBaseFrontContentPanel() {
        return baseFrontContentPanel;
    }

    public void setBaseFrontContentPanel(AnchorPane baseFrontContentPanel) {
        this.baseFrontContentPanel = baseFrontContentPanel;
    }
}
