package edu.esprit.utils;

import java.sql.*;

public class DataSource {
    private Connection conn;
    final String url = "jdbc:mysql://localhost:3307/pidev2";
    final String user = "root";
    final String pwd = "";
    static DataSource instance;

    private DataSource() {
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connected");
        } catch (SQLException s) {
            System.out.println(s.getMessage());

        }
    }

    public static DataSource getInstance() {
        if (instance == null)
            instance = new DataSource();
        return instance;
    }

    public Connection getConn() {
        return conn;
    }
}