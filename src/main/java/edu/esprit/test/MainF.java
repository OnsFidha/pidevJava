package edu.esprit.test;

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
     //  FXMLLoader loader=new FXMLLoader(getClass().getResource("/AdminListPub.fxml"));
     FXMLLoader loader=new FXMLLoader(getClass().getResource("/adminPage.fxml"));
     //  FXMLLoader loader=new FXMLLoader(getClass().getResource("/MainPage.fxml"));
        try {
            System.setProperty("javafx.graphics", "true");
            Parent root = loader.load();
            Scene scene =new Scene(root);
            primaryStage.setTitle("Artistool - reclamation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
