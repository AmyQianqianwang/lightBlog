package in.wenwen.entity;

public class Article extends BaseEntity {
    
	/**
     * 
     */
    private static final long serialVersionUID = 2910679294999743060L;
    
	private Long typeId;
	private Long userId;
	private String title;
	private String content;
	private Long click;
	private Long assess;
	private String isSave;
	private String isHalt;
	private String tags;
	
	public String getTags() {
        return tags;
    }
	
	public void setTags(String tags) {
        this.tags = tags;
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
	
	public Long getUserId() {
        return userId;
    }
	
	public void setUserId(Long userId) {
        this.userId = userId;
    }
	
	public String getIsSave() {
		return isSave;
	}
	
	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}
	
	public Long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	public String getIsHalt() {
		return isHalt;
	}
	
	public void setIsHalt(String isHalt) {
		this.isHalt = isHalt;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
