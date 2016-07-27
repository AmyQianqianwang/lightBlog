package in.wenwen.util;

import java.util.List;

public class PageResult<T> {
    private List<T> rows;
    private int results;
    private boolean hasError = false;
    private String error = "";
    
    public List<T> getRows() {
        return rows;
    }
    
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
    
    public int getResults() {
        return results;
    }
    public void setResults(int results) {
        this.results = results;
    }
    public boolean isHasError() {
        return hasError;
    }
    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}
