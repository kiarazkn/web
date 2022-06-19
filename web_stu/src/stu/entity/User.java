package stu.entity;

public class User {

    private int id;				//主键id
    private String role;   		//角色
    private String name;		//姓名or账户名
    private String pwd;			//密码
    
	public User() {
		
	}
	public User(int id, String role, String name, String pwd) {
		
		this.id = id;
		this.role = role;
		this.name = name;
		this.pwd = pwd;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	

	
	
    
}
