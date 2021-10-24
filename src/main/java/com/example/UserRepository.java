package com.example;

import javax.persistence.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    EntityManager manager;
    EntityManagerFactory factory;

    public UserRepository () {
        factory = Persistence.createEntityManagerFactory("app");
        manager = factory.createEntityManager();
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        //HQL - Hibernate Query Language
        String query = "SELECT u FROM User u WHERE u.id IS NOT NULL";
        TypedQuery<User> typedQuery = manager.createQuery(query, User.class);

        try {
            users = typedQuery.getResultList();
        } catch (NoResultException err) {
            err.printStackTrace();
        }

        return users;
    }

    public User getUser(int id) {
        User user = new User();
        String query = "SELECT u FROM User u WHERE u.id = :id";

        TypedQuery<User> typedQuery = manager.createQuery(query, User.class);
        typedQuery.setParameter("id", id);

        try {
            user = typedQuery.getSingleResult();
        } catch (NoResultException err) {
            err.printStackTrace();
        }

        return user;
    }

    public User create(User user) {
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = manager.getTransaction();
            entityTransaction.begin();
            manager.persist(user);
            entityTransaction.commit();
        } catch (Exception err) {
            //rollback
            if(entityTransaction != null) {
                entityTransaction.rollback();
            }
            err.printStackTrace();
        }

        return user;
    }

    public User update(User user) {
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = manager.getTransaction();
            entityTransaction.begin();
            manager.merge(user);
            entityTransaction.commit();
        } catch (Exception err) {
            if(entityTransaction != null){
                entityTransaction.rollback();
            }
            err.printStackTrace();
        }

        return user;
    }

    public boolean delete(User user){
        EntityTransaction entityTransaction = null;

        try {
            entityTransaction = manager.getTransaction();
            entityTransaction.begin();
            user = manager.find(User.class, user.getId());
            if(user == null){
                entityTransaction.rollback();
                return false;
            }
            manager.remove(user);
            entityTransaction.commit();
        } catch (Exception err) {
            if(entityTransaction != null) {
                entityTransaction.rollback();
            }
            err.printStackTrace();
        }
        return true;
    }
}
