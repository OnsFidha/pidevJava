package edu.esprit.controllers.produitsfront;

import edu.esprit.entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    @FXML
    void addProductToPannier(ActionEvent event) {

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
