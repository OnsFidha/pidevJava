package edu.esprit.controllers;

import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceUtilisateur;
import edu.esprit.utils.MyDataBase;
import edu.esprit.utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.chart.PieChart;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;

public class AdminUserController implements Initializable {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    public TextField idtf;
    @FXML
    public TextField nametf;
    @FXML
    public TextField prenametf;
    @FXML
    public TextField emailtf;
    @FXML
    public TextField mdptf;
    @FXML
    public TextField phonetf;
    @FXML
    public TextField pdptf;
    @FXML
    public ComboBox rolescb;
    @FXML
    public TabPane usertp;
    @FXML
    public Tab gusertab;
    @FXML
    public Tab listusertab;
    @FXML
    public GridPane userContainer;
    @FXML
    public ImageView imagepdp;
    @FXML
    private Label uinfolabel;
    @FXML
    private ImageView logedUserimage;
    @FXML
    private Label logedUsernamee;
    @FXML
    private TextField usersearch;
    @FXML
    private PieChart statisticPieChart;
    @FXML
    private Label adminCountLabel;
    @FXML
    private Label userCountLabel;
    ObservableList<String> RolesList = FXCollections.observableArrayList("User", "Admin");
    private final ServiceUtilisateur UserS = new ServiceUtilisateur();
    private Connection cnx;
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        load();
        rolescb.setValue("User");
        rolescb.setItems(RolesList);
        String imagePath = SessionManager.getImage();
        String nameP= SessionManager.getName()+" "+SessionManager.getPrename();

        logedUsernamee.setText(nameP);
        int adminCount = UserS.getCountByRole("Admin");
        int userCount = UserS.getCountByRole("User");
        adminCountLabel.setText(Integer.toString(adminCount));
        userCountLabel.setText(Integer.toString(userCount));

        // Ajouter les données au graphique à secteurs
        statisticPieChart.getData().add(new PieChart.Data("Administrateurs", adminCount));
        statisticPieChart.getData().add(new PieChart.Data("Utilisateurs", userCount));

    }

    private boolean emailExists(String email) throws SQLException
    {
        cnx = MyDataBase.getInstance().getCnx();
        String query = "SELECT * FROM `user` WHERE email=?";
        PreparedStatement statement = cnx.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    @FXML
    public void AjouterUser(ActionEvent actionEvent) throws SQLException
    {
        String NAME = nametf.getText();
        String PRENAME = prenametf.getText();
        String EMAIL = emailtf.getText();
        String MDP = mdptf.getText();
        int PHONE = Integer.parseInt(phonetf.getText());
        String ROLES = (String) rolescb.getValue();
        String IMAGE = pdptf.getText();
        if (EMAIL.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@(esprit\\.tn|gmail\\.com)$")) {
            if (phonetf.getText().matches("\\d{8}")) {
                if (!emailExists(EMAIL)) {
                    UserS.Add(new Utilisateur(0, NAME, PRENAME, EMAIL, MDP, PHONE, ROLES, IMAGE));
                    uinfolabel.setText("Ajout Effectue");
                } else {
                    uinfolabel.setText("Email déjà existe");
                }
            } else {
                uinfolabel.setText("N° Telephone est invalide");
            }
        } else {
            uinfolabel.setText("Email est invalide");
        }
    }

    @FXML
    public void ModifierUser(ActionEvent actionEvent)
    {
        int ID = Integer.parseInt(idtf.getText());
        String NAME = nametf.getText();
        String PRENAME = prenametf.getText();
        String EMAIL = emailtf.getText();
        String MDP = mdptf.getText();
        int PHONE = Integer.parseInt(phonetf.getText());
        String ROLES = (String) rolescb.getValue();
        String IMAGE = pdptf.getText();
        if (EMAIL.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@(esprit\\.tn|gmail\\.com)$")) {
            if (phonetf.getText().matches("\\d{8}")) {
                UserS.Update(new Utilisateur(ID, NAME, PRENAME, EMAIL, MDP, PHONE, ROLES, IMAGE));
                uinfolabel.setText("Modification Effectue");
                load();
            } else {
                uinfolabel.setText("N° Telephone est invalide");
            }
        } else {
            uinfolabel.setText("Email est invalide");
        }

    }

    @FXML
    public void pdpup(ActionEvent actionEvent)
    {
        String imagePath = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        Stage stage = (Stage) nametf.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                Path destinationFolder = Paths.get("src/main/resources/assets/uploads");
                if (!Files.exists(destinationFolder)) {
                    Files.createDirectories(destinationFolder);
                }
                String fileName = UUID.randomUUID().toString() + "_" + selectedFile.getName();
                Path destinationPath = destinationFolder.resolve(fileName);
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                imagePath = destinationPath.toString();
                System.out.println("Image uploaded successfully: " + imagePath);
                pdptf.setText(imagePath);
                if (imagePath != null) {
                    try {
                        File file = new File(imagePath);
                        FileInputStream inputStream = new FileInputStream(file);
                        Image image = new Image(inputStream);
                        imagepdp.setImage(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load()
    {
        int column = 0;
        int row = 1;
        try {
            for (Utilisateur user : UserS.afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserController cardC = fxmlLoader.getController();
                cardC.setData(user);
                if (column == 2) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void TriName(ActionEvent actionEvent)
    {
        int column = 0;
        int row = 1;
        try {
            for (Utilisateur user : UserS.TriparName()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserController cardC = fxmlLoader.getController();
                cardC.setData(user);
                if (column == 2) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void refresh(ActionEvent actionEvent)
    {
        load();
    }

    @FXML
    public void TriEmail(ActionEvent actionEvent)
    {
        int column = 0;
        int row = 1;
        try {
            for (Utilisateur user : UserS.TriparEmail()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserController cardC = fxmlLoader.getController();
                cardC.setData(user);
                if (column == 2) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void RechercheName(ActionEvent actionEvent)
    {
        int column = 0;
        int row = 1;
        String recherche = usersearch.getText();
        try {
            userContainer.getChildren().clear();
            for (Utilisateur user : UserS.Rechreche(recherche)){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserController cardC = fxmlLoader.getController();
                cardC.setData(user);
                if (column == 2) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Menu1(ActionEvent actionEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Charts.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool - Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Deconnection(ActionEvent actionEvent)
     {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            SessionManager.cleanUserSession();
            stage.setTitle("artistool - Connection");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reclamAdmin(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool - List Reclamations");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @FXML
    void reponseAdmin(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReponseAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("artistool - List Reponses");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
