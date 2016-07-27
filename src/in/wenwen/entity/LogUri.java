package in.wenwen.entity;

public class LogUri extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 4975595284984552863L;

    private String uri;
    private String domain;
    private String ip;
    private String ipName;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public String getDomain() {
        return domain;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }
}
