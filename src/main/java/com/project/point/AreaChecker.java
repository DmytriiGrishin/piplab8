package com.project.point;

import com.project.eclipselink.DataBaseOperations;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Created by kover on 25.05.17.
 */
public class AreaChecker implements Serializable{
    private LinkedHashSet<Point> points;
    DataBaseOperations dbOps ;
    public DataBaseOperations getDbOps(){
        return dbOps;
    }

    public void setDbOps(DataBaseOperations dbOps) {
        this.dbOps = dbOps;
    }

    private Input input;
    public Input getInput() {
        return input;
    }


    public void setInput(Input input) {
        this.input = input;
    }
    public void submitHandler(){
        if(isValidInput()) addPoint(input.getX(), input.getY(), parseR(input.getR()));
    }

    public LinkedHashSet<Point> getPoints() {
        return points;
    }
    @PostConstruct
    public void postInit(){
        try {
            points = dbOps.readAllTable();
        } catch(Exception e){
            points = new LinkedHashSet<Point>();
        }
    }
    private boolean isValidInput(){
        if(input.getR()==-1){
            return false;
        }
        if(input.getX()>3||input.getX()<-5){
            return false;
        }
        if(input.getY()<-3.0||input.getY()>5.0){
            return false;
        }
        return true;
    }
    public void addPoint(float x, float y, float r){
        if(points.size()>0){
            Point e = (Point)(points.toArray()[0]);
            float oldR = e.getRadius();

            if(oldR!=r){
                dbOps.deleteAllPoints();
                for(Point p : points){
                   p.setRadius(r);
                    dbOps.addPoint(p);
                }
            }
        }
            Point newp = new Point(x, y, r);
            dbOps.addPoint(newp);
            points.add(newp);
            checkAllPoints(points, r);

    }
    public void reset(){
        dbOps.deleteAllPoints();
        points = new LinkedHashSet<Point>();
    }
    private float mx;
    private float my;

    public float getMx() {
        return mx;
    }

    public float getMy() {
        return my;
    }

    public void setMx(float mx) {
        this.mx = mx;
    }

    private float parseR(int tmpr){
        switch (tmpr){
            case 0:
                 return 1f;
            case 1:
                return 1.5f;
            case 2:
                return 2f;
            case 3:
                return 2.5f;
            case 4:
                return 3f;
            default: return 1f;
        }
    }
    public void setMy(float my) {
        this.my = my;
    }
    public void clickListener(AjaxBehaviorEvent e){
        if(isValidInput()) {
            addPoint(mx, my, parseR(input.getR()));
        }
    }
    private String img;
    public String getImg() throws IOException{
        String webPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/graph.png");
        BufferedImage source = null;
        try {
            source = ImageIO.read(new File(webPath));
        } catch (Exception e ){return webPath;}
        drawAllPoints(source);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(source, "png", baos);
        baos.flush();
        byte[] imgToSend = baos.toByteArray();
        baos.close();
        img = DatatypeConverter.printBase64Binary(imgToSend);
        return img;
    }
    private void drawAllPoints(BufferedImage img){
        for(Point p : points) drawPoint(img, p, p.getRadius());

    }
    private void drawPoint(BufferedImage img, Point point, float r){
        //221x221
        //r=80px
        Graphics2D g = img.createGraphics();
        if(point.isIncluded() ) g.setColor(Color.GREEN);
        else g.setColor(Color.RED);
        int width = img.getWidth();
        int height = img.getHeight();
        g.fillOval(width/2 + (int)(point.getX()/r*80)-5, height/2 - (int)(point.getY()/r * 80) -5 , 10, 10);
    }
    private void checkAllPoints(HashSet<Point> points, float r) {
        Iterator<Point> itr = points.iterator();
        while(itr.hasNext()) checkPoint(itr.next(), r);
    }
    private void checkPoint(Point p, float r){
        if(p.getY()>=0&&p.getX()>=0&&p.getX()<=r/2&&p.getY()<=r){
            p.setIncluded(true);
            return;
        }
        if(p.getX()<=0&&p.getX()>=-r&&p.getY()>=0&&p.getY()<=(0.5*p.getX()+r/2)){
            p.setIncluded(true);
            return;
        }
        if(p.getX()<=0&&p.getX()>=-r/2&&p.getY()<=0&&p.getY()>=-Math.sqrt(-p.getX()*p.getX() + r*r/4)){
            p.setIncluded(true);
            return;
        }
        p.setIncluded(false);
    }
}
