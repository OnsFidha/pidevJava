package edu.esprit.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IService <T>{
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> getAll() throws SQLException;
     T getOneById(int id) throws SQLException;
}
