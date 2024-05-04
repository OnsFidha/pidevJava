package edu.esprit.service;

import edu.esprit.entities.*;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;


public class ServiceCommande implements IService<Commande> {
    Connection conn = DataSource.getInstance().getConn();
    static IService<Commande> serviceCommande;
    IService<User> serviceUser = ServiceUser.getInstance();
    IService<Produit> serviceProduit = Serviceproduit.getInstance();

    private ServiceCommande() {
        System.out.println("ServiceCommande crée");
    }

    public static IService<Commande> getInstance() {
        if (serviceCommande == null)
            serviceCommande = new ServiceCommande();
        return serviceCommande;
    }

    @Override
    public void ajouter(Commande commande) {
        String req = "INSERT INTO `commande`(`user_id`, `date_commande`, `montant_total`) VALUES (?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, commande.getUser().getId());
            pst.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
            pst.setDouble(3, commande.getMontant_total());
            pst.executeUpdate();
            ResultSet resultSet = pst.getGeneratedKeys();
            if (resultSet.next()){
                commande.setId(resultSet.getInt(1));
                ajouterCommandeDetails(commande);
                System.out.println("Commande ajoutée");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void ajouterCommandeDetails(Commande commande){
        for (DetailCommande detailCommande: commande.getDetailsCommande()) {
            String req = "INSERT INTO `detail_commande`(`commande_id`, `produit_id`, `prix`, `quantite`) VALUES (?,?,?,?)";
            try {
                PreparedStatement pst = conn.prepareStatement(req);
                System.out.println("commande.getId(): "+commande.getId());
                System.out.println("produit: "+detailCommande.getProduit());
                pst.setInt(1, commande.getId());
                pst.setInt(2, detailCommande.getProduit().getId());
                pst.setDouble(3, detailCommande.getProduit().getPrix());
                pst.setInt(4, detailCommande.getQuantite());
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void modifier(Commande categorie) {
    }

    @Override
    public void supprimer(int id) {

        try {
            supprimerDetailCommande(id);
            String req = "DELETE FROM `commande` WHERE `id`=?";
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("commande supprimée ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimerDetailCommande(int id) {

        String req = "DELETE FROM `detail_commande` WHERE `commande_id`=?";
        try {
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("detail_commande supprimée ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Commande> getAll() {
        List<Commande> commandes = new ArrayList<>();
        String req = "select * from commande";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                User user = serviceUser.getOneById(user_id);
                Timestamp date_commande = rs.getTimestamp("date_commande");
                double montantTotal = rs.getDouble("montant_total");
                List<DetailCommande> detailCommandes = getDetailCommande(id);
                Commande commande = new Commande(user, date_commande, "", montantTotal, detailCommandes);
                commandes.add(commande);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  commandes.stream()
                .sorted(Comparator.comparing(Commande::getDate_commande).reversed())
                .collect(Collectors.toList());
    }


    public List<DetailCommande> getDetailCommande(int commandeId) {
        List<DetailCommande> detailCommandes = new ArrayList<>();
        String req = "select * from `detail_commande` WHERE `commande_id`=?";
        try {
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setInt(1, commandeId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int produit_id = rs.getInt("produit_id");
                Produit produit = serviceProduit.getOneById(produit_id);
                double prix = rs.getDouble("prix");
                int quantite = rs.getInt("quantite");

                DetailCommande detailCommande = new DetailCommande(commandeId, produit, prix, quantite);
                detailCommande.setCommande_id(id);
                detailCommandes.add(detailCommande);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return detailCommandes;
    }


    @Override
    public Commande getOneById(int id) {
       return null;
    }
}
