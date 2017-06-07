package com.project.point;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * Created by kover on 25.05.17.
 */
public class Input implements Serializable {
    private boolean r[] = new boolean[5];
    @PostConstruct
    private void postinit(){
        r[0]=true;
    }

    private float x;

    public float getX() {
        return x;
    }
    public int getR(){
        for(int i=0; i<5;i++){
            if(r[i]) return i;
        }
        return -1;
    }
    public void setX(float x) {
        this.x = x;
    }
    public boolean getR1(){
        return r[0];
    }
    public boolean getR2(){
        return r[1];
    }
    public boolean getR3(){
        return r[2];
    }
    public boolean getR4(){
        return r[3];
    }
    public boolean getR5(){
        return r[4];
    }
    private void setDefaultR(){
        for(int i=0;i<5;i++)
            if(r[i])  return;
        r[0]=true;
    }
    private void falseAll(){
        for(int i=0;i<5;i++){
            r[i]=false;
        }
    }
    public void setR1(boolean t){
        falseAll();
        r[0]=t;
        setDefaultR();
    }
    public void setR2(boolean t){
        falseAll();
        r[1]=t;
        setDefaultR();
    }public void setR3(boolean t){
        falseAll();
        r[2]=t;
        setDefaultR();
    }public void setR4(boolean t){
        falseAll();
            r[3]=t;
        setDefaultR();
    }public void setR5(boolean t){
        falseAll();
            r[4]=t;
        setDefaultR();
    }
    private float y;

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
