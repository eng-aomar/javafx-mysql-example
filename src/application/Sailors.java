package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sailors {
	private int sid;
	private String sname;
	private int rating;
	private double age;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public double getAge() {
		return age;
	}
	public void setAge(double age) {
		this.age = age;
	}
	public Sailors(int sid, String sname, int rating, double age) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.rating = rating;
		this.age = age;
	}
	

	
	}


