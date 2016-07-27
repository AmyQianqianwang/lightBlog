package in.wenwen.dto;

import in.wenwen.entity.BaseEntity;

public class WebappDto extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = -22142380340441530L;
    private String name;
    private String context;
    private String domain;
    private String main;// /index.html
    private String url;
    private String uri;
    private String templet;
    private UserDto userDto;
    private String descript;
    
    public String getDescript() {
        return descript;
    }
    
    public void setDescript(String descript) {
        this.descript = descript;
    }
    
    public String getTemplet() {
        return templet;
    }
    
    public void setTemplet(String templet) {
        this.templet = templet;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMain() {
        return main;
    }
    
    public void setMain(String main) {
        this.main = main;
    }
    
    public UserDto getUser() {
        return userDto;
    }
    
    public void setUser(UserDto user) {
        this.userDto = user;
    }
    
    public String getUri() {
        return uri;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public String getContext() {
        return context;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public String getDomain() {
        return domain;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
}
