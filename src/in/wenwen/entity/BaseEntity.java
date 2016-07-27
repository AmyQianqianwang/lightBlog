package in.wenwen.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6841103049291793041L;
    
    private Long id;
    private Long webappId;
    private Date createDatetime;
    private Date modifyDatetime;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getWebappId() {
        return webappId;
    }
    
    public void setWebappId(Long webappId) {
        this.webappId = webappId;
    }
    
    public Date getCreateDatetime() {
        return createDatetime;
    }
    
    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }
    
    public Date getModifyDatetime() {
        return modifyDatetime;
    }
    
    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }
}
