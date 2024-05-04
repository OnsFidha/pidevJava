package edu.esprit.service;

import edu.esprit.entities.Participation;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipationService implements IService<Participation> {

    Connection conn= DataSource.getInstance().getConn();
    @Override
    public void ajouter(Participation participation) throws SQLException {
        // Insertion dans la table participation
        String sql = "INSERT INTO participation () VALUES ()";
        try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Exécution de la requête
            statement.executeUpdate();

            // Récupération de l'ID généré pour la participation
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                participation.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Échec de la récupération de l'ID généré pour la participation.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        // Insertion dans la table participation_evenement
        String sqlInsertEvent = "INSERT INTO participation_evenement (participation_id, evenement_id) VALUES (?, ?)";
        try (PreparedStatement insertEventStatement = conn.prepareStatement(sqlInsertEvent)) {
            insertEventStatement.setInt(1, participation.getId());
            insertEventStatement.setInt(2, participation.getId_evenment());
            insertEventStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        // Insertion dans la table participation_user
        String sqlInsertUser = "INSERT INTO participation_user (participation_id, user_id) VALUES (?, ?)";
        try (PreparedStatement insertUserStatement = conn.prepareStatement(sqlInsertUser)) {
            insertUserStatement.setInt(1, participation.getId());
            insertUserStatement.setInt(2, participation.getId_user());
            insertUserStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean hasParticipated(int userId, int eventId) {
        String sql = "SELECT COUNT(*) FROM participation_user pu " +
                "JOIN participation_evenement pe ON pu.participation_id = pe.participation_id " +
                "WHERE pu.user_id = ? AND pe.evenement_id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, eventId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Participation> getParticipationStatsByEvent() throws SQLException {
        List<Participation> statsData = new ArrayList<>();
        String sql = "SELECT evenement_id, COUNT(*) AS participation_count " +
                "FROM participation_evenement " +
                "GROUP BY evenement_id";


             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery() ;

            while (resultSet.next()) {
                int eventId = resultSet.getInt("evenement_id");
                int participationCount = resultSet.getInt("participation_count");
                statsData.add(new Participation(eventId, participationCount));
            }


        return statsData;
    }
    public int getParticipationCountByEvent(int eventId) throws SQLException {
        // Execute a SQL query to count participations for the given event ID
        String sql = "SELECT COUNT(*) AS participation_count FROM participation_evenement WHERE evenement_id = ?";
        int participationCount = 0;

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    participationCount = resultSet.getInt("participation_count");
                }
            }
        }

        return participationCount;
    }


    @Override
    public void modifier(Participation participation) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<Participation> getAll() throws SQLException {
        return null;
    }

    @Override
    public Participation getOneById(int id) throws SQLException {
        return null;
    }

}
