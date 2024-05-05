package edu.esprit.services;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.esprit.interfaces.IUtilisateur;
import edu.esprit.entities.Utilisateur;
import edu.esprit.utils.MyDataBase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(newPassword.getBytes());
            //String mdpHash = Utility.toHexString(hash);
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hash) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            String newPasswordHashed = hexString.toString();
            ps.setString(1, newPasswordHashed); // You should hash the password
            ps.setString(2, email);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
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
    public boolean isValidPassword(String password) {
        String mdpreg = String.valueOf(password);
        int minLength = 8; // Longueur minimale du mot de passe
        int maxLength = 20; // Longueur maximale du mot de passe

        // Vérifier la longueur minimale et maximale
        if (mdpreg.length() < minLength || mdpreg.length() > maxLength) {
            return false;
        }

        // Vérifier s'il y a au moins un chiffre et un caractère alphabétique
        Pattern digitPattern = Pattern.compile("\\d");
        Pattern letterPattern = Pattern.compile("[a-zA-Z]");

        Matcher digitMatcher = digitPattern.matcher(mdpreg);
        Matcher letterMatcher = letterPattern.matcher(mdpreg);

        return digitMatcher.find() && letterMatcher.find();
    }

    public int getCountByRole(String roles) {
        int count = 0;
        String query = "SELECT COUNT(*) AS count FROM `user` WHERE roles = ?";
        try {
            PreparedStatement statement = cnx.prepareStatement(query);
            statement.setString(1, roles);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Utilisateur getUtilisateurById(int userId) {
        Utilisateur utilisateur = null;
        String sql = "SELECT * FROM `user` WHERE `id` = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(sql);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("id"));
                utilisateur.setName(rs.getString("name"));
                utilisateur.setPrename(rs.getString("prename"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setPassword(rs.getString("password"));
                utilisateur.setPhone(rs.getInt("phone"));
                utilisateur.setRoles(rs.getString("roles"));
                utilisateur.setImage(rs.getString("image"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return utilisateur;
    }
    }
