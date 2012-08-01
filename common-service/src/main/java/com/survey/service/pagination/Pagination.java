package com.survey.service.pagination;

/**
 * 分页信息.
 * 
 */
public class Pagination  {
 

	/**
	 * 缺省每页显示的条数.
	 */
	public static final int DEFAULT_PAGE_SIZE = 15;

	/**
	 * 缺省第一页页码.
	 */
	public static final int DEFAULT_FIRST_PAGE_NO = 1;

	/**
	 * 缺省分页链接大小.
	 */
	public static final int DEFAULT_LINKSIZE = 10;

	/**
	 * 第一页页码.
	 */
	private int pageNo = DEFAULT_FIRST_PAGE_NO;

	/**
	 * 每页条数.
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	/**
	 * 分页链接大小
	 */
	private int linkSize = DEFAULT_LINKSIZE;

	/**
	 * 结果集纪录数.
	 */
	private int recordCount;

	/**
	 * 当页结果集.
	 */
	private int pageRecordCount;

	/**
	 * 页偏移量.
	 */
	private int pageOffset;

	/**
	 * 结果集页数.
	 */
	private int pageCount;

	/**
	 * 分页显示开始页
	 */
	private int start;

	/**
	 * 分页显示结束页
	 */
	private int end;

	/**
	 * 
	 */
	private int top;

	/**
	 * 默认构造器.
	 */
	public Pagination() {
		this.pageOffset=Pagination.DEFAULT_FIRST_PAGE_NO;
		this.pageSize=Pagination.DEFAULT_PAGE_SIZE;
	}
	
	
	
	public Pagination(int pageNo,int pageSize,int recordCount) {
		super();
		this.pageNo=pageNo;
		this.pageSize = pageSize;
		this.calculatePagination(recordCount);
	}



	public void calculatePagination(int recordCount){
		int pageCount = (recordCount %  this.pageSize == 0) ? recordCount /  this.pageSize: recordCount /  this.pageSize + 1;
		this.recordCount=recordCount;
		this.pageCount=pageCount;
		int mid = this.linkSize/ 2;
		this.start=Math.max(1, this.pageNo - mid);
		this.end=Math.min(pageCount, this.pageNo + mid);
		this.pageOffset=(this.pageNo - 1) * this.pageSize;
	}
	
	public long getEnd() {
		return end;
	}

	/**
	 * 取得纪录上标偏移，用来构造SQL语句.
	 * 
	 * @return 纪录上标偏移
	 */
	public final int getFetchingMaxNumber() {
		return this.pageNo * this.pageSize;
	}

	/**
	 * 取得纪录下标偏移，用来构造SQL语句.
	 * 
	 * @return 纪录下标偏移
	 */
	public final int getFetchingMinNumber() {
		return (this.pageNo - 1) * this.pageSize + 1;
	}

	public int getLinkSize() {
		return linkSize;
	}

	public int getPageCount() {
		return pageCount;
	}

 

	public int getPageOffset() {
		return pageOffset;
	}

	public int getPageRecordCount() {
		return pageRecordCount;
	}

 

	public int getRecordCount() {
		return recordCount;
	}

	public int getStart() {
		return start;
	}

	public long getTop() {
		return top;
	}

	/**
	 * 处理分页链接显示信息
	 */
	public void handleLink() {
		int mid = getLinkSize() / 2;
		this.start = Math.max(1, getPageNo() - mid);
		this.end = Math.min(getPageCount(), getPageNo() + mid);
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setLinkSize(int linkSize) {
		this.linkSize = linkSize;
	}

	public void setPageCount(int pagecount) {
		this.pageCount = pagecount;
	}
 

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
