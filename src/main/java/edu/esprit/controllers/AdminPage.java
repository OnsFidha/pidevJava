package edu.esprit.controllers;

import edu.esprit.entities.Participation;
import edu.esprit.service.ParticipationService;
import edu.esprit.service.PublicationService;
import edu.esprit.utils.DataSource;
import edu.esprit.utils.SessionManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminPage implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private HBox pub;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private BarChart<String, Number> barChartEvent;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private PieChart stat;

    @FXML
    private HBox event;

    @FXML
    private Label logedUsernamee;

    @FXML
    private HBox produit;



    @FXML
    private HBox recla;

    @FXML
    private HBox reponse;

    @FXML
    private HBox users;

    @FXML
    private NumberAxis yAxis;
    String imagePath = SessionManager.getImage();
    String nameP= SessionManager.getName()+" "+ SessionManager.getPrename();

    @FXML
    private URL location;
    @FXML
    private Button BtnRecherche;
    private Connection cnx;
    private PreparedStatement pst;
    private ResultSet rs;
    @FXML
    private Circle circle;

    private PublicationService publicationService = new PublicationService(); // Remplacez par votre classe de service

    private void getListPub() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminListPub.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void stat() {
        Map<String, Integer> countByType = null;
        try {
            countByType = publicationService.getCountByType();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // CrÃ©er l'axe des catÃ©gories (noms des types de publications)
        xAxis.setLabel("Types de publications");

        // CrÃ©er l'axe des nombres (nombre de publications)
        yAxis.setLabel("Nombre de publications");

        // Ajouter les donnÃ©es de nombre de publications au graphique
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : countByType.entrySet()) {
            String type = entry.getKey();
            int count = entry.getValue();
            XYChart.Data<String, Number> data = new XYChart.Data<>(type, count);
            StackPane stackPane = createDataNode(type, count);
            data.setNode(stackPane);
            series.getData().add(data);
        }

        // Ajouter la sÃ©rie de donnÃ©es au graphique
        barChart.getData().add(series);
    }

    private StackPane createDataNode(String type, int count) {
        StackPane stackPane = new StackPane();
        Label label = new Label(type + ": " + count);
        stackPane.getChildren().add(label);

        stackPane.setOnMouseEntered(event -> {
            label.setVisible(true);
        });

        stackPane.setOnMouseExited(event -> {
            label.setVisible(false);
        });

        label.setVisible(false);
        return stackPane;
    }
    private void statEvent(){

        ParticipationService participationService = new ParticipationService();

        try {
            // Récupérer les statistiques de participation par événement depuis le service
            List<Participation> participationStats = participationService.getParticipationStatsByEvent();

            // Créer une série de données pour le graphique
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (Participation participation : participationStats) {
                series.getData().add(new XYChart.Data<>(String.valueOf(participation.getId_evenment()), participation.getId_user()));
            }



            // Ajouter la série de données au graphique
            barChartEvent.getData().add(series);

            // Configurer les axes du graphique
            CategoryAxis xAxis = (CategoryAxis) barChartEvent.getXAxis();
            xAxis.setLabel("Evenement");

            NumberAxis yAxis = (NumberAxis) barChartEvent.getYAxis();
            yAxis.setLabel("Nombre de participations");
            System.out.println(yAxis);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logedUsernamee.setText(nameP);
        int img = imagePath.lastIndexOf("\\");
        String nomFichier = imagePath.substring(img + 1);
        Image image = new Image("assets/uploads/"+nomFichier);
        circle.setFill(new ImagePattern(image));
        EventHandler<MouseEvent> clickHandler4 = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                getListRecla();
            }
        };

        // Ajouter l'EventHandler au HBox
        recla.setOnMouseClicked(clickHandler4);
        EventHandler<MouseEvent> clickHandler3 = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                getListEvent();
            }
        };

        // Ajouter l'EventHandler au HBox
        event.setOnMouseClicked(clickHandler3);
        EventHandler<MouseEvent> clickHandler2 = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                getListUsers();
            }
        };

        // Ajouter l'EventHandler au HBox
        users.setOnMouseClicked(clickHandler2);
        EventHandler<MouseEvent> clickHandler5= new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                getListProduit();
            }
        };

        // Ajouter l'EventHandler au HBox
        produit.setOnMouseClicked(clickHandler5);
        EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                getListPub();
            }
        };

        // Ajouter l'EventHandler au HBox
        pub.setOnMouseClicked(clickHandler);
        EventHandler<MouseEvent> clickHandler6 = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                getListRep();
            }
        };

        // Ajouter l'EventHandler au HBox
        reponse.setOnMouseClicked(clickHandler6);
        stat();
        statRecla();
        statEvent();
    }

    private void getListRep() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReponseAdmin.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getListProduit() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AdminTemplate.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getListRecla() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationAdmin.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getListEvent() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/baseAdminEvent.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getListUsers() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUser.fxml"));
        try {

            Parent root = loader.load();
            pub.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void statRecla(){
        // Requête SQL pour obtenir les statistiques sur les réclamations par type
        String req = "SELECT type, COUNT(*) AS count FROM Reclamation GROUP BY type";


        try {
            cnx = DataSource.getInstance().getConn();
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

        }
        catch (SQLException e) {
            // Gérer l'exception en cas d'erreur de requête SQL
            System.out.println("Erreur lors de la récupération des statistiques : " + e.getMessage());

            // Afficher une alerte à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.ERROR);
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
}
