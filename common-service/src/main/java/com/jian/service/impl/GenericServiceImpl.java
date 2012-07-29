package com.jian.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jian.dao.GenericDao;
import com.jian.dao.pagination.AbstractObjectVO;
import com.jian.dao.pagination.Pagination;
import com.jian.service.GenericService;
import com.jian.service.pagination.PaginationResult;

/**
 * 业务处理实现基类。
 * 
 * @author jason
 * 
 * @param <T>
 * @param <PK>
 */
public class GenericServiceImpl<T, PK extends Serializable> implements
		GenericService<T, PK> {
	
	@Override
	public GenericDao getGenricDao() {
		throw new RuntimeException("children not implement getGenricDao method.");
	}

	@Override
	public T getById(PK id) throws Exception {
		return (T)this.getGenricDao().selectById(id);
	}

	@Override
	public Long saveEntity(T t) throws Exception {
		return this.getGenricDao().insertEntity(t);
	}

	@Override
	public Long modifyEntity(T t) throws Exception {
		return this.getGenricDao().updateEntity(t);
	}

	@Override
	public Long removeById(PK id) throws Exception {
		return this.getGenricDao().deleteById(id);
	}

	@Override
	public T get(AbstractObjectVO vo) throws Exception {
		return (T)this.getGenricDao().selectOne(vo);
	}
	
	@Override
	public Pagination getPagination(AbstractObjectVO<T> vo, int pageNo, int pageSize) throws Exception {
		long recordCount=this.getGenricDao().selecCount(vo);
		Pagination pagination=new Pagination(pageNo,pageSize,recordCount);
		return pagination;
	}

	@Override
	public PaginationResult<T> getPaginationResult(AbstractObjectVO<T> vo,int pageNo,int pageSize) throws Exception {
		long recordCount=this.getGenricDao().selecCount(vo);
		Pagination pagination=new Pagination(pageNo,pageSize,recordCount);
		List<T> results=this.getGenricDao().selectList(vo, pagination);
		return new PaginationResult(results,pagination);
	}

	@Override
	public PaginationResult getVoPaginationResult(AbstractObjectVO<T> vo, int pageNo, int pageSize) throws Exception {
		long recordCount=this.getGenricDao().selecCount(vo);
		Pagination pagination=new Pagination(pageNo,pageSize,recordCount);
		List<AbstractObjectVO<T>> results=this.getGenricDao().selectVoList(vo, pagination);
		return new PaginationResult(results,pagination);
	}

	@Override
	public List<T> getMatch(AbstractObjectVO vo) throws Exception {
		return this.getGenricDao().selectMatchList(vo);
	}

	@Override
	public List<T> getByIds(PK[] ids) {
		return this.getGenricDao().selectByIds(ids);
	}

	@Override
	public Long saveEntitySelective(T t) throws Exception {
		return this.getGenricDao().insertEntitySelective(t);
	}

	@Override
	public Long modifyEntitySelective(T t) throws Exception {
		return this.getGenricDao().updateEntitySelectiveById(t);
	}

}
