package org.project.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.entities.User;
import org.project.interfaces.DAO;
import org.project.utilities.HibernateSessionFactory;

import java.util.List;

public class UserDAO implements DAO<User> {
    @Override
    public int save(User user){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        int id = (int) session.save(user);
        tx.commit();
        session.close();
        return id;
    }

    @Override
    public void update(User user){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(user);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(User user){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(user);
        tx.commit();
        session.close();
    }

    @Override
    public List<User> findAll(){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<User> users = (List<User>) session.createQuery("From User").list();
        tx.commit();
        session.close();
        return users;
    }

    @Override
    public User findById(int id){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        User user = (User) session.get(User.class, id);
        session.close();
        return user;
    }
}
