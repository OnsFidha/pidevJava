package edu.esprit.service;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ReponseService implements IService <Reponse> {

    Connection conn= DataSource.getInstance().getConn();

    @Override
    public void ajouter(Reponse reponse) throws SQLException {
        String sql = "INSERT INTO reponse (relation_id, reponse, date_reponse) VALUES (?, ?,?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1,reponse.getRelation_id());
            statement.setString(2, reponse.getReponse());
            statement.setTimestamp(3, new Timestamp(reponse.getDate_reponse().getTime()));

            statement.executeUpdate();
            System.out.println("Bien ajoutee");
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void modifier(Reponse reponse) throws SQLException {
        //String sql = "UPDATE reponse SET relation_id = ?, reponse = ? WHERE id = ?";
        String sql = "UPDATE reponse SET  reponse = ? WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            //statement.setInt(1, reponse.getRelation_id());
            statement.setString(1, reponse.getReponse());

            statement.setInt(2, reponse.getId());
            statement.executeUpdate();
            System.out.println("modification effectuée");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM reponse WHERE id = ?";

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
    public List<Reponse> getAll() throws SQLException {
        List<Reponse> reponses = new ArrayList<>();
        String sql = "SELECT * FROM reponse";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Reponse reponse = new Reponse();
                reponse.setId(resultSet.getInt("id"));
                reponse.setRelation_id(resultSet.getInt("Relation_id"));
                reponse.setReponse(resultSet.getString("reponse"));
                reponse.setDate_reponse(resultSet.getDate("date_reponse"));

                // publication.setUserId(resultSet.getInt("id_user_id"));

                reponses.add(reponse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return reponses;
    }

    @Override
    public Reponse getOneById(int id) throws SQLException {
        Reponse reponse = null;
        String sql = "SELECT * FROM reponse WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                reponse = new Reponse();
                reponse.setId(resultSet.getInt("id"));
                reponse.setRelation_id(resultSet.getInt("Relation_id"));
                reponse.setReponse(resultSet.getString("reponse"));
                reponse.setDate_reponse(resultSet.getDate("date_reponse"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return reponse;
    }

    public List<Reponse> refreshRep(){
        List<Reponse> myList = new ArrayList<>();

        try{
            String requete = "SELECT * FROM reponse";
            Statement st = conn.createStatement();
            ResultSet rs =  st.executeQuery(requete);

            while(rs.next()){
                Reponse reponse = new Reponse();
                reponse.setId(rs.getInt("id"));
               // reponse.setRelation_id(rs.getInt("relation_id"));
                reponse.setReponse(rs.getString("reponse"));
                reponse.setDate_reponse(rs.getDate("date_reponse"));

                myList.add(reponse);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return myList;
    }

    public Reponse getReponseByReclamationId(int reclamationId) {
        Reponse reponse = null;
        try {
            String req = "SELECT * FROM reponse WHERE relation_id = ? LIMIT 1";
            PreparedStatement sttm = conn.prepareStatement(req);
            sttm.setInt(1, reclamationId);
            ResultSet rst = sttm.executeQuery();
            if (rst.next()) {
                reponse = new Reponse();
                reponse.setId(rst.getInt("id"));
                reponse.setReponse(rst.getString("reponse"));
                reponse.setDate_reponse(rst.getDate("date_reponse"));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reponse;
    }


}
