package in.wenwen.entity;

public class UserAuthCache extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -5881458387167830365L;
    
    private Long userId;
    private String cacheString;
    private String isHalt;
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getCacheString() {
        return cacheString;
    }
    
    public void setCacheString(String cacheString) {
        this.cacheString = cacheString;
    }
    
    public String getIsHalt() {
        return isHalt;
    }
    
    public void setIsHalt(String isHalt) {
        this.isHalt = isHalt;
    }
}
