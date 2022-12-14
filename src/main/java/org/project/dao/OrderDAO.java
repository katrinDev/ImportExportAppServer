package org.project.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.entities.Order;
import org.project.interfaces.DAO;
import org.project.utilities.HibernateSessionFactory;

import java.util.List;

public class OrderDAO implements DAO<Order> {
    @Override
    public int save(Order order){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        int id = (int) session.save(order);
        tx.commit();
        session.close();
        return id;
    }

    @Override
    public void update(Order order){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(order);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Order order){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(order);
        tx.commit();
        session.close();
    }

    @Override
    public List<Order> findAll(){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Order> orders = (List<Order>) session.createQuery("From Order ").list();
        tx.commit();
        session.close();
        return orders;
    }

    @Override
    public Order findById(int id){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Order order = session.get(Order.class, id);
        session.close();
        return order;
    }
}
