package stu.entity;

import java.util.ArrayList;
import java.util.List;



public class Stu {
	
	private int id;
	private String name;
	private String gender;
	private String birthday;
	private String tel;
	private int majorId;
	private List<Major> majors=new ArrayList<Major>();

	public Stu() {
		
	}
	public Stu(int id, String name, String gender, String birthday, String tel, int majorId, List<Major> majors) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.tel = tel;
		this.majorId = majorId;
		this.majors = majors;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getMajorId() {
		return majorId;
	}
	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}
	
	public List<Major> getMajors() {
		return majors;
	}
	public void setMajors(List<Major> majors) {
		this.majors = majors;
	}

	
	
}
