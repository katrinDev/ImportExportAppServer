package org.project.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.entities.Item;
import org.project.interfaces.DAO;
import org.project.utilities.HibernateSessionFactory;

import java.util.List;

public class ItemDAO implements DAO<Item> {
    @Override
    public int save(Item item){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        int id = (int) session.save(item);
        tx.commit();
        session.close();
        return id;
    }

    @Override
    public void update(Item item){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(item);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Item item){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(item);
        tx.commit();
        session.close();
    }

    @Override
    public List<Item> findAll(){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Item> items = (List<Item>) session.createQuery("From Item ").list();
        tx.commit();
        session.close();
        return items;
    }

    @Override
    public Item findById(int id){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Item item = session.get(Item.class, id);
        session.close();
        return item;
    }
}
