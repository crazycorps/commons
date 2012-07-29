package com.jian.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jian.dao.GenericDao;
import com.jian.dao.pagination.AbstractObjectVO;
import com.jian.dao.pagination.Pagination;
import com.jian.service.pagination.PaginationResult;

/**
 * 业务处理基接口
 * 
 * @author jason
 * 
 * @param <T>
 * @param <PK>
 */
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
public interface GenericService<T, PK extends Serializable> {
	
	GenericDao getGenricDao();
	
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
	T get(AbstractObjectVO vo) throws Exception ;
	
	List<T> getMatch(AbstractObjectVO vo) throws Exception;
	
	Pagination getPagination(AbstractObjectVO<T> vo,int pageNo,int pageSize) throws Exception ;
	
	PaginationResult<T> getPaginationResult(AbstractObjectVO<T> vo,int pageNo,int pageSize)throws Exception ;
	
	PaginationResult getVoPaginationResult(AbstractObjectVO<T> vo,int pageNo,int pageSize)throws Exception ;
	
	List<T> getByIds(PK[] ids);
	
	
	
	
}
