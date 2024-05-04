package edu.esprit.controllers;
import edu.esprit.entities.Evenement;
import edu.esprit.service.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.sql.Date;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;
    private EvenementService evenementService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        evenementService = new EvenementService(); // Initialize EvenementService
        try {
            drawCalendar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void backOneMonth(ActionEvent event) throws SQLException {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) throws SQLException {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() throws SQLException {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        LocalDate startDate = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        // Fetch events from the service for the current month
        // Fetch events from the service for the current month
        List<Evenement> calendarEvents = evenementService.getEvenementsByDateRange(startDate, endDate);


        // Create a map to store events by day
        Map<Integer, List<Evenement>> calendarActivityMap = createCalendarMap(calendarEvents);

        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            StackPane stackPane = new StackPane();

            Rectangle rectangle = new Rectangle(100, 100);
            rectangle.setFill(Color.rgb(255, 255, 255)); // Light gray fill color
            rectangle.setStroke(Color.rgb(215, 194, 194)); // Light gray stroke color
            rectangle.setStrokeWidth(2); // Stroke width
            rectangle.setArcWidth(10); // Horizontal corner radius
            rectangle.setArcHeight(10); // Vertical corner radius

// Add drop shadow effect
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(5);
            dropShadow.setColor(Color.rgb(255, 255, 255, 0.65)); // Semi-transparent gray shadow
            rectangle.setEffect(dropShadow);


            Text text = new Text(String.valueOf(date.getDayOfMonth()));
            stackPane.getChildren().addAll(rectangle, text);

            int dayOfMonth = date.getDayOfMonth();

            // Check if there are events for this day and add them to the stack pane
            if (calendarActivityMap.containsKey(dayOfMonth)) {
                createCalendarActivity(calendarActivityMap.get(dayOfMonth), rectangle.getHeight(), rectangle.getWidth(), stackPane);
            }

            calendar.getChildren().add(stackPane);
        }

    }

    private Map<Integer, List<Evenement>> createCalendarMap(List<Evenement> calendarActivities) {
        Map<Integer, List<Evenement>> calendarActivityMap = new HashMap<>();

        for (Evenement activity : calendarActivities) {
            // Get the start date of the event
            java.sql.Date startDate = (Date) activity.getDateDebut();

            // Convert the start date to a LocalDate
            LocalDate activityDate = startDate.toLocalDate();

            // Get the day of the month
            int dayOfMonth = activityDate.getDayOfMonth();

            // Add the activity to the list for the corresponding day
            if (!calendarActivityMap.containsKey(dayOfMonth)) {
                calendarActivityMap.put(dayOfMonth, new ArrayList<>());
            }
            calendarActivityMap.get(dayOfMonth).add(activity);
        }
        return calendarActivityMap;
    }


    private void createCalendarActivity(List<Evenement> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if(k >= 2) {
                Text moreActivities = new Text("...");
                moreActivities.setFill(Color.WHITE);
                moreActivities.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    // On ... click, print all activities for given date
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getNom());
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            text.setTextAlignment(TextAlignment.CENTER);
            calendarActivityBox.getChildren().add(text);
            int finalK = k;
            text.setOnMouseClicked(mouseEvent -> {
                redirectToEventPage(calendarActivities.get(finalK));
            });
        }
        calendarActivityBox.setAlignment(Pos.CENTER);
        calendarActivityBox.setTranslateY(rectangleHeight * 0.1);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color: #ffb7b7; -fx-background-radius: 10px");

        stackPane.getChildren().add(calendarActivityBox);
    }
    private void redirectToEventPage(Evenement selectedEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvent.fxml"));
            Parent root = loader.load();

            // Pass event data to the event page controller
            AfficherEventController eventController = loader.getController();
            eventController.setData(selectedEvent);


            // Display the event page in a new window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error loading the event page
        }
    }

    @FXML
    void goBack(MouseEvent event) {
        try {
            // Load the previous FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenements.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception gracefully
            // Show an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Page Navigation Error");
            alert.setContentText("An error occurred while navigating to the previous page. Please try again.");
            alert.showAndWait();
        }

    }



}