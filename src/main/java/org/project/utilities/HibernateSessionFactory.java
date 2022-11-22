package org.project.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.project.entities.Company;
import org.project.entities.Person;
import org.project.entities.Role;
import org.project.entities.User;

public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null){
            try{
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Person.class);
                configuration.addAnnotatedClass(Role.class);
                configuration.addAnnotatedClass(Company.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch(Exception e){
                System.out.println("Вызвано исключение!" + e);
            }
        }
        return sessionFactory;
    }

}
