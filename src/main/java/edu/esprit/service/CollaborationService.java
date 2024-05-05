package edu.esprit.service;


import edu.esprit.entities.Collaboration;
import edu.esprit.entities.Publication;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CollaborationService implements IService<Collaboration>{
    Connection conn = DataSource.getInstance().getConn();

    @Override
    public void ajouter(Collaboration collaboration) throws SQLException {
        String sql = "INSERT INTO collaboration (disponibilite, competence, cv) VALUES (?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, collaboration.getDisponibilite());
            statement.setString(2, collaboration.getCompetence());
            statement.setString(3, collaboration.getCv());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idCollaboration = generatedKeys.getInt(1); // Récupérer l'ID généré pour la nouvelle collaboration
                collaboration.setId(idCollaboration); // Mettre à jour l'ID de la collaboration avec l'ID généré
                // Maintenant, vous pouvez ajouter la nouvelle collaboration à la table de liaison avec Publication
                    ajouterPublicationCollaboration(collaboration.getId_publication(), idCollaboration);
                    ajouterUserCollaboration(collaboration.getId_user(), idCollaboration);


            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void ajouterUserCollaboration(int idUser, int idCollaboration) throws SQLException {
        String sql = "INSERT INTO collaboration_user ( collaboration_id,user_id) VALUES (?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idCollaboration);
            statement.setInt(2, idUser);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    private void ajouterPublicationCollaboration(int idPublication, int idCollaboration) throws SQLException {
        String sql = "INSERT INTO collaboration_publication (publication_id, collaboration_id) VALUES (?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idPublication);
            statement.setInt(2, idCollaboration);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public void modifier(Collaboration collaboration) throws SQLException {
        String sql = "UPDATE collaboration SET disponibilite = ?, competence = ?, cv = ? WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, collaboration.getDisponibilite());
            statement.setString(2, collaboration.getCompetence());
            statement.setString(3, collaboration.getCv());
            statement.setInt(4, collaboration.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM collaboration WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public List<Collaboration> getListByIdPublication(int publicationId) throws SQLException {
        List<Collaboration> collaborations = new ArrayList<>();
        String sql = "SELECT c.* FROM collaboration c " +
                "JOIN collaboration_publication cp ON c.id = cp.collaboration_id " +
                "WHERE cp.publication_id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, publicationId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Collaboration collaboration = new Collaboration();
                collaboration.setId(resultSet.getInt("id"));
                collaboration.setDisponibilite(resultSet.getString("disponibilite"));
                collaboration.setCompetence(resultSet.getString("competence"));
                collaboration.setCv(resultSet.getString("cv"));
                collaborations.add(collaboration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return collaborations;
    }
    @Override
    public List getAll() throws SQLException {
        List  <Collaboration> collaborations = new ArrayList<>();
        String sql = "SELECT * FROM collaboration";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Collaboration collaboration = new Collaboration();
                collaboration.setId(resultSet.getInt("id"));
                collaboration.setDisponibilite(resultSet.getString("disponibilite"));
                collaboration.setCompetence(resultSet.getString("competence"));
                collaboration.setCv(resultSet.getString("cv"));
                collaborations.add(collaboration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return collaborations;

    }

    @Override
    public Collaboration getOneById(int id) throws SQLException {
        Collaboration collaboration = null;
        String sql = "SELECT * FROM collaboration WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                collaboration = new Collaboration();
                collaboration.setId(resultSet.getInt("id"));
                collaboration.setDisponibilite(resultSet.getString("disponibilite"));
                collaboration.setCompetence(resultSet.getString("competence"));
                collaboration.setCv(resultSet.getString("cv"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return collaboration;
    }

    public String getUserNameByCollaborationId(int collaborationId) throws SQLException {
        String userName = null;
        String sql = "SELECT u.name, u.prename FROM user u JOIN collaboration_user cu ON u.id = cu.user_id WHERE cu.collaboration_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, collaborationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nom = resultSet.getString("name");
                    String prenom = resultSet.getString("prename");
                    userName = nom + " " + prenom;
                }
            }
        }
        return userName;
    }

    public String getUserImageByCollaborationId(int collaborationId) throws SQLException {
        String userImage = null;
        String sql = "SELECT u.image FROM user u JOIN collaboration_user cu ON u.id = cu.user_id WHERE cu.collaboration_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, collaborationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userImage = resultSet.getString("image");
                }
            }
        }
        return userImage;
    }
}
