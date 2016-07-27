package in.wenwen.entity;

public class UserAuth extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 6337660619571089022L;
    
    private String uri;
    private String descript;
    private String descriptEn;
    private String isHalt;
    
    public String getUri() {
        return uri;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getDescriptEn() {
        return descriptEn;
    }

    public void setDescriptEn(String descriptEn) {
        this.descriptEn = descriptEn;
    }

    public String getIsHalt() {
        return isHalt;
    }
    
    public void setIsHalt(String isHalt) {
        this.isHalt = isHalt;
    }
}
