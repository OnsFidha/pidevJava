package edu.esprit.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Reclamation {
    private int id;
    private String type;
    private String description;
    private boolean etat;
    private Date date_creation;

    public Reclamation() {
    }

    public Reclamation(int id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.etat = false;

    }

    public Reclamation(String type, String description) {
        this.type = type;
        this.description = description;
        this.etat = false;
        this.date_creation = new Date();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEtat() {
        return etat;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {

        //this.description = description;
        // Vérifier si la description contient des mots interdits
        String descriptionCleaned = filterBadWords(description);

        // Affecter la description nettoyée
        this.description = descriptionCleaned;

        //return this;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return id == that.id && etat == that.etat && Objects.equals(type, that.type) && Objects.equals(description, that.description) && Objects.equals(date_creation, that.date_creation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, description, etat, date_creation);
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", etat=" + etat +
                ", date_creation=" + date_creation +
                '}';
    }

    private String filterBadWords(String description) {
        // Chemin vers le fichier contenant la liste des mots interdits
        String filePath = "C:\\Users\\21624\\Desktop\\pidevJava\\full-list-of-bad-words_text-file_2022_05_05.txt";

        // Lire la liste des mots interdits à partir du fichier
        List<String> badWords = new ArrayList<>();
        try (var br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                badWords.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur d'accès au fichier ou de lecture
            // Ici, je print l'erreur, mais vous pouvez gérer différemment selon vos besoins.
        }

        // Convertir la description en minuscules pour éviter les correspondances de casse
        String descriptionLowercase = description.toLowerCase();

        // Remplacer les mots interdits par des astérisques
        for (String badWord : badWords) {
            descriptionLowercase = descriptionLowercase.replaceAll("(?i)" + Pattern.quote(badWord), "*".repeat(badWord.length()));
        }

        // Retourner la description nettoyée
        return descriptionLowercase;
    }
}
