package in.wenwen.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = -167290546155342122L;
    
    private Long id;
    private Long webappId;
	/** 账号 */
	private String uid;
	/** 昵称 */
	private String nick;
	/** 密码 */
	private String password;
	/** 创建IP */
	private String createIp;
	/** 最后登录IP */
	private String modifyIp;
	/** 创建时间 */
	private Date createDatetime;
	/** 最后登录时间 */
	private Date modifyDatetime;
	/** 是否停用 default=F*/
	private String isHalt;
	/** 邮箱 */
	private String email;
	/** 手机号 */
	private String mobile;
	/** G男/M女 */
	private String sex;
	
	public String getSex() {
        return sex;
    }
	
	public void setSex(String sex) {
        this.sex = sex;
    }
	

	public Long getWebappId() {
        return webappId;
    }
	
	public void setWebappId(Long webappId) {
        this.webappId = webappId;
    }
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsHalt() {
		return isHalt;
	}
	
	public void setIsHalt(String isHalt) {
		this.isHalt = isHalt;
	}
	
	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getModifyIp() {
		return modifyIp;
	}

	public void setModifyIp(String modifyIp) {
		this.modifyIp = modifyIp;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUid(String uid) {
        this.uid = uid;
    }
	
	public String getUid() {
        return uid;
    }

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
