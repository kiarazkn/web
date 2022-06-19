package ph.entity;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;				//主键id
    private String role;   		//角色：宠物主人or诊所系统管理员
    private String name;		//姓名or账户名
    private String pwd;			//密码
    private String tel;			//客户电话
    private String address;		//客户地址
    private List<Pet> pets=new ArrayList<Pet>();	//客户的宠物

    public User() {

    }

    public User(int id, String role, String name, String pwd, String tel, String address, List<Pet> pets) {

        this.id = id;
        this.role = role;
        this.name = name;
        this.pwd = pwd;
        this.tel = tel;
        this.address = address;
        this.pets = pets;
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
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public List<Pet> getPets() {
        return pets;
    }
    public void setPets(List<Pet>pets) {
        this.pets = pets;
    }


}


