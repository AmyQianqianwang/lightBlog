package in.wenwen.entity;

import java.util.Date;

public class Domain {
	private Long id;
	private Long webappId;
	/** 网址 */
	private String site;
	private String main;
	/** 模板 */
	private String templet;
	private String isHalt;
	private Date createDatetime;
	private Date modifyDatetime;
	
	public String getMain() {
        return main;
    }
	
	public void setMain(String main) {
        this.main = main;
    }

	public String getTemplet() {
		return templet;
	}
	
	public void setTemplet(String templet) {
		this.templet = templet;
	}
	
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
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
