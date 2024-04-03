package edu.esprit.service;

import edu.esprit.entities.Reclamation;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ReclamationService implements IService <Reclamation> {
    Connection conn= DataSource.getInstance().getConn();
    @Override
    public void ajouter(Reclamation reclamation) throws SQLException{
        String sql = "INSERT INTO reclamation (type, description,etat,date_creation) VALUES (?, ?,?,?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1,reclamation.getType());
            statement.setString(2, reclamation.getDescription());
            statement.setBoolean(3, reclamation.isEtat());
            statement.setTimestamp(4, new Timestamp(reclamation.getDate_creation().getTime()));

            //statement.setTimestamp(4, new Timestamp(reclamation.getDate_creation().getTime()));
            statement.executeUpdate();
            System.out.println("Bien ajoutee");
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Override
    public void modifier(Reclamation reclamation) throws SQLException{
        String sql = "UPDATE reclamation SET type = ?, description = ?, etat= ? WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, reclamation.getType());
            statement.setString(2, reclamation.getDescription());

            statement.setBoolean(3, reclamation.isEtat());
            statement.setInt(4, reclamation.getId());

            statement.executeUpdate();
            System.out.println("modification effectuée");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void supprimer(int id) throws SQLException{
        String sql = "DELETE FROM reclamation WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("suppression effectuée");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Reclamation> getAll() throws SQLException {
        List<Reclamation> reclamations = new ArrayList<>();
        String sql = "SELECT * FROM reclamation";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(resultSet.getInt("id"));
                reclamation.setType(resultSet.getString("type"));
                reclamation.setDescription(resultSet.getString("description"));
                reclamation.setEtat(resultSet.getBoolean("etat"));
                reclamation.setDate_creation(resultSet.getDate("date_creation"));

                // publication.setUserId(resultSet.getInt("id_user_id"));

                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return reclamations;
    }

    @Override
    public Reclamation getOneById(int id) throws SQLException {
        Reclamation reclamation = null;
        String sql = "SELECT * FROM reclamation WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                reclamation = new Reclamation();
                reclamation.setId(resultSet.getInt("id"));
                reclamation.setType(resultSet.getString("type"));
                reclamation.setDescription(resultSet.getString("description"));
                reclamation.setEtat(resultSet.getBoolean("etat"));
                reclamation.setDate_creation(resultSet.getDate("date_creation"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return reclamation;
    }

    public List<Reclamation> refreshReclam(){
        List<Reclamation> myList = new ArrayList<>();

        try{
            String requete = "SELECT * FROM reclamation";
            Statement st = conn.createStatement();
            ResultSet rs =  st.executeQuery(requete);

            while(rs.next()){
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setType(rs.getString("type"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setEtat(rs.getBoolean("etat"));
                reclamation.setDate_creation(rs.getDate("date_creation"));

                myList.add(reclamation);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return myList;
    }

}
