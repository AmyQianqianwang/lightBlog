package in.wenwen.dto;

import java.util.List;

public class LyDto {
    private Long id;
    private String author;
    private String content;
    private String url;
    private String img;
    private String ipName;
    private String isHalt;
    private String createDatetime;
    private List<LyDto> lys;
    
    public String getIsHalt() {
        return isHalt;
    }
    
    public void setIsHalt(String isHalt) {
        this.isHalt = isHalt;
    }
    
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }
    
    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public List<LyDto> getLys() {
        return lys;
    }
    public void setLys(List<LyDto> lys) {
        this.lys = lys;
    }
}
