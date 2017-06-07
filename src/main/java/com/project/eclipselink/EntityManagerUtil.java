package com.project.eclipselink;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by kover on 25.05.17.
 */
public class EntityManagerUtil {
    private static final EntityManagerFactory entityManagerFactory;
    static{
        try{
            entityManagerFactory= Persistence.createEntityManagerFactory("lab8");
        } catch(Throwable e){
            throw new ExceptionInInitializerError(e);
        }
    }
    public static EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
