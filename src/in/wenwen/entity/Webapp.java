package in.wenwen.entity;

public class Webapp extends BaseEntity {
    
	/**
     * 
     */
    private static final long serialVersionUID = 8935057162854084994L;
    
    private String name;
    private String descript;
	private String isHalt;
	
	public String getDescript() {
        return descript;
    }
	
	public void setDescript(String descript) {
        this.descript = descript;
    }
	
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIsHalt() {
        return isHalt;
    }
    
    public void setIsHalt(String isHalt) {
        this.isHalt = isHalt;
    }
}
