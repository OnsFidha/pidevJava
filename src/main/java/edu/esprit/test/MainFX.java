package edu.esprit.test;

<<<<<<< HEAD

=======
>>>>>>> 7d40d48e66bd4a89cfd13d249c637139b609081f
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {
<<<<<<< HEAD

=======
>>>>>>> 7d40d48e66bd4a89cfd13d249c637139b609081f
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
<<<<<<< HEAD

     //   FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherReclamationAdmin.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherReponseAdmin.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/stat.fxml"));
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/login.fxml"));

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

=======
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherEvenements.fxml"));

        try {
            Parent root=loader.load();
            Scene scene=new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
>>>>>>> 7d40d48e66bd4a89cfd13d249c637139b609081f
