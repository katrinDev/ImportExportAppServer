package org.project.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.entities.Company;
import org.project.interfaces.DAO;
import org.project.utilities.HibernateSessionFactory;

import java.util.List;

public class CompanyDAO implements DAO<Company> {
    @Override
    public int save(Company company){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        int id = (int) session.save(company);
        tx.commit();
        session.close();
        return id;
    }

    @Override
    public void update(Company company){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(company);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Company company){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(company);
        tx.commit();
        session.close();
    }

    @Override
    public List<Company> findAll(){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Company> companies = (List<Company>) session.createQuery("From Company ").list();
        tx.commit();
        session.close();
        return companies;
    }

    @Override
    public Company findById(int id){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Company company = session.get(Company.class, id);
        session.close();
        return company;
    }

}
