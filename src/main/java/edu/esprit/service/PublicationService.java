package edu.esprit.service;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Publication;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;


public class PublicationService implements IService <Publication> {
    Connection conn= DataSource.getInstance().getConn();
    @Override
    public void ajouter(Publication publication) throws SQLException {
        String sql = "INSERT INTO publication (type, text, lieu, avis, photo,id_user_id) VALUES (?, ?, ?, ?, ? ,?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, publication.getType());
            statement.setString(2, publication.getText());
            statement.setString(3, publication.getLieu());
            statement.setInt(4, publication.getAvis());
            statement.setString(5, publication.getPhoto());
            statement.setInt(6, publication.getId_user_id());
            statement.executeUpdate();
        }
     catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
    }

    @Override
    public void modifier(Publication publication) throws SQLException {
        String sql = "UPDATE publication SET type = ?, text = ?, lieu = ?, photo = ?, date_modification = CURRENT_TIMESTAMP WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, publication.getType());
            statement.setString(2, publication.getText());
            statement.setString(3, publication.getLieu());
            statement.setString(4, publication.getPhoto());
            statement.setInt(5, publication.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM publication WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Publication> getAll() throws SQLException {
        List<Publication> publications = new ArrayList<>();
        String sql = "SELECT * FROM publication";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Publication publication = new Publication();
                publication.setId(resultSet.getInt("id"));
                publication.setType(resultSet.getString("type"));
                publication.setText(resultSet.getString("text"));
                publication.setLieu(resultSet.getString("lieu"));
                publication.setAvis(resultSet.getInt("avis"));
                publication.setPhoto(resultSet.getString("photo"));
                publication.setDateCreation(resultSet.getDate("date_creation"));
                publication.setDateModification(resultSet.getDate("date_modification"));
                publication.setId_user_id(resultSet.getInt("id_user_id"));
                publications.add(publication);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return publications;
    }


    public Map<String, Integer> getCountByType() throws SQLException {
        Map<String, Integer> countByType = new HashMap<>();
        String sql = "SELECT type, COUNT(*) AS count FROM publication GROUP BY type";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                int count = resultSet.getInt("count");
                countByType.put(type, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return countByType;
    }

    public String getUserNamePrenameByPublicationId(int publicationId) throws SQLException {
        String userNamePrename = null;
        String sql = "SELECT u.name, u.prename FROM publication p JOIN user u ON p.id_user_id = u.id WHERE p.id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, publicationId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userNamePrename = resultSet.getString("name") + " " + resultSet.getString("prename");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return userNamePrename;
    }

    @Override
    public Publication getOneById(int id) throws SQLException {
        Publication publication = null;
        String sql = "SELECT * FROM publication WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                publication = new Publication();
                publication.setId(resultSet.getInt("id"));
                publication.setType(resultSet.getString("type"));
                publication.setText(resultSet.getString("text"));
                publication.setLieu(resultSet.getString("lieu"));
                publication.setAvis(resultSet.getInt("avis"));
                publication.setPhoto(resultSet.getString("photo"));
                publication.setDateCreation(resultSet.getDate("date_creation"));
                publication.setDateModification(resultSet.getDate("date_modification"));
                publication.setId_user_id(resultSet.getInt("id_user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return publication;
    }
    public List<Commentaire> getCommentairesByPublicationId(int publicationId) throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String sql = "SELECT * FROM commentaire WHERE id_publication_id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, publicationId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Commentaire commentaire = new Commentaire();
                commentaire.setId(resultSet.getInt("id"));
                commentaire.setText(resultSet.getString("text"));
                //  commentaire.setInt(4, resultSet.getUserId());
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return commentaires;
    }


}
