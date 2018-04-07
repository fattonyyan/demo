package com.yan.project.core.util;

public class Page {

	private static final int DEFAULT_PAGE_SIZE = 20;
	
	private int pageIndex;
	
	private int pageSize;
	
	private int totalCount;
	
	private int totalPage;
	
	private int offset;
	
	private String sort;
	
	private String order;

	public Page(){
		this.pageIndex = 0;
		this.pageSize = Page.DEFAULT_PAGE_SIZE;
	}
	
	public Page(int pageIndex){
		this.pageIndex = pageIndex;
		this.pageSize = Page.DEFAULT_PAGE_SIZE;
	}
	
	public Page(int pageIndex, int pageSize){
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
