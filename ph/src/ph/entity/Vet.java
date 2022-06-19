package ph.entity;

import java.util.ArrayList;
import java.util.List;

public class Vet {

    private int id;			//主键id
    private String name;	//医生名
    private List<Speciality> specs=new ArrayList<Speciality>();

    public Vet() {

    }

    public Vet(int id, String name, List<Speciality> specs) {

        this.id = id;
        this.name = name;
        this.specs = specs;
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

    public List<Speciality> getSpecs() {
        return specs;
    }
    public void setSpecs(List<Speciality>specs) {
        this.specs = specs;
    }

}



