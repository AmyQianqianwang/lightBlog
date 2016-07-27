package in.wenwen.dto;

import org.jiucheng.orm.annotation.Table;

@Table("article")
public class BlogDto {
    private Long id;
    private Long webappId;
    private String title;
    private String isHalt;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
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
    public String getIsHalt() {
        return isHalt;
    }
    public void setIsHalt(String isHalt) {
        this.isHalt = isHalt;
    }
}
