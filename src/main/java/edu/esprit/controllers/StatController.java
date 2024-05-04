package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.esprit.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import edu.esprit.utils.DataSource;

public class StatController implements Initializable {
    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+SessionManager.getPrename();

    @FXML
    private Label logedUsernamee;

    private Connection cnx;
    private PreparedStatement pst;
    private ResultSet rs;

    @FXML
    private PieChart stat;  // Utilisez PieChart pour l'ID fx:id="stat" dans le FXML

    @FXML
    private Button statics;
    @FXML
    private Button reclamation;
    @FXML
    private Button statics11;
    @FXML
    private Button statics111;
    @FXML
    private Button statics1111;
    @FXML
    private Button account;

    @FXML
    private Circle circle;

    // Constructeur du contrôleur
    public StatController() {
        // Initialisation de la connexion à la base de données
        cnx = DataSource.getInstance().getConn();
    }

    @FXML
    void reclamations(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool - Ajout Reclamation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void reponses(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReponseAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool - Ajout Reclamation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        logedUsernamee.setText(nameP);
        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        circle.setFill(new ImagePattern(image));

        // Requête SQL pour obtenir les statistiques sur les réclamations par type
        String req = "SELECT type, COUNT(*) AS count FROM Reclamation GROUP BY type";

        try {
            // Préparation et exécution de la requête SQL
            pst = cnx.prepareStatement(req);
            rs = pst.executeQuery();

            // Parcourir les résultats de la requête
            while (rs.next()) {
                // Récupérer le type de réclamation et le nombre de réclamations pour ce type
                String type = rs.getString("type");
                int count = rs.getInt("count");

                // Ajouter les données au PieChart
                PieChart.Data data = new PieChart.Data(type, count);
                stat.getData().add(data);
            }

        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur de requête SQL
            System.out.println("Erreur lors de la récupération des statistiques : " + e.getMessage());

            // Afficher une alerte à l'utilisateur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la récupération des statistiques. Veuillez vérifier votre connexion à la base de données.");
            alert.showAndWait();
        } finally {
            // Fermer les ressources pour éviter les fuites de mémoire
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    // Méthode pour naviguer vers la vue de réclamation
    @FXML
    private void toRec(ActionEvent event) throws IOException {
        navigateToView("ReclamationAdmin.fxml", event);
    }

    // Méthode pour naviguer vers la vue de statistiques
    @FXML
    private void toStat(ActionEvent event) throws IOException {
        navigateToView("stat.fxml", event);
    }

    // Méthode pour naviguer vers une vue spécifique
    private void navigateToView(String fxmlFile, ActionEvent event) throws IOException {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        // Créer une nouvelle scène avec le fichier FXML chargé
        Scene scene = new Scene(root);

        // Obtenir la fenêtre actuelle à partir de l'événement
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Modifier la scène de la fenêtre actuelle
        stage.setScene(scene);
        stage.show();
    }
}
