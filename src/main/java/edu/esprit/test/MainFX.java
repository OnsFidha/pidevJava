package edu.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


     //   FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherReclamationAdmin.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherReponseAdmin.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/stat.fxml"));
       // FXMLLoader loader=new FXMLLoader(getClass().getResource("/login.fxml"));
     //   FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherEvenements.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/BaseAdmin.fxml"));

      //  FXMLLoader loader=new FXMLLoader(getClass().getResource("/BaseFront.fxml"));

        try {
            Parent root = loader.load();
            Scene scene =new Scene(root);
            primaryStage.setTitle("Artistool - Connection");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }



}


