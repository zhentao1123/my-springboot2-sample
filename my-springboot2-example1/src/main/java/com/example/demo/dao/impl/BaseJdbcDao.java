package com.example.demo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.annotation.Persistent;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mysql.jdbc.Statement;

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
	
	public <V> V queryValue(final String sql, final Class<V> requiredType, final Object... args) throws Exception {
		try {  
	    	return (args!=null && args.length!=0) ? 
	    			getJdbcTemplate().queryForObject(sql, args, requiredType) : 
	    			getJdbcTemplate().queryForObject(sql, requiredType);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <V> List<V> queryValueList(final String sql, final Class<V> requiredType, final Object... args) throws Exception {
		try {  
	    	return (args!=null && args.length!=0) ? 
	    			getJdbcTemplate().queryForList(sql, requiredType, args):
	    			getJdbcTemplate().queryForList(sql, requiredType);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public Map<String, Object> queryMap(final String sql, final Object... args) throws Exception {
		try {
			return getJdbcTemplate().queryForMap(sql, args);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public List<Map<String, Object>> queryMapList(final String sql, final Object... args) throws Exception {
		try {  
	    	return (args!=null && args.length!=0) ? 
	    			getJdbcTemplate().queryForList(sql, args) : 
	    			getJdbcTemplate().queryForList(sql);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <O> O queryObject(final String sql, final Class<O> requiredType, final Object... args) throws Exception {
		try {
			return getJdbcTemplate().queryForObject(sql, args, getRowMapper(requiredType));
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <O> List<O> queryObjectList(final String sql, final Class<O> requiredType, final Object... args) throws Exception {
		try {  
	    	return (args!=null && args.length!=0) ? 
	    			getJdbcTemplate().query(sql, args, getRowMapper(requiredType)) : 
	    				getJdbcTemplate().query(sql, getRowMapper(requiredType));
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public void update(final String sql, final Object... args) throws Exception {
		getJdbcTemplate().update(sql, args);
	}

	public void batchUpdate(final String sql, final List<Object[]> batchArgs) throws Exception{
		if(batchArgs!=null && !batchArgs.isEmpty()) {
			getJdbcTemplate().batchUpdate(sql, batchArgs);
		}else {
			getJdbcTemplate().batchUpdate(sql);
		}
	}
	
	public Integer addObjectReturnIntId(final String sql, final Object... args) throws Exception {
		PreparedStatementCreator psc = new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				if(null!=args && args.length!=0) {
					for(int i=0; i<args.length; i++) {
						ps.setObject(i+1, args[i]);
					}
				}
				return ps;
			}
		};
		KeyHolder keyholder = new GeneratedKeyHolder();
		getJdbcTemplate().update(psc, keyholder);
		Integer id = keyholder.getKey().intValue();
		return id;
	}
	
	public Long addObjectReturnLongId(final String sql, final Object... args) throws Exception {
		PreparedStatementCreator psc = new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				if(null!=args && args.length!=0) {
					for(int i=0; i<args.length; i++) {
						ps.setObject(i+1, args[i]);
					}
				}
				return ps;
			}
		};
		KeyHolder keyholder = new GeneratedKeyHolder();
		getJdbcTemplate().update(psc, keyholder);
		Long id = keyholder.getKey().longValue();
		return id;
	}
	
	//-------------------------------------------------------------------

	public <V> V queryValue(final String sql, final Class<V> requiredType, final Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().queryForObject(sql, args, requiredType);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <V> List<V> queryValueList(final String sql, final Class<V> requiredType, final Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().queryForList(sql, args, requiredType);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public Map<String, Object> queryMap(final String sql, final Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().queryForMap(sql, args);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public List<Map<String, Object>> queryMapList(final String sql, final Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().queryForList(sql, args);
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <O> O queryObject(final String sql, final Class<O> requiredType, final Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().queryForObject(sql, args, getRowMapper(requiredType));
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public <O> List<O> queryObjectList(final String sql, final Class<O> requiredType, final Map<String, ?> args) throws Exception {
		try {  
			return getNamedParameterJdbcTemplate().query(sql, args, getRowMapper(requiredType));
	    } catch (EmptyResultDataAccessException e){
	    	return null;
	    }
	}

	public void update(final String sql, final Map<String, ?> args) throws Exception {
		getNamedParameterJdbcTemplate().update(sql, args);
	}
	
	public void batchUpdate(final String sql, final Map<String, ?>[] batchArgs) throws Exception{
		getNamedParameterJdbcTemplate().batchUpdate(sql, batchArgs);
	}
	
	/**
	 * 对象属性名必须和数据库完全一致
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public <O> Integer addObjectReturnIntId(final String sql, final O obj) throws Exception {
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(obj);
		KeyHolder keyholder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sql, paramSource, keyholder);
		Integer id = keyholder.getKey().intValue();
		return id;
	}
	
	/**
	 * 对象属性名必须和数据库完全一致
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public <O> Long addObjectReturnLongId(final String sql, final O obj) throws Exception {
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(obj);
		KeyHolder keyholder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sql, paramSource, keyholder);
		Long id = keyholder.getKey().longValue();
		return id;
	}

	public Integer addObjectReturnIntId(final String sql, final Map<String, ?> args) throws Exception {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		if(null!=args && !args.isEmpty()) {
			for (Map.Entry<String, ?> entry : args.entrySet()) {
				paramSource = paramSource.addValue(entry.getKey(), entry.getValue());
	        }
		}
		KeyHolder keyholder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sql, paramSource, keyholder);
		Integer id = keyholder.getKey().intValue();
		return id;
	}
	
	public Long addObjectReturnLongId(final String sql, final Map<String, ?> args) throws Exception {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		if(null!=args && !args.isEmpty()) {
			for (Map.Entry<String, ?> entry : args.entrySet()) {
				paramSource = paramSource.addValue(entry.getKey(), entry.getValue());
	        }
		}
		KeyHolder keyholder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(sql, paramSource, keyholder);
		Long id = keyholder.getKey().longValue();
		return id;
	}
}
