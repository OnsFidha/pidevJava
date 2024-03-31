package edu.esprit.service;

import edu.esprit.entities.Commentaire;
import edu.esprit.utils.DataSource;

import java.sql.SQLException;
import java.sql.*;
import java.util.*;


public class CommentaireService implements IService<Commentaire> {
    Connection conn = DataSource.getInstance().getConn();

    @Override
    public void ajouter(Commentaire commentaire) throws SQLException {
        String sql = "INSERT INTO commentaire (id_publication_id, text) VALUES (?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, commentaire.getId_publication());
            statement.setString(2, commentaire.getText());
          //  statement.setInt(4, commentaire.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void modifier(Commentaire commentaire) throws SQLException {
        String sql = "UPDATE commentaire SET  text = ? WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, commentaire.getText());
           // statement.setInt(4, commentaire.getUserId());
            statement.setInt(2, commentaire.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM commentaire WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Commentaire> getAll() throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String sql = "SELECT * FROM commentaire";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Commentaire commentaire = new Commentaire();
                commentaire.setId(resultSet.getInt("id"));
                commentaire.setId_publication(resultSet.getInt("id_publication_id"));
                commentaire.setText(resultSet.getString("text"));
                commentaire.setDateCreation(resultSet.getTimestamp("date_creation"));
               // commentaire.setUserId(resultSet.getInt("id_user_id"));
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return commentaires;
    }

    @Override
    public Commentaire getOneById(int id) throws SQLException {
        String sql = "SELECT * FROM commentaire WHERE id = ?";
        Commentaire commentaire = null;
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                commentaire = new Commentaire();
                commentaire.setId(resultSet.getInt("id"));
                commentaire.setId_publication(resultSet.getInt("id_publication_id"));
                commentaire.setText(resultSet.getString("text"));
                commentaire.setDateCreation(resultSet.getTimestamp("date_creation"));
              //  commentaire.setUserId(resultSet.getInt("id_user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return commentaire;
    }
}