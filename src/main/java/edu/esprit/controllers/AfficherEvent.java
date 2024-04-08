package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.model.Event;
import edu.esprit.service.EvenementService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherEvent implements Initializable {
    @FXML
    private GridPane eventgrid;
    private List<Evenement> events;
    private EvenementService evenementService = new EvenementService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EvenementService evenementService = new EvenementService();

        try {
            List<Evenement> events = evenementService.getAll();

            int columns = 0;
            int rows = 1;

            for (Evenement event : events) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Event.fxml"));
                VBox eventbox = fxmlLoader.load();

                EventController eventController = fxmlLoader.getController();
                eventController.setData(event);

                if (columns == 3) {
                    columns = 0;
                    rows++;
                }

                eventgrid.add(eventbox, columns++, rows);
                GridPane.setMargin(eventbox, new Insets(10));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Handle any potential errors here
        }
    }

//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        events= new ArrayList<>(data());
//
//        int columns=0;
//        int rows=1;
//        try{
//            for ( int i=0; i < events.size(); i++){
//                FXMLLoader fxmlloader = new FXMLLoader();
//                fxmlloader.setLocation(getClass().getResource("/Event.fxml"));
//
//                VBox eventbox = fxmlloader.load();
//
//                EventController eventController = fxmlloader.getController();
//                eventController.setData(events.get(i));
//
//                if ( columns == 3){
//                    columns = 0;
//                    ++rows;
//                }
//
//                eventgrid.add(eventbox, columns++, rows);
//                GridPane.setMargin(eventbox, new Insets(10));
//            }
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//
//
//    }
//    private List<Evenement> data(){
//        List<Evenement> ls= new ArrayList<>();
//        try {
//            // Retrieve events from the database using the service class
//            ls = evenementService.getAll();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle any potential errors here
//        }


//        Evenement event = new Event();
//        event.setProfileImageSrc("/img/user2.png");
//        event.setEventImageSrc("/img/Event5.png");
//        event.setUsername("Syrine Zaier");
//        event.setEventName("Art Event");
//        event.setNbStars("0/5");
//        event.setNbComments("2K");
//        ls.add(event);
//
//        Evenement event2 = new Event();
//        event2.setProfileImageSrc("/img/user1.png");
//        event2.setEventImageSrc("/img/Event3.png");
//        event2.setUsername("Syrine Zaier");
//        event2.setEventName("Art Event");
//        event2.setNbStars("5/5");
//        event2.setNbComments("24K");
//        ls.add(event2);
//
//        Evenement event3 = new Event();
//        event3.setProfileImageSrc("/img/user1.png");
//        event3.setEventImageSrc("/img/Event3.png");
//        event3.setUsername("Syrine Zaier");
//        event3.setEventName("Art Event");
//        event3.setNbStars("5/5");
//        event3.setNbComments("24K");
//        ls.add(event3);
//        Evenement event4 = new Event();
//        event4.setProfileImageSrc("/img/user1.png");
//        event4.setEventImageSrc("/img/Event3.png");
//        event4.setUsername("Syrine Zaier");
//        event4.setEventName("Art Event");
//        event4.setNbStars("5/5");
//        event4.setNbComments("24K");
//        ls.add(event4);
//        return ls;
//    }
}
