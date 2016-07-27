package in.wenwen.dto;

import org.jiucheng.orm.annotation.Table;

@Table("user")
public class UserDto {
    private Long id;
    private Long webappId;
    private String uid;
    private String nick;
    private String token;
    
    public Long getWebappId() {
        return webappId;
    }
    
    public void setWebappId(Long webappId) {
        this.webappId = webappId;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getNick() {
        return nick;
    }
    
    public void setNick(String nick) {
        this.nick = nick;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUid() {
        return uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }
}
