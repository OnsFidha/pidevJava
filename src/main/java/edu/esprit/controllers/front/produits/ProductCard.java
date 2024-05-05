package edu.esprit.controllers.front.produits;

import edu.esprit.entities.Produit;
import edu.esprit.model.UserCommande;
import edu.esprit.service.IService;
import edu.esprit.service.Serviceproduit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.util.Objects;

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
    private Label labelMontantTotal;

    @FXML
    private Label nbreProduitdansLepanier;

    public void setNbreProduitdansLepanier(Label nbreProduitdansLepanier) {
        this.nbreProduitdansLepanier = nbreProduitdansLepanier;
    }

    public void setLabelMontantTotal(Label labelMontantTotal) {
        this.labelMontantTotal = labelMontantTotal;
    }

    IService<Produit> serviceProduit = Serviceproduit.getInstance();

    @FXML
    void ajouterAuPanier(ActionEvent event) {
        try {
            Produit produit = serviceProduit.getOneById(Integer.parseInt(productId.getText()));
            UserCommande.addProductToCommande(produit);
            labelMontantTotal.setText(Objects.toString(UserCommande.getCommande().getMontant_total()));
            nbreProduitdansLepanier.setText(Objects.toString(UserCommande.getCommande().getDetailsCommande().size()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Le produit est bien ajouté au panier");
            alert.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showProduct(Produit produit){
        if (Objects.nonNull(UserCommande.getCommande())){
            labelMontantTotal.setText(Objects.toString(UserCommande.getCommande().getMontant_total()));
            nbreProduitdansLepanier.setText(Objects.toString(UserCommande.getCommande().getDetailsCommande().size()));
        }

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
