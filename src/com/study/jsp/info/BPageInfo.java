package com.study.jsp.info;

public class BPageInfo {
	int totalCount;
	int listCount;
	int totalPage;
	int curPage;
	int pageCount;
	int startPage;
	int endPage;
	
	public BPageInfo() {
		super();
	}		
	
	public BPageInfo(int totalCount, int listCount, int totalPage, int curPage, int pageCount, int startPage,
			int endPage) {
		super();
		this.totalCount = totalCount;
		this.listCount = listCount;
		this.totalPage = totalPage;
		this.curPage = curPage;
		this.pageCount = pageCount;
		this.startPage = startPage;
		this.endPage = endPage;
	}

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
		
}
