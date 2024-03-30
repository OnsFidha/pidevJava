
package edu.esprit.controllers;

        import com.mysql.cj.conf.BooleanProperty;
        import edu.esprit.entities.Reclamation;
        import edu.esprit.service.ReclamationService;
        import javafx.beans.binding.Bindings;
        import javafx.beans.binding.StringBinding;
        import javafx.beans.property.SimpleBooleanProperty;
        import javafx.beans.value.ObservableBooleanValue;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.Button;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.shape.Circle;

        import java.net.URL;
        import java.sql.SQLException;
        import java.util.List;
        import java.util.ResourceBundle;


public class AfficherReclamation implements Initializable {

    @FXML
    private TableView<Reclamation> tableauReclam;
    @FXML
    private TableColumn<?, ?> DateReclam;
    ObservableList myList ;
    @FXML
    private Button ModifierReclam;

    @FXML
    private Button addReclam;

    @FXML
    private Circle circle;

    @FXML
    private TableColumn<?, ?> descReclam;

    @FXML
    private TableColumn<?, ?> etatReclam;

    @FXML
    private Button refresh;

    @FXML
    private Button supprimerReclam;

    @FXML
    private TableColumn<?, ?> typeReclam;

    @FXML
    private Button verifier;

    @FXML
    void addReclamation(ActionEvent event) {

    }

    @FXML
    void deleteReclamation(ActionEvent event) {

    }

    @FXML
    void refreshReclamation(ActionEvent event) {

    }

    @FXML
    void updateReclamation(ActionEvent event) {

    }

    @FXML
    void verifierReponseReclamation(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            // TODO
        try {
            afficherReclam();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void afficherReclam() throws SQLException {
      /* ServiceReclamation sr = new ServiceReclamation();
        ObservableList<Reclamation> o = FXCollections.observableArrayList(sr.afficherReclamation());*/
        ReclamationService sr = new ReclamationService();
        List<Reclamation> reclam = sr.getAll();
        myList = FXCollections.observableList(reclam);
        tableauReclam.setItems(myList);

        descReclam.setCellValueFactory(new PropertyValueFactory<>("description_reclamation"));
        typeReclam.setCellValueFactory(new PropertyValueFactory<>("type_reclamation"));


        etatReclam.setCellValueFactory(new PropertyValueFactory<>("etat_reclamation"));
        DateReclam.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));

    }
}
