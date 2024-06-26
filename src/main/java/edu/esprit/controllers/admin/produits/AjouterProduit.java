package edu.esprit.controllers.admin.produits;

import edu.esprit.controllers.AdminContentPanel;
import edu.esprit.entities.Categorie;
import edu.esprit.entities.Produit;
import edu.esprit.service.IService;
import edu.esprit.service.Servicecategorie;
import edu.esprit.service.Serviceproduit;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.image.ImageView;
//import javafx.embed.swing.SwingFXUtils;


public class AjouterProduit extends AdminContentPanel implements Initializable {

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
    private ImageView productImageView;

    IService<Categorie> serviceCategorie = Servicecategorie.getInstance();
    IService<Produit> serviceProduit = Serviceproduit.getInstance();

    @FXML
    void ajouterProduit(ActionEvent event) {
        try {
            Categorie categorie = categorieDropDown.getValue();
            Image image = productImageView.getImage();
            String imagePath = image == null ? "" : image.getUrl();
            String imageName = "";
            if (!imagePath.isEmpty()) {
                File file = new File(imagePath);
                imageName = file.getName(); // Obtient le nom du fichier à partir de l'URL de l'image
            }

            Produit produit = new Produit(categorie, Integer.parseInt(TFquantite.getText()), Double.parseDouble(TFprix.getText()),
                    TFnom.getText(), TFdescription.getText(), imageName);
          serviceProduit.ajouter(produit);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Produit ajouté avec succès");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/produits/AfficherProduits.fxml"));
                        Parent root = loader.load();
                        // Access the controller of the AnotherView
                        AfficherProduits controller = loader.getController();
                        controller.setAdminPanelContent(super.getAdminPanelContent());
                        controller.showData();
                        super.getAdminPanelContent().getChildren().clear();
                        super.getAdminPanelContent().getChildren().setAll(root);
                    } catch (IOException e) {
                        displayAlertErreure("Error", "Il y a un problème lors de l'ajout d'un produit");
                    }
                }
            });
        } catch (SQLException | IllegalArgumentException e) {
            displayAlertErreure("Exception", e.getMessage());
        }
    }

    private void displayAlertErreure(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Categorie> categories = serviceCategorie.getAll();
            if (categories != null && !categories.isEmpty()) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        String destinationDirectory = "C:/Users/HP/Desktop/projetIntegration/pidev/public/uploads/images/";

        File initialDirectory = new File(AjouterProduit.class.getResource("/assets/uploads/produits").getPath());
        fileChooser.setInitialDirectory(initialDirectory);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            productImageView.setImage(image);

            // Copier le fichier sélectionné vers la destination spécifiée
            try {
                Path sourcePath = selectedFile.toPath();
                String fileName = selectedFile.getName();
                Path destinationPath = Paths.get(destinationDirectory + fileName);
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace(); // Gérer les exceptions selon votre cas d'utilisation
            }
        }
    }


}
