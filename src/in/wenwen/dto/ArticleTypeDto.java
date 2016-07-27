package in.wenwen.dto;

import org.jiucheng.orm.annotation.Table;

@Table("article_type")
public class ArticleTypeDto {
    
    private Long id;
    private Long webappId;
    private String isHalt;
    private String name;
    private Long size;
    
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getSize() {
        return size;
    }
    
    public void setSize(Long size) {
        this.size = size;
    }
}
