package edu.esprit.controllers;

import edu.esprit.entities.Collaboration;
import edu.esprit.entities.Publication;
import edu.esprit.service.CollaborationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListCollab implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Circle circle;

    @FXML
    private GridPane liste;
    private Publication p;

    public  void initData(Publication p) {
        this.p=p;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CollaborationService cs = new CollaborationService();
        List<Collaboration> collaborateurs;
        try {
            collaborateurs = cs.getListByIdPublication(1);

            int column = 0;
            int row = 0;
            int maxColumnsPerRow = 2;

            for (Collaboration collaborateur : collaborateurs) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cardCollab.fxml"));
                AnchorPane card = loader.load();

                cardCollab controller = loader.getController();
                controller.setData(collaborateur);

                card.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2) {
                      //  redirectToCollaborateurPage(collaborateur);
                    }
                });

                liste.add(card, column, row);

                column++;
                if (column == maxColumnsPerRow) {
                    column = 0;
                    row++;
                }
                liste.setHgap(25);
                liste.setVgap(250);
             //   GridPane.setMargin(card, new Insets(7));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
