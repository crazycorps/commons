package com.survey.service.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractObjectVO<T> implements Serializable {
	
	protected T entity;

	public AbstractObjectVO(T entity) {
		super();
		this.entity = entity;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

}
