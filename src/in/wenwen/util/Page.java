package in.wenwen.util;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {

	private int totalRow;
	private int totalPage;
	private int currentPage;
	private int pageSize;
	private int barSize;
	private List<T> results;
	private String prev = "上一页";
	private String next = "下一页";
	private String url;
	private String title = "";
	public Page() {
		barSize = 3;
		pageSize = 15;
		results = new ArrayList<T>();
	}
	
	public void setTitle(String title) {
        this.title = title;
    }
	
	public String getTitle() {
        return title;
    }
	
	public Page(String url) {
		this();
		setUrl(url);
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	//
	public String getUrl() {
		return url;
	}
	//
	
	public void setPrev(String prev) {
		this.prev = prev;
	}
	
	public String getPrev() {
		return prev;
	}
	
	public void setNext(String next) {
		this.next = next;
	}
	
	public String getNext() {
		return next;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setBarSize(int barSize) {
		this.barSize = barSize;
	}
	
	public int getBarSize() {
		return barSize;
	}
	
	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public void addResult(T entity) {
		results.add(entity);
	}
	
	public void addResults(List<T> results) {
		this.results.addAll(results);
	}
	
	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}
}
