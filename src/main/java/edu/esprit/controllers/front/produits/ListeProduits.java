package edu.esprit.controllers.front.produits;

import edu.esprit.controllers.FrontContentPanel;
import edu.esprit.entities.Categorie;
import edu.esprit.entities.Commande;
import edu.esprit.entities.Produit;
import edu.esprit.service.IService;
import edu.esprit.service.Servicecategorie;
import edu.esprit.service.Serviceproduit;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class ListeProduits extends FrontContentPanel {


    @FXML
    private GridPane productsList;

    IService<Produit> serviceProduit = Serviceproduit.getInstance();
    IService<Categorie> serviceCategorie = Servicecategorie.getInstance();

    @FXML
    private Label labelMontantTotal;

    @FXML
    private Label nbreProduitdansLepanier;

    @FXML
    private ChoiceBox<Categorie> categorieDropDown;

    public void showProducts(){
        try {
            addCategorieToDropDown();
            List<Produit> produits = serviceProduit.getAll().stream().toList();
            addProductsToGrid(produits);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCategorieToDropDown(){
        try {
            List<Categorie> categories =  serviceCategorie.getAll();
            categories.add(0, new Categorie(-1, "Toutes les catégories", "toutes les catégories"));
            categorieDropDown.setConverter(new StringConverter<>() {
                @Override
                public String toString(Categorie objet) {
                    // Retourne la représentation en String de l'objet que vous voulez afficher
                    return objet.getNom();
                }
                @Override
                public Categorie fromString(String s) {
                    return null;
                }
            });
            categorieDropDown.setItems(FXCollections.observableArrayList(categories));
            categorieDropDown.setValue(categories.stream().findFirst().orElse(null));
            // Add a listener to the value property
            categorieDropDown.valueProperty().addListener((observable, oldValue, categorie) -> {
                System.out.println("Selected option: " + categorie);
                filterProductByCategory(categorie);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void filterProductByCategory(Categorie categorie){
        try {
            List<Produit> produits = serviceProduit.getAll().stream().toList();
            if(Objects.nonNull(categorie) && categorie.getId()!=-1){
                produits = produits.stream().filter(produit -> produit.getCategorie().getId()==categorie.getId()).toList();
            }
            addProductsToGrid(produits);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addProductsToGrid(List<Produit> produits) {
        productsList.setAlignment(Pos.CENTER);
        productsList.getChildren().clear();
        int index=0;
        for (int i = 0; i< produits.size(); i++){
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
    }

    private Parent getProductDetails(Produit produit){
        try {
            if(produit == null){
                return null;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front/produits/ProductCard.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front/produits/PanierDetails.fxml"));
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
