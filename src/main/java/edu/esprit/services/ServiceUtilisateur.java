package edu.esprit.services;

import edu.esprit.interfaces.IUtilisateur;
import edu.esprit.entities.Utilisateur;
import edu.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur implements IUtilisateur<Utilisateur> {
    private Connection cnx;
    public ServiceUtilisateur() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void Add(Utilisateur user) {
        String qry = "INSERT INTO `user`(`name`, `prename`, `email`,`password`, `phone`,`roles`, `image`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, user.getName());
            stm.setString(2, user.getPrename());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getPassword());
            stm.setInt(5, user.getPhone());
            stm.setString(6, user.getRoles());
            stm.setString(7, user.getImage());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Utilisateur> afficher() {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT `id`, `name`, `prename`, `email`, `password`, `phone`, `roles`, `image` FROM `user`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPrename(rs.getString("prename"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getInt("phone"));
                user.setRoles(rs.getString("roles"));
                user.setImage(rs.getString("image"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    @Override
    public List<Utilisateur> TriparName() {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT `id`, `name`, `prename`, `email`, `password`, `phone`, `roles`, `image` FROM `user` ORDER BY `name`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPrename(rs.getString("prename"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getInt("phone"));
                user.setRoles(rs.getString("roles"));
                user.setImage(rs.getString("image"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    @Override
    public List<Utilisateur> TriparEmail() {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT `id`, `name`, `prename`, `email`, `password`, `phone`, `roles`, `image` FROM `user` ORDER BY `email`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPrename(rs.getString("prename"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getInt("phone"));
                user.setRoles(rs.getString("roles"));
                user.setImage(rs.getString("image"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    @Override
    public List<Utilisateur> Rechreche(String recherche) {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT `id`, `name`, `prename`, `email`, `password`, `phone`, `roles`, `image` FROM `user` WHERE `name` LIKE '%" + recherche + "%' OR `prename` LIKE '%" + recherche + "%' OR `email`LIKE '%" + recherche + "%'";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPrename(rs.getString("prename"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getInt("phone"));
                user.setRoles(rs.getString("roles"));
                user.setImage(rs.getString("image"));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    @Override //manich nestaamel fiha
    public ArrayList<Utilisateur> getAll() {
        ArrayList<Utilisateur> users = new ArrayList();
        String qry = "SELECT * FROM `user`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPrename(rs.getString("prename"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getInt("phone"));
                user.setRoles(rs.getString("roles"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void Update(Utilisateur user) {
       try {
            String qry = "UPDATE `user` SET `name`=?,`prename`=?,`email`=?,`password`=?,`phone`=?, `roles`=?, `image`=? WHERE `id`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, user.getName());
            stm.setString(2, user.getPrename());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getPassword());
            stm.setInt(5, user.getPhone());
            stm.setString(6, user.getRoles());
            stm.setString(7, user.getImage());
            stm.setInt(8, user.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void Delete(Utilisateur user) {
        try {
            String qry = "DELETE FROM `user` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, user.getId());
            smt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void DeleteByID(int id) {
        try {
            String qry = "DELETE FROM `user` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, id);
            smt.executeUpdate();
            System.out.println("Suppression Effectué");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean updatePassword(String email, String newPassword) {
        String req = "UPDATE `user` SET `password` = ? WHERE `email` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, newPassword); // You should hash the password
            ps.setString(2, email);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkUserExists(String email) {
        String req = "SELECT count(1) FROM `user` WHERE `email`=?";
        boolean exists = false;
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, email);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                exists = res.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking if user exists: " + e.getMessage());
        }
        return exists;
    }
    //email
    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*\\.?[a-zA-Z0-9_+&*-]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    //phone number
    public boolean isValidPhoneNumber(int phone) {
        String phoneStr = String.valueOf(phone);
        return phoneStr.length() == 8;
    }

}