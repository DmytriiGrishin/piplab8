package com.project.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by evgeniy on 24.05.17.
 */
@ManagedBean
@RequestScoped
public class TimeBean {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    public void refresh(AjaxBehaviorEvent e){
        date = new Date();
    }

    public String getDate(){
        return dateFormat.format(date);
    }

}
