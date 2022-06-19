package ph.entity;

import java.util.ArrayList;
import java.util.List;

public class Speciality {

    private int id;			//主键id
    private String name;	//专业名
    private List<Vet> vets=new ArrayList<Vet>();

    public Speciality() {

    }

    public Speciality(int id, String name, List<Vet> vets) {

        this.id = id;
        this.name = name;
        this.vets = vets;
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

    public List<Vet> getVets() {
        return vets;
    }
    public void setvets(List<Vet>vets) {
        this.vets = vets;
    }

}
