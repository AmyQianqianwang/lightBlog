package in.wenwen.dto;

import org.jiucheng.orm.annotation.Table;

@Table("user_auth")
public class UserAuthDto {
    
    private Long id;
    private Long webappId;
    private String descript;
    private String descriptEn;
    private String isHalt;
    private String checked;
    
    public String getIsHalt() {
        return isHalt;
    }
    
    public void setIsHalt(String isHalt) {
        this.isHalt = isHalt;
    }
    
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

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
