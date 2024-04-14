package edu.esprit.controllers.produits;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.Produit;
import edu.esprit.service.IService;
import edu.esprit.service.Servicecategorie;
import edu.esprit.service.Serviceproduit;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;

public class ModifierProduit {

    @FXML
    private Label TFlabelIdProduit;

    @FXML
    private TextField TFnom;

    @FXML
    private TextField TFprix;
    @FXML
    private TextField TFquantite;
    @FXML
    private TextArea TFdescription;

    @FXML
    private ChoiceBox<Categorie> categorieDropDown;

    @FXML
    private Button modifierProduitBtn;

    @FXML
    private ImageView productImageView;

    IService<Produit> serviceProduit = Serviceproduit.getInstance();
    IService<Categorie> serviceCategorie = Servicecategorie.getInstance();
    @FXML
    void modifierProduit(ActionEvent event) {
        try {

            Categorie categorie = categorieDropDown.getValue();
            Image image = productImageView.getImage();
            Produit produit = new Produit(Integer.parseInt(TFlabelIdProduit.getText()), categorie, Integer.parseInt(TFquantite.getText()), Double.parseDouble(TFprix.getText()),
                    TFnom.getText(), TFdescription.getText(), image==null?"":image.getUrl());
            serviceProduit.modifier(produit);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Produit ajouté avec succès");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        // Load the AnotherView.fxml file
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/produits/afficherProduits.fxml"));
                        Parent root = loader.load();

                        // Create a new scene with the loaded FXML file
                        Scene scene = new Scene(root);

                        // Get the stage from the button and set the new scene
                        Stage stage = (Stage) modifierProduitBtn.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        displayAlertErreure("Error", "Il y a un problème lors de l'ajout d'un produit");
                    }
                }
            });
        } catch (SQLException|IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void displayAlertErreure(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setProduitDetails(int idProduit) {
        try {
            Produit produit = serviceProduit.getOneById(idProduit);
            TFlabelIdProduit.setText(Objects.toString(idProduit));
            TFnom.setText(produit.getNom());
            TFdescription.setText(produit.getDescription());
            TFquantite.setText(Objects.toString(produit.getQuantite()));
            TFprix.setText(Objects.toString(produit.getPrix()));
            if(produit.getImage()!=null && !produit.getImage().trim().equalsIgnoreCase("")){
                Image image = new Image(produit.getImage());
                productImageView.setImage(image);
            }

            Set<Categorie> categories = serviceCategorie.getAll();
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
            categorieDropDown.setValue(produit.getCategorie());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            productImageView.setImage(image);
        }
    }


}
