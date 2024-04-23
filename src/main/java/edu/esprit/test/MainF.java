package edu.esprit.test;

import edu.esprit.controllers.produitsfront.ListeProduits;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainF extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/baseAdmin.fxml"));
       FXMLLoader loader=new FXMLLoader(getClass().getResource("/baseFront.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/produitsfront/listeProduits.fxml"));

        try {
            Parent root = loader.load();
            Scene scene =new Scene(root);
            primaryStage.setTitle("Artistool");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}