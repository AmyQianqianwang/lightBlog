package in.wenwen.util;

public class UploadResult {
    private int error = 1;
    private String url;
    
    public int getError() {
        return error;
    }
    
    public void setError(int error) {
        this.error = error;
    }
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
