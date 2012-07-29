package com.jian.dao.pagination;

import java.io.Serializable;

public abstract class AbstractObjectVO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 冗余字段 */
	public long offset;
	public long rows;

	private boolean success;

	/**
	 * 查询条件
	 */
	public String clause;

	// 时间段
	private Long downDateline;
	private Long upDateline;
	
	protected T entity;

	public AbstractObjectVO() {
		super();
	}
	
	public AbstractObjectVO(T entity) {
		super();
		this.entity = entity;
	}



	public void pagination(long offset,long rows){
		this.offset=offset;
		this.rows=rows;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getRows() {
		return rows;
	}

	public void setRows(long rows) {
		this.rows = rows;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getDownDateline() {
		return downDateline;
	}

	public void setDownDateline(Long downDateline) {
		this.downDateline = downDateline;
	}

	public Long getUpDateline() {
		return upDateline;
	}

	public void setUpDateline(Long upDateline) {
		this.upDateline = upDateline;
	}

	public String getClause() {
		return clause;
	}

	public void setClause(String clause) {
		this.clause = clause;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	

}
