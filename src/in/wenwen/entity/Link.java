package in.wenwen.entity;

public class Link extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -1795798918829012244L;

    private String uid;
    private String name;
    private String url;
    private String remark;
    private String isHalt;
    private Long fr;
    private Long to;
    
    public Long getFr() {
        return fr;
    }
    
    public void setFr(Long fr) {
        this.fr = fr;
    }
    
    public Long getTo() {
        return to;
    }
    
    public void setTo(Long to) {
        this.to = to;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsHalt() {
        return isHalt;
    }

    public void setIsHalt(String isHalt) {
        this.isHalt = isHalt;
    }
}
