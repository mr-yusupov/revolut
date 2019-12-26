package com.revolut.money.infrastructure.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionProvider {
    private static HibernateSessionProvider entity;
    private SessionFactory sessionFactory;

    public static HibernateSessionProvider get() {
        if (entity == null) {
            entity = new HibernateSessionProvider();
        }

        return entity;
    }

    private HibernateSessionProvider() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
