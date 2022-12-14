package org.project.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.entities.TradeOperation;
import org.project.interfaces.DAO;
import org.project.utilities.HibernateSessionFactory;

import java.util.List;

public class OperationDAO implements DAO<TradeOperation> {
    @Override
    public int save(TradeOperation operation){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        int id = (int) session.save(operation);
        tx.commit();
        session.close();
        return id;
    }

    @Override
    public void update(TradeOperation operation){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(operation);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(TradeOperation operation){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(operation);
        tx.commit();
        session.close();
    }

    @Override
    public List<TradeOperation> findAll(){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<TradeOperation> operations = (List<TradeOperation>) session.createQuery("From TradeOperation ").list();
        tx.commit();
        session.close();
        return operations;
    }

    @Override
    public TradeOperation findById(int id){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        TradeOperation operation = session.get(TradeOperation.class, id);
        session.close();
        return operation;
    }
}
