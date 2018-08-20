package com.neo.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.http.client.utils.DateUtils;

/**
 * Created by summer on 2016/11/29.
 */
public class User implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2792703939441305661L;

	private String name;

    private String pass;
    
    private String dateStr = DateUtils.formatDate(new Date(),"yy-MM-dd HH:mm:ss");

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
}
