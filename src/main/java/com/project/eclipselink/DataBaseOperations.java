package com.project.eclipselink;

import com.project.point.Point;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by evgeniy on 02.06.17.
 */
public class DataBaseOperations {
    public void addPoint(Point point){
        try {
            EntityManager em = EntityManagerUtil.getEntityManager();
            if (!isExist(point)){
                em.getTransaction().begin();
                em.persist(point);
                em.getTransaction().commit();
            }


        }catch (Exception e){}

    }

    public boolean isExist(Point point){
        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();
        boolean result;

        Query query = em.createQuery("select p from Point p where p.x = :x and p.y = :y and p.radius = :r");
        query.setParameter("x", point.getX());
        query.setParameter("y", point.getY());
        query.setParameter("r", point.getRadius());
        result = (query.getResultList().isEmpty());
        em.getTransaction().commit();
        return !result;
    }


    public void deleteAllPoints(){
        try {
            EntityManager entityManager = EntityManagerUtil.getEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery("DELETE FROM POINTS");
            query.executeUpdate();
            entityManager.getTransaction().commit();
        }catch (Exception e){}

    }


    public LinkedHashSet<Point> readAllTable(){

        EntityManager em = EntityManagerUtil.getEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select p from Point p");
        em.getTransaction().commit();
        LinkedHashSet<Point> points = new LinkedHashSet<Point>();
        List list = query.getResultList();
        for(Object obj : list){
            points.add((Point) obj);
        }
        return points;
    }

}
