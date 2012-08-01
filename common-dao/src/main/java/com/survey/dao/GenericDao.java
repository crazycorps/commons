package com.survey.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 数据访问dao基接口
 * 
 * @param <T>
 * @param <PK>
 */
public interface GenericDao<T,PK extends Serializable> {
	
	/**
	 * 每个继承该接口的dao，必须实现该接口，该接口主要针对mybatis的namespace
	 * @return
	 */
	String namespace();
	
	/**
	 * 通过主键来查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	T selectById(PK id) throws Exception ;
	
	Long insertEntity(T t) throws Exception ;
	
	Long updateEntity(T t) throws Exception ;
	
	Long deleteById(PK id) throws Exception ;
	
	Long selecCount(T query) throws Exception;
	
	/**
	 * 分页查询
	 * @param query
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<T> selectList(T query, int offset,int limit)throws Exception ;
	
	/**
	 * 查询满足条件的记录（默认只返回1000条）
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<T> selectMatchList(T query)throws Exception ;
	
	/**
	 * 查询满足条件的数据，不做分页处理
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<T> selectEntitySelective(T t)throws Exception ;
	
	/**
	 * 默认返回第一个,如果没有则返回null
	 * @param query
	 * @return
	 * @throws Exception
	 */
	T selectOne(T query)throws Exception;
	
	/**
	 * 查询匹配的条数
	 * @param query
	 * @return
	 * @throws Exception
	 */
	long matchCount(T query)throws Exception;
	
	/**
	 * 匹配的条件是否存在记录
	 * @param query
	 * @return
	 * @throws Exception
	 */
	boolean isExist(T query)throws Exception;
	
	/**
	 * 删除满足查询条件的记录
	 * @param query
	 * @return
	 * @throws Exception
	 */
	long deleteByQuery(T query)throws Exception;
	
	/**
	 * 通过主键，更新设置的属性
	 * @param query
	 * @return
	 * @throws Exception
	 */
	long updateEntitySelectiveById(T t)throws Exception;
	
	/**
	 * 插入数据，通过选择插入
	 * @param t
	 * @return
	 * @throws Exception
	 */
	long insertEntitySelective(T t)throws Exception;
	
	/**
	 * 通过主键，递增选择的属性值
	 * @param t
	 * @return
	 * @throws Exception
	 */
	long increaseEntitySelectiveById(T t)throws Exception;
	/**
	 * 通过主键，递减选择的属性值
	 * @param t
	 * @return
	 * @throws Exception
	 */
	long decreaseEntitySelectiveById(T t)throws Exception;
	
	List<T> selectByIds(PK[] ids);

}