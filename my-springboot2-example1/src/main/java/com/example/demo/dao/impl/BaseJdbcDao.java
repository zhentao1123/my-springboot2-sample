package com.example.demo.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.annotation.Persistent;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Persistent
public class BaseJdbcDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}
	
	private <R> RowMapper<R> getRowMapper(Class<R> clazz) {
		return BeanPropertyRowMapper.newInstance(clazz);
	}
	
	public <V> V queryValue(String sql, Class<V> requiredType, Object... args) throws Exception {
		try {  
	    	return (args!=null && args.length!=0) ? 
	    			getJdbcTemplate().queryForObject(sql, args, requiredType) : 
	    			getJdbcTemplate().queryForObject(sql, requiredType);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <V> List<V> queryValueList(String sql, Class<V> requiredType, Object... args) throws Exception {
		try {  
	    	return (args!=null && args.length!=0) ? 
	    			getJdbcTemplate().queryForList(sql, requiredType, args):
	    			getJdbcTemplate().queryForList(sql, requiredType);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public Map<String, Object> queryMap(String sql, Object... args) throws Exception {
		try {
			return getJdbcTemplate().queryForMap(sql, args);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public List<Map<String, Object>> queryMapList(String sql, Object... args) throws Exception {
		try {  
	    	return (args!=null && args.length!=0) ? 
	    			getJdbcTemplate().queryForList(sql, args) : 
	    			getJdbcTemplate().queryForList(sql);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <O> O queryObject(String sql, final Class<O> requiredType, Object... args) throws Exception {
		try {
			return getJdbcTemplate().queryForObject(sql, args, getRowMapper(requiredType));
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <O> List<O> queryObjectList(String sql, final Class<O> requiredType, Object... args) throws Exception {
		try {  
	    	return (args!=null && args.length!=0) ? 
	    			getJdbcTemplate().query(sql, args, getRowMapper(requiredType)) : 
	    				getJdbcTemplate().query(sql, getRowMapper(requiredType));
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public void update(String sql, Object... args) throws Exception {
		getJdbcTemplate().update(sql, args);
	}

	public void batchUpdate(final String sql, List<Object[]> batchArgs) throws Exception{
		if(batchArgs!=null && !batchArgs.isEmpty()) {
			getJdbcTemplate().batchUpdate(sql, batchArgs);
		}else {
			getJdbcTemplate().batchUpdate(sql);
		}
	}
	
	//-------------------------------------------------------------------

	public <V> V queryValue(String sql, Class<V> requiredType, Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().queryForObject(sql, args, requiredType);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <V> List<V> queryValueList(String sql, Class<V> requiredType, Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().queryForList(sql, args, requiredType);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public Map<String, Object> queryMap(String sql, Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().queryForMap(sql, args);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public List<Map<String, Object>> queryMapList(String sql, Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().queryForList(sql, args);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <O> O queryObject(String sql, final Class<O> requiredType, Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().queryForObject(sql, args, getRowMapper(requiredType));
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <O> List<O> queryObjectList(String sql, final Class<O> requiredType, Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().query(sql, args, getRowMapper(requiredType));
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public void update(String sql, Map<String, ?> args) throws Exception {
		getNamedParameterJdbcTemplate().update(sql, args);
	}
	
	public void batchUpdate(final String sql, Map<String, ?>[] batchArgs) throws Exception{
		getNamedParameterJdbcTemplate().batchUpdate(sql, batchArgs);
	}

}
