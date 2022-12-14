package org.project.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.project.entities.*;

public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null){
            try{
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Role.class);
                configuration.addAnnotatedClass(Person.class);
                configuration.addAnnotatedClass(Company.class);
                configuration.addAnnotatedClass(TradeOperation.class);
                configuration.addAnnotatedClass(Item.class);
                configuration.addAnnotatedClass(Order.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch(Exception e){
                System.out.println("Вызвано исключение!" + e);
            }
        }
        return sessionFactory;
    }

}
