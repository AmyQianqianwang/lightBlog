package in.wenwen.dto;

import org.jiucheng.util.StringUtil;

public class QueryDto {
    private Long webappId;
    private Long typeId;
    private String q;
    private String t;
    private String pageIndex;
    private String limit;
    
    public String getT() {
        return t;
    }
    
    public void setT(String t) {
        this.t = t;
    }
    
    public Long getTypeId() {
        return typeId;
    }
    
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    
    public void setWebappId(Long webappId) {
        this.webappId = webappId;
    }
    
    public Long getWebappId() {
        return webappId;
    }
    
    public void setPageIndex(int pageIndex) {
        this.pageIndex = Integer.toString(pageIndex);
    }
    
    public void setLimit(String limit) {
        this.limit = limit;
    }
    
    public String getQ() {
        return q;
    }
    
    public void setQ(String q) {
        this.q = q;
    }
    
    public int getPageIndex() {
        int p = 0;
        if(StringUtil.isNotBlank(pageIndex) && pageIndex.matches("[0-9]+")) {
            p = Integer.parseInt(pageIndex);
        }
        return p;
    }
    
    public int getLimit() {
        int l = 10;
        if(StringUtil.isNotBlank(limit) && limit.matches("[0-9]+")) {
            l = Integer.parseInt(limit);
        }
        if(l > 100) {
            l = 100;
        }
        return l;
    }
    
    
}
