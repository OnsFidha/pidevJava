package edu.esprit.controllers.produitsfront;

import edu.esprit.entities.Commande;
import edu.esprit.entities.DetailCommande;
import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class ProductCard{
    @FXML
    private Label productId;

    @FXML
    private Button addProductToPannierBtn;

    @FXML
    private Label productDescription;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    private ImageView productImage;

    IService<Commande> commandeIService = ServiceCommande.getInstance();
    IService<Produit> serviceProduit = Serviceproduit.getInstance();
    IService<User> serviceUser = ServiceUser.getInstance();

    @FXML
    void passerCommande(ActionEvent event) {
        try {
            Produit produit = serviceProduit.getOneById(Integer.parseInt(productId.getText()));
            User user = serviceUser.getOneById(1);
            List<DetailCommande> detailCommandes = List.of(new DetailCommande(0, produit, produit.getPrix(), 1));
            Commande commande = new Commande(user, Calendar.getInstance().getTime(), "", produit.getPrix(), detailCommandes);
            commandeIService.ajouter(commande);
            SendMail.send(user.getEmail());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("La commande est passé avec success ");
            alert.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showProduct(Produit produit){
        productId.setText(String.valueOf(produit.getId()));
        productName.setText(produit.getNom());
        productDescription.setText(produit.getDescription());
        productPrice.setText(String.valueOf(produit.getPrix()));
        if(produit.getImage()!=null && !produit.getImage().trim().equalsIgnoreCase("")){
            Image image = new Image(produit.getImage());
            productImage.setImage(image);
        }
    }
}
