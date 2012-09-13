package com.survey.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


/**
 * 数据访问dao基类
 * 
 * @param <T>
 * @param <PK>
 */
public abstract class AbstractGenericDaoImpl<T, PK extends Serializable> implements
		GenericDao<T, PK> {

	@Autowired(required=false)
	protected SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired(required=false)
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public SqlSessionTemplate getSqlSessionTemplate() {
		if(this.sqlSessionTemplate==null){
			throw new RuntimeException("sqlSessionTemplate not to init!");
		}
		return sqlSessionTemplate;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		if(this.namedParameterJdbcTemplate==null){
			throw new RuntimeException("namedParameterJdbcTemplate not to init!");
		}
		return namedParameterJdbcTemplate;
	}
	
	@Override
	public String namespace() {
		throw new RuntimeException("children is not init namespace method.");
	}

	@Override
	public T selectById(PK id) throws Exception {
		return this.getSqlSessionTemplate().selectOne(this.namespace()+".selectById",id);
	}

	@Override
	public Long insertEntity(T t) throws Exception {
		return (long) this.getSqlSessionTemplate().update(this.namespace()+".insert", t);
	}

	@Override
	public Long updateEntity(T t) throws Exception {
		return (long) this.getSqlSessionTemplate().update(this.namespace()+".update", t);
	}

	@Override
	public Long deleteById(PK id) throws Exception {
		return (long) this.getSqlSessionTemplate().delete(this.namespace()+".delete", id);
	}
	
	@Override
	public Long selecCount(T query) throws Exception {
		return (Long) this.getSqlSessionTemplate().selectOne(this.namespace()+".selectCount", query);
	}

	@Override
	public List<T> selectList(T query, int offset,int limit)throws Exception {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectList",query,new RowBounds(offset, limit));
	}
	
	@Override
	public List<T> selectMatchList(T query) throws Exception {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectMatchList",query,new RowBounds(0, 1000));
	}
	
	@Override
	public T selectOne(T query) throws Exception {
		return this.getSqlSessionTemplate().selectOne(this.namespace()+".selectOne",query);
	}

	@Override
	public long matchCount(T query) throws Exception {
		return this.getSqlSessionTemplate().selectOne(this.namespace()+".matchCount",query);
	}

	@Override
	public boolean isExist(T query) throws Exception {
		long matchCount=this.matchCount(query);
		return matchCount>0?true:false;
	}

	@Override
	public long deleteByQuery(T query) throws Exception {
		return (long) this.getSqlSessionTemplate().delete(this.namespace()+".deleteByQuery", query);
	}

	@Override
	public long updateEntitySelectiveById(T t) throws Exception {
		return (long) this.getSqlSessionTemplate().update(this.namespace()+".updateSelectiveById", t);
	}

	@Override
	public long increaseEntitySelectiveById(T t) throws Exception {
		return (long) this.getSqlSessionTemplate().update(this.namespace()+".increaseSelectiveById", t);
	}

	@Override
	public long decreaseEntitySelectiveById(T t) throws Exception {
		return (long) this.getSqlSessionTemplate().update(this.namespace()+".decreaseSelectiveById", t);
	}

	@Override
	public long insertEntitySelective(T t) throws Exception {
		return (long) this.getSqlSessionTemplate().update(this.namespace()+".insertSelective", t);
	}

	@Override
	public List<T> selectEntitySelective(T t) throws Exception {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectSelective",t);
	}

	@Override
	public List<T> selectByIds(PK[] ids) {
		return this.getSqlSessionTemplate().selectList(this.namespace()+".selectByIds",ids);
	}
	
}
