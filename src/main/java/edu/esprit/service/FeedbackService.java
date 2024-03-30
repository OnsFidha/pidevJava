package edu.esprit.service;

import edu.esprit.entities.Feedback;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackService implements IService<Feedback>{
    Connection conn= DataSource.getInstance().getConn();

    @Override
    public void ajouter(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO feedback (id_evenement, text) VALUES (?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1,feedback.getId_evenment());
            statement.setString(2, feedback.getText());

            statement.executeUpdate();
            System.out.println("Ajouté avec succés");
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void modifier(Feedback feedback) throws SQLException {
        String sql = "UPDATE feedback SET id_evenement = ?, text = ? WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, feedback.getId_evenment());
            statement.setString(2, feedback.getText());

            statement.setInt(3, feedback.getId());
            statement.executeUpdate();
            System.out.println("modification réussie");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM feedback WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("suppression réussie");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public List<Feedback> getAll() throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT * FROM feedback";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(resultSet.getInt("id"));
                feedback.setId_evenment(resultSet.getInt("id_evenement"));
                feedback.setText(resultSet.getString("text"));

                // publication.setUserId(resultSet.getInt("id_user_id"));

                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return feedbacks;
    }

    @Override
    public Feedback getOneById(int id) throws SQLException {
        Feedback feedback = null;
        String sql = "SELECT * FROM feedback WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                feedback = new Feedback();
                feedback.setId(resultSet.getInt("id"));
                feedback.setId_evenment(resultSet.getInt("id_evenement"));
                feedback.setText(resultSet.getString("text"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return feedback;
    }

}
