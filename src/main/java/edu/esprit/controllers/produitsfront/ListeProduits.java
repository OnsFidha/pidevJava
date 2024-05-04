package edu.esprit.controllers.produitsfront;

import edu.esprit.controllers.FrontContentPanel;
import edu.esprit.entities.Commande;
import edu.esprit.entities.Produit;
import edu.esprit.service.IService;
import edu.esprit.service.Serviceproduit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.input.MouseEvent;

public class ListeProduits extends FrontContentPanel {


    @FXML
    private GridPane productsList;

    IService<Produit> serviceProduit = Serviceproduit.getInstance();

    @FXML
    private Label labelMontantTotal;

    @FXML
    private Label nbreProduitdansLepanier;

    Commande commande;

    public void showProducts(){
        try {
            List<Produit> produits = serviceProduit.getAll().stream().toList();
            int index=0;
            for (int i = 0; i<produits.size(); i++){
                Produit column1 = produits.get(i);
                i++;
                if (i >= produits.size()) {
                    productsList.addRow(index, getProductDetails(column1));
                    break;
                }

                Produit column2 = produits.get(i);
                productsList.addRow(index, getProductDetails(column1), getProductDetails(column2));
                index++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Parent getProductDetails(Produit produit){
        try {
            if(produit == null){
                return null;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/produitsfront/ProductCard.fxml"));
            Parent root = loader.load();
            // Access the controller of the AnotherView
            ProductCard controller = loader.getController();
            controller.setLabelMontantTotal(labelMontantTotal);
            controller.setNbreProduitdansLepanier(nbreProduitdansLepanier);
            controller.showProduct(produit);
            return root;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void afficherLeDetailsduPanier(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/produitsfront/PanierDetails.fxml"));
            Parent root = loader.load();
            // Access the controller of the AnotherView
            PanierDetails controller = loader.getController();
            controller.setBaseFrontContentPanel(super.getBaseFrontContentPanel());
            controller.afficherPanierDetails();
            super.getBaseFrontContentPanel().getChildren().clear();
            super.getBaseFrontContentPanel().getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
