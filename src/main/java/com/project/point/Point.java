package com.project.point;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kover on 06.05.17.
 */

@Entity

@Table(name="POINTS")
public class Point implements Serializable{

    @Id
    @Column(name =  "id")
    private long id;

    @Column(name="x")
    private float x;

    @Column(name="y")
    private float y;

    @Column(name = "isin")
    private boolean included;

    @Column(name = "radius")
    private float radius;


    public Point(){}

    public Point(float x, float y, float r){
        this.x = x;
        this.y = y;
        this.radius = r;
        included = false;
        id = hashCode();
    }


    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    public boolean isIncluded() {
        return included;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj){
        Point p = null;
        try {
            p = (Point)obj;
        }catch (ClassCastException cce){return false;}

        if( p != null ){
            if( this.x == p.x && this.y == p.y ){
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode(){
        return (int)(x*1000000+y*1000 + radius);
    }
}
