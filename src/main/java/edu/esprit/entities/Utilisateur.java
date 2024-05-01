package edu.esprit.entities;

public class Utilisateur {

    private int id , phone ;
    private String name , prename, email, password, roles, image;
    public static Utilisateur Current_User;

    public Utilisateur() {}

    public Utilisateur(int id, String name, String prename, String email, String password, int phone, String roles, String image) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.prename = prename;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.image = image;
    }


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getPhone() {return phone;}
    public void setPhone(int phone) {this.phone = phone;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getPrename() {return prename;}
    public void setPrename(String prename) {this.prename = prename;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getRoles() {return roles;}
    public void setRoles(String roles) {this.roles = roles;}

    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}

    public static Utilisateur getCurrent_User() {return Current_User;}
    public static void setCurrent_User(Utilisateur Current_User) {Utilisateur.Current_User = Current_User;}

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", phone=" + phone +
                ", nom='" + name + '\'' +
                ", prenom='" + prename + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
