package edu.esprit.controllers.front.produits;

import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.model.UserSession;
import edu.esprit.service.IService;
import edu.esprit.service.ServiceUser;
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
    IService<User> serviceUser = ServiceUser.getInstance();


    @FXML
    void ajouterAuPanier(ActionEvent event) {
        try {
            Produit produit = serviceProduit.getOneById(Integer.parseInt(productId.getText()));
            UserSession.addProductToCommande(produit);
            labelMontantTotal.setText(Objects.toString(UserSession.getCommande().getMontant_total()));
            nbreProduitdansLepanier.setText(Objects.toString(UserSession.getCommande().getDetailsCommande().size()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Le produit est bien ajouté au panier");
            alert.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showProduct(Produit produit){
        try {
            UserSession.setLoggedUser(serviceUser.getOneById(1));
            if (Objects.nonNull(UserSession.getCommande())){
                labelMontantTotal.setText(Objects.toString(UserSession.getCommande().getMontant_total()));
                nbreProduitdansLepanier.setText(Objects.toString(UserSession.getCommande().getDetailsCommande().size()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
