package edu.esprit.service;

import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;


public class ServiceUser implements IService<User> {
    Connection conn = DataSource.getInstance().getConn();
    static IService<User> serviceUser;

    private ServiceUser() {
        System.out.println("ServiceCommande crée");
    }

    public static IService<User> getInstance() {
        if (serviceUser == null)
            serviceUser = new ServiceUser();
        return serviceUser;
    }

    @Override
    public void ajouter(User commande) {

    }

    @Override
    public void modifier(User categorie) {
    }

    @Override
    public void supprimer(int id) {
    }

    @Override
    public List<User> getAll() {
        return null;
    }


    @Override
    public User getOneById(int id) {
        String req ="select * from user WHERE id=?";
        try {
            PreparedStatement pst= conn.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                String nom = rs.getString("name");
                String prename = rs.getString("prename");
                String email = rs.getString("email");
                User user = new User(nom, prename, email);
                user.setId(id);
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
