package edu.esprit.utils;

public class SessionManager {
    private static SessionManager instance;

    private static int id_user;
    private static String name;
    private static String prename;
    private static int phone;
    private static String email;
    private static String roles;
    private static String image;

    private SessionManager(int id_user , String name , String prename , int phone , String email, String roles, String image) {
        SessionManager.id_user=id_user;
        SessionManager.name=name;
        SessionManager.prename=prename;
        SessionManager.phone=phone;
        SessionManager.email=email;
        SessionManager.roles=roles;
        SessionManager.image=image;
    }
    public static SessionManager getInstace(int id_user , String name , String prename , int phone , String email , String roles, String image) {
        if(instance == null) {
            instance = new SessionManager(id_user, name , prename, phone, email, roles, image);
        }
        return instance;
    }

    public static SessionManager getInstance() {return instance;}

    public static int getId_user() {return id_user;}

    public static String getName() {return name;}

    public static String getPrename() {return prename;}

    public static String getEmail() {return email;}

    public static int getPhone() {return phone;}

    public static String getRoles() {return roles;}

    public static String getImage() {return image;}

    public static void setInstance(SessionManager instance) {SessionManager.instance = instance;}

    public static void setId_user(int id_user) {SessionManager.id_user = id_user;}

    public static void setName(String name) {SessionManager.name = name;}

    public static void setPrename(String prename) {SessionManager.prename = prename;}

    public static void setPhone(int phone) {SessionManager.phone = phone;}

    public static void setEmail(String email) {SessionManager.email = email;}

    public static void setRoles(String roles) {SessionManager.roles = roles;}

    public static void setImage(String image) {SessionManager.image = image;}

    public static void cleanUserSession() {
        id_user=0;
        name="";
        prename="";
        phone=0;
        email="";
        roles="";
        image="";
    }

}