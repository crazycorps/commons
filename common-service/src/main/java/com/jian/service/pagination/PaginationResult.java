package com.jian.service.pagination;

import java.util.ArrayList;
import java.util.List;

import com.survey.dao.pagination.Pagination;

public class PaginationResult<T>  {

	private List<T> results;
	
	private Pagination pagination;

	public PaginationResult() {
		super();
		results=new ArrayList<T>();
		this.pagination=new Pagination();
	}

	public PaginationResult(List<T> results, Pagination pagination) {
		super();
		this.results = results;
		this.pagination = pagination;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
}
