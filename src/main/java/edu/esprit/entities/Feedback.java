package edu.esprit.entities;

import java.util.Objects;

public class Feedback {
    private int id;
    private int id_evenment;
    private String text;
    private int Likes;

    public Feedback() {
    }

    public Feedback(int id, int id_evenment, String text) {
        this.id = id;
        this.id_evenment = id_evenment;
        this.text = text;

    }
    public Feedback(int id_evenment, String text) {
        this.id_evenment = id_evenment;
        this.text = text;

    }


    public Feedback(int id_evenment, String text, int Likes) {
        this.id_evenment = id_evenment;
        this.text = text;
        this.Likes=Likes;
    }

    public int getLikes() {
        return Likes;
    }

    public void setLikes(int likes) {
        Likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_evenment() {
        return id_evenment;
    }

    public void setId_evenment(int id_evenment) {
        this.id_evenment = id_evenment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return id == feedback.id && id_evenment == feedback.id_evenment && Objects.equals(text, feedback.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, id_evenment, text,Likes);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", id_evenment=" + id_evenment +
                ", text='" + text + '\'' +
                ", Likes=" + Likes +
                '}';
    }
}

