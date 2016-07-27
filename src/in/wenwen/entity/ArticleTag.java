package in.wenwen.entity;

public class ArticleTag extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 551181820941424257L;

    private Long articleId;
    private Long tagId;
    private String isHalt;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getIsHalt() {
        return isHalt;
    }

    public void setIsHalt(String isHalt) {
        this.isHalt = isHalt;
    }
}
