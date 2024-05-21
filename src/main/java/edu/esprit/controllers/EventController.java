package edu.esprit.controllers;

import edu.esprit.entities.Evenement;

import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceUtilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class EventController {




        @FXML
        private ImageView EventImage;

        @FXML
        private Label EventName;

        @FXML
        private ImageView ProfileImage;



        @FXML
        private Label username;

    ServiceUtilisateur serviceuser = new ServiceUtilisateur();

    public void setData(Evenement event) {
        Utilisateur userevent = serviceuser.getUtilisateurById(event.getId_user_id());
        // Load event image
//        if (event.getImage() != null) {
//            try {
//                // Prepend "file:///" to the file path
//                String imagePath = "file:///" + event.getImage();
//                Image image = new Image(imagePath);
//                EventImage.setImage(image);
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.err.println("Failed to load image for the event: " + event.getNom());
//            }
//        } else {
//            // Handle the case where image path is null
//            // For example, set a default image or display an error message
//            System.err.println("Image path is null for the event: " + event.getNom());
//        }
        String imageName = event.getImage();
        String destinationDirectory = "C:/Users/HP/Desktop/projetIntegration/pidev/public/uploads/";
        String imagePath = destinationDirectory + imageName;
        File file = new File(imagePath);
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            EventImage.setImage(image);
        } else {

        }

        // Load profile image (assuming "/img/user2.png" is a valid image path)
//        String imagePath2 = userevent.getImage();
//
//
//
//        int img = imagePath2.lastIndexOf("\\");
//        String nomFichier = imagePath2.substring(img + 1);
//        Image nimage = new Image("assets/uploads/"+nomFichier);
//        ProfileImage.setImage(nimage);
        String imagePath2 = userevent.getImage();
        int img = imagePath2.lastIndexOf("\\");
        String nomFichier = imagePath2.substring(img + 1);
        String imageUrl = "file:///C:/Users/HP/Desktop/projetIntegration/pidev/public/uplaods/" + nomFichier;
        Image nimage = new Image(imageUrl);
        ProfileImage.setImage(nimage);

        // Set username and event details
        username.setText(userevent.getPrename()+" "+ userevent.getName());
        EventName.setText(event.getNom());

    }




}
