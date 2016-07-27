package in.wenwen.dto;

import in.wenwen.entity.Ly;

public class LyQueryDto extends Ly {

    /**
     * 
     */
    private static final long serialVersionUID = -9105892803018639117L;
    
    private String articleTitle;

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
}
