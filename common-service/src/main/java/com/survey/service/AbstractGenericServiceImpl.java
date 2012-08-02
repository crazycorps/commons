package com.survey.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.survey.dao.GenericDao;
import com.survey.service.pagination.Pagination;
import com.survey.service.pagination.PaginationResult;
import com.survey.service.vo.AbstractObjectVO;

/**
 * 业务处理实现基类。
 * 
 * @param <T>
 * @param <PK>
 */
public abstract class AbstractGenericServiceImpl<R extends AbstractObjectVO<T>,T, PK extends Serializable> implements
		GenericService<R,T, PK> {
	
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
	public T get(T vo) throws Exception {
		return (T)this.getGenricDao().selectOne(vo);
	}
	
	@Override
	public Pagination getPagination(T vo, int pageNo, int pageSize) throws Exception {
		int recordCount=this.getGenricDao().selecCount(vo).intValue();
		Pagination pagination=new Pagination(pageNo,pageSize,recordCount);
		return pagination;
	}

	@Override
	public PaginationResult<T> getPaginationResult(T t,int pageNo,int pageSize) throws Exception {
		int recordCount=this.getGenricDao().selecCount(t).intValue();
		Pagination pagination=new Pagination(pageNo,pageSize,recordCount);
		List<T> results=this.getGenricDao().selectList(t, pagination.getPageOffset(),pagination.getPageSize());
		return new PaginationResult<T>(results,pagination);
	}

	@Override
	public PaginationResult<R> getVoPaginationResult(T t, int pageNo, int pageSize) throws Exception {
		int recordCount=this.getGenricDao().selecCount(t).intValue();
		Pagination pagination=new Pagination(pageNo,pageSize,recordCount);
		List<T> results=this.getGenricDao().selectList(t, pagination.getPageOffset(),pagination.getPageSize());
		List<R> resultProcessedList=new ArrayList<R>();
		if(results!=null&&!results.isEmpty()){
			for(T tt:results){
				R rr=this.getClassR().newInstance();
				rr.setEntity(tt);
				resultProcessedList.add(rr);
			}
		}
		return new PaginationResult<R>(resultProcessedList,pagination);
	}

	@Override
	public List<T> getMatch(T t) throws Exception {
		return this.getGenricDao().selectMatchList(t);
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
