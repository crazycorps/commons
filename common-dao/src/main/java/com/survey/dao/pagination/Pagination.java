package com.survey.dao.pagination;

/**
 * 分页信息.
 * 
 * @author jason
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
	private long linkSize = DEFAULT_LINKSIZE;

	/**
	 * 结果集纪录数.
	 */
	private long recordCount;

	/**
	 * 当页结果集.
	 */
	private long pageRecordCount;

	/**
	 * 页偏移量.
	 */
	private long pageOffset;

	/**
	 * 结果集页数.
	 */
	private long pageCount;

	/**
	 * 分页显示开始页
	 */
	private long start;

	/**
	 * 分页显示结束页
	 */
	private long end;

	/**
	 * 
	 */
	private long top;

	/**
	 * 默认构造器.
	 */
	public Pagination() {
		this.pageOffset=Pagination.DEFAULT_FIRST_PAGE_NO;
		this.pageSize=Pagination.DEFAULT_PAGE_SIZE;
	}
	
	
	
	public Pagination(int pageNo,int pageSize,long recordCount) {
		super();
		this.pageNo=pageNo;
		this.pageSize = pageSize;
		this.calculatePagination(recordCount);
	}



	public void calculatePagination(long recordCount){
		long pageCount = (recordCount %  this.pageSize == 0) ? recordCount /  this.pageSize: recordCount /  this.pageSize + 1;
		this.recordCount=recordCount;
		this.pageCount=pageCount;
		long mid = this.linkSize/ 2;
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
	public final long getFetchingMaxNumber() {
		return this.pageNo * this.pageSize;
	}

	/**
	 * 取得纪录下标偏移，用来构造SQL语句.
	 * 
	 * @return 纪录下标偏移
	 */
	public final long getFetchingMinNumber() {
		return (this.pageNo - 1) * this.pageSize + 1;
	}

	public long getLinkSize() {
		return linkSize;
	}

	public long getPageCount() {
		return pageCount;
	}

 

	public long getPageOffset() {
		return pageOffset;
	}

	public long getPageRecordCount() {
		return pageRecordCount;
	}

 

	public long getRecordCount() {
		return recordCount;
	}

	public long getStart() {
		return start;
	}

	public long getTop() {
		return top;
	}

	/**
	 * 处理分页链接显示信息
	 */
	public void handleLink() {
		long mid = getLinkSize() / 2;
		this.start = Math.max(1, getPageNo() - mid);
		this.end = Math.min(getPageCount(), getPageNo() + mid);
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public void setLinkSize(int linkSize) {
		this.linkSize = linkSize;
	}

	public void setPageCount(long pagecount) {
		this.pageCount = pagecount;
	}
 

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public void setTop(long top) {
		this.top = top;
	}

	public void setPageOffset(long pageOffset) {
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
