package ph.entity;

public class Visit {

    private int id;				//主键id
    private int petId;			//宠物主键
    private int vetId;			//医生主键
    private String visitdate;	//日期
    private String description;	//病情描述
    private String treatment;	//治疗方案
    
    private String vetName;

    public Visit() {

    }

    public Visit(int id, int petId, int vetId, String visitdate, String description, String treatment) {

        this.id = id;
        this.petId = petId;
        this.vetId = vetId;
        this.visitdate = visitdate;
        this.description = description;
        this.treatment = treatment;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPetId() {
        return petId;
    }
    public void setPetId(int petId) {
        this.petId = petId;
    }
    public int getVetId() {
        return vetId;
    }
    public void setVetId(int vetId) {
        this.vetId = vetId;
    }
    public String getVisitdate() {
        return visitdate;
    }
    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTreatment() {
        return treatment;
    }
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

	public String getVetName() {
		return vetName;
	}

	public void setVetName(String vetName) {
		this.vetName = vetName;
	}

}

