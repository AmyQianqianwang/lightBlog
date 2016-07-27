package in.wenwen.dto;

import org.jiucheng.orm.annotation.Table;

import in.wenwen.entity.Article;

@Table("article")
public class ArticleDto extends Article {

    /**
     * 
     */
    private static final long serialVersionUID = 272791841612494014L;
    
    private String title;
    private String logo;
    private Long click;
    private Long assess;
    private String isHalt;
    private String content;
    private String titleHtml;
    private String userName;
    private String typeName;
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Long getClick() {
        return click;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    public Long getAssess() {
        return assess;
    }

    public void setAssess(Long assess) {
        this.assess = assess;
    }

    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    public void setTitleHtml(String titleHtml) {
        this.titleHtml = titleHtml;
    }
    
    public String getTitleHtml() {
        return titleHtml;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    public String getIsHalt() {
        return isHalt;
    }
    
    public void setIsHalt(String isHalt) {
        this.isHalt = isHalt;
    }
}
