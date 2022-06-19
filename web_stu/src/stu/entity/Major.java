package stu.entity;

import java.util.ArrayList;
import java.util.List;


public class Major {
	private int id;
	private String name;
	private List<Stu> stus=new ArrayList<Stu>();
	 
	public Major() {
		
	}
	public Major(int id, String name, List<Stu> stus) {
		
		this.id = id;
		this.name = name;
		this.stus = stus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Stu> getStus() {
		return stus;
	}
	public void setStus(List<Stu> stus) {
		this.stus = stus;
	}

	
}
