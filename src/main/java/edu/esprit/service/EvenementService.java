package edu.esprit.service;

import edu.esprit.entities.Evenement;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements IService <Evenement>{



        Connection conn= DataSource.getInstance().getConn();
        @Override
        public void ajouter(Evenement event) throws SQLException {
            String sql = "INSERT INTO evenement (nom, description, lieu, nbreParticipants, nbreMax, dateDebut, dateFin, image) VALUES (?, ?, ?, ?, ?,?,?,?)";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, event.getNom());
                statement.setString(2, event.getDescription());
                statement.setString(3, event.getLieu());
                statement.setInt(4, event.getNbreParticipants());
                statement.setInt(5, event.getNbreMax());
                statement.setDate(6, new Date(event.getDateFin().getTime()));
                statement.setDate(7, new Date(event.getDateDebut().getTime()));

                statement.setString(8, event.getImage());
                statement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }

        @Override
        public void modifier(Evenement event) throws SQLException {
            String sql = "UPDATE evenement SET nom = ?, description = ?, lieu = ?, nbreParticipants = ?, nbreMax = ?, dateDebut = ?, dateFin = ?, image = ? WHERE id = ?";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, event.getNom());
                statement.setString(2, event.getDescription());
                statement.setString(3, event.getLieu());
                statement.setInt(4, event.getNbreParticipants());
                statement.setInt(5, event.getNbreMax());
                statement.setDate(6, new java.sql.Date(event.getDateFin().getTime()));
                statement.setDate(7, new java.sql.Date(event.getDateDebut().getTime()));
                statement.setString(8, event.getImage());
                statement.setInt(9, event.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }

        @Override
        public void supprimer(int id) throws SQLException {
            String sql = "DELETE FROM evenement WHERE id = ?";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }

        @Override
        public List<Evenement> getAll() throws SQLException {
            List<Evenement> events = new ArrayList<>();
            String sql = "SELECT * FROM evenement";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Evenement event = new Evenement();
                    event.setId(resultSet.getInt("id"));
                    event.setNom(resultSet.getString("nom"));
                    event.setDescription(resultSet.getString("description"));
                    event.setLieu(resultSet.getString("lieu"));
                    event.setDateDebut(resultSet.getDate("dateDebut"));
                    event.setDateFin(resultSet.getDate("dateFin"));
                    event.setImage(resultSet.getString("image"));
                    event.setNbreParticipants(resultSet.getInt("nbreParticipants"));
                    event.setNbreMax(resultSet.getInt("nbreMax"));
                    // event.setUserId(resultSet.getInt("id_user_id"));

                    events.add(event);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }

            return events;
        }

        @Override
        public Evenement getOneById(int id) throws SQLException {
            Evenement event = null;
            String sql = "SELECT * FROM evenement WHERE id = ?";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    event = new Evenement();
                    event.setId(resultSet.getInt("id"));
                    event.setNom(resultSet.getString("nom"));
                    event.setDescription(resultSet.getString("description"));
                    event.setLieu(resultSet.getString("lieu"));
                    event.setDateDebut(resultSet.getDate("dateDebut"));
                    event.setDateFin(resultSet.getDate("dateFin"));
                    event.setImage(resultSet.getString("image"));
                    event.setNbreParticipants(resultSet.getInt("nbreparticipants"));
                    event.setNbreMax(resultSet.getInt("nbreMax"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }

            return event;
        }


}