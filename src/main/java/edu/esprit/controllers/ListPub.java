package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import edu.esprit.entities.Publication;
import edu.esprit.service.PublicationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ListPub implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane pubList;
    private List<Publication> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PublicationService ps=new PublicationService();
        try{
            List<Publication> list= ps.getAll();
            int columns = 0;
            int rows = 1;
            for (Publication pub : list) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cardPub.fxml"));
                VBox carbox = fxmlLoader.load();
                CardController cc =fxmlLoader.getController();
                cc.setData(pub);
                if(columns == 3){
                    columns = 0;
                    rows++;
                }
                pubList.add(carbox,columns++,rows);
                GridPane.setMargin(carbox,new Insets(10));

            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    private List<Publication> recentlyAdded(){
      List<Publication> ls=new ArrayList<>();
      Publication p=new Publication();
      p.setLieu("tunis");
      p.setText("gagaha");
      p.setPhoto("img/sanaPic.jpg");
      ls.add(p);
      return ls;
    }

}
