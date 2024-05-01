package edu.esprit.entities;

import java.util.Objects;

public class Participation {
    private int id;
    private int id_evenment;
    private int id_user;

    public Participation() {
    }

    public Participation( int id_evenment, int id_user) {

        this.id_evenment = id_evenment;
        this.id_user = id_user;
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

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participation that = (Participation) o;
        return id == that.id && id_evenment == that.id_evenment && id_user == that.id_user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, id_evenment, id_user);
    }

    @Override
    public String toString() {
        return "Participation{" +
                "id=" + id +
                ", id_evenment=" + id_evenment +
                ", id_user=" + id_user +
                '}';
    }
}
