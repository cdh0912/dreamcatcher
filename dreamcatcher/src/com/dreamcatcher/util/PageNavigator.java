package com.dreamcatcher.util;

public class PageNavigator {
	private String root;
	private int newArticleCount;
	private int totalArticleCount;	// 411
	private int currentPage;		
	private int totalPageCount;		// (totalArticleCount-1) / BoardSizeConstant.REBOARD_LIST_SIZE + 1 
	private boolean isFirstPage;	
	private boolean isLastPage;		
	private String navigator;
	private int pageSize;
	private String jsMethodName;
	

	public void setJsMethodName(String jsMethodName) {
		this.jsMethodName = jsMethodName;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public int getNewArticleCount() {
		return newArticleCount;
	}
	public void setNewArticleCount(int newArticleCount) {
		this.newArticleCount = newArticleCount;
	}
	public int getTotalArticleCount() {
		return totalArticleCount;
	}
	public void setTotalArticleCount(int totalArticleCount) {
		this.totalArticleCount = totalArticleCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public boolean isFirstPage() {
		return isFirstPage;
	}
	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}
	public boolean isLastPage() {
		return isLastPage;
	}
	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
	public String getNavigator() {
		return navigator;
	}
	
	public void setNavigator() {
		StringBuffer tempNavigator = new StringBuffer();
		
		int previousPage = ( currentPage - 1) / pageSize * pageSize;
		int startPage = previousPage + 1;
		int endPage = previousPage + pageSize;
		int nextPage = endPage + 1;

		tempNavigator.append("<nav>\n");
		tempNavigator.append(" <ul class='pagination'>\n");
		
		// ���� �������� ù��° �������� ���
		if (this.isFirstPage()) {
			// �ֽ� ���
			tempNavigator.append("  <li class='disabled'><a href='#' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>\n");
			tempNavigator.append("  <li class='disabled'><a href='#' aria-label='Previous'><span aria-hidden='true'>&lt;</span></a></li>\n");

		// ���� �������� ù��° �������� �ƴ� ���
		} else {
			tempNavigator.append("  <li><a href='javascript:"+jsMethodName+"(1)' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>\n");
			tempNavigator.append("  <li><a href='javascript:"+jsMethodName+"("+previousPage+")' aria-label='Previous'><span aria-hidden='true'>&lt;</span></a></li>\n");			
		}

		// ������ �������� ���� ��ü ī��Ʈ���� ũ�ٸ�
		if( endPage > totalPageCount){
			endPage = totalPageCount;
		}
		
		for (int i = startPage; i <= endPage; i++) {
			if (currentPage == i) {
				tempNavigator.append("     <li class='active'><a href='#'>"+i+"</a></li>\n");
			} else {
				tempNavigator.append("     <li><a href='javascript:"+jsMethodName+"("+i+")'>"+i+"</a></li>\n");
			}
		}
		
		// ���� �������� ������ �������� ���
		if (this.isLastPage()) {
			tempNavigator.append("<li class='disabled'><a href='#' aria-label='Next'><span aria-hidden='true'>&gt;</span></a></li>\n");
			tempNavigator.append("<li class='disabled'><a href='#' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>\n");		
		// ���� �������� ������ �������� �ƴ� ���
		} else {
			tempNavigator.append("<li><a href='javascript:"+jsMethodName+"("+nextPage+")' aria-label='Next'><span aria-hidden='true'>&gt;</span></a></li>\n");
			tempNavigator.append("<li><a href='javascript:"+jsMethodName+"(" + totalPageCount + ")' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>\n");		
		}

		tempNavigator.append("  </ul\n");
		tempNavigator.append(" </nav>\n");
		this.navigator = tempNavigator.toString();
	}
	
	

}
