package edu.esprit.controllers;

import edu.esprit.entities.Collaboration;
import edu.esprit.entities.Publication;
import edu.esprit.service.CollaborationService;
import edu.esprit.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListCollab  {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Label logedUsernamee;
    @FXML
    private URL location;
    @FXML
    private Label non;
    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+ SessionManager.getPrename();

    @FXML
    private Circle circle;

    @FXML
    private GridPane liste;
    private Publication p;

    public  void initData(Publication p) {
        this.p=p;
    }


    public void initialize(Publication p) {
        logedUsernamee.setText(nameP);
        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        circle.setFill(new ImagePattern(image));

        CollaborationService cs = new CollaborationService();
        List<Collaboration> collaborateurs;
        try {
            collaborateurs = cs.getListByIdPublication(p.getId());

            int column = 0;
            int row = 0;
            int maxColumnsPerRow = 2;
            if(collaborateurs.isEmpty()){
                non.setText("pas de collaboration pour le moment");
            }else{
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
            }}
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void retour(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListPub.fxml"));
        try {
            Parent root = loader.load();
            liste.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
