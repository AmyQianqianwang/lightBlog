package in.wenwen.entity;

public class Tag extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -2665293925159816887L;

    private String name;
    private Long size;
    private String isHalt;

    public Long getSize() {
        return size;
    }
    
    public void setSize(Long size) {
        this.size = size;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsHalt() {
        return isHalt;
    }

    public void setIsHalt(String isHalt) {
        this.isHalt = isHalt;
    }
}
