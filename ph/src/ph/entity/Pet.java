package ph.entity;

import java.util.ArrayList;
import java.util.List;

public class Pet {
    private int id;				//主键id
    private String name;		//宠物名
    private String birthdate;	//生日
    private String photo;		//照片
    private int ownerId;		//主人
    private List<Visit> visits=new ArrayList<Visit>();	//宠物病例
    public Pet() {

    }

    public Pet(int id, String name, String birthdate, String photo, int ownerId, List<Visit> visits) {

        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.photo = photo;
        this.ownerId = ownerId;
        this.visits = visits;
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
    public String getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public int getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public List<Visit> getVisits() {
        return visits;
    }
    public void setVisits(List<Visit>visits) {
        this.visits = visits;
    }


}


