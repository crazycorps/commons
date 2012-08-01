package com.survey.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.survey.dao.GenericDao;
import com.survey.service.pagination.Pagination;
import com.survey.service.pagination.PaginationResult;
import com.survey.service.vo.AbstractObjectVO;

/**
 * 业务处理基接口
 * 
 * @author jason
 * 
 * @param <T>
 * @param <PK>
 */
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public interface GenericService<R extends AbstractObjectVO<T>,T, PK extends Serializable> {
	
	GenericDao<T,PK> getGenricDao();
	
	Class<R> getClassR();
	
	@Transactional(propagation = Propagation.SUPPORTS)
	T getById(PK id) throws Exception ;
	
	@Transactional(propagation = Propagation.REQUIRED)
	Long saveEntity(T t) throws Exception ;
	
	@Transactional(propagation = Propagation.REQUIRED)
	Long saveEntitySelective(T t) throws Exception ;
	
	@Transactional(propagation = Propagation.REQUIRED)
	Long modifyEntity(T t) throws Exception ;
	
	@Transactional(propagation = Propagation.REQUIRED)
	Long modifyEntitySelective(T t) throws Exception ;
	
	@Transactional(propagation = Propagation.REQUIRED)
	Long removeById(PK id) throws Exception ;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	T get(T vo) throws Exception ;
	
	List<T> getMatch(T t) throws Exception;
	
	Pagination getPagination(T t,int pageNo,int pageSize) throws Exception ;
	
	PaginationResult<T> getPaginationResult(T t,int pageNo,int pageSize)throws Exception ;
	
	PaginationResult<R> getVoPaginationResult(T t,int pageNo,int pageSize)throws Exception ;
	
	List<T> getByIds(PK[] ids);
	
	
	
	
}
