package org.project.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.entities.Person;
import org.project.interfaces.DAO;
import org.project.utilities.HibernateSessionFactory;

import java.util.List;

public class PersonDAO implements DAO<Person> {
    @Override
    public void save(Person person){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(person);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Person person){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(person);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Person person){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(person);
        tx.commit();
        session.close();
    }

    @Override
    public List<Person> findAll(){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Person> employees = (List<Person>) session.createQuery("From Person").list();
        tx.commit();
        session.close();
        return employees;
    }

    @Override
    public Person findById(int id){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Person person = (Person) session.get(Person.class, id);
        session.close();
        return person;
    }
}
