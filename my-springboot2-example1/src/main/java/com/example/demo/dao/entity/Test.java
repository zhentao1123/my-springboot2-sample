package com.example.demo.dao.entity;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

import com.example.demo.dao.entity.BaseEntity;

/**
 * test
 * 
 */
@Entity
@Table(name="test")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Test extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false, length=20)
	private Long id;
	
	@Column(name="name", length=100)
	private String name;
	
	@Column(name="age", length=10)
	private Integer age;

	public Test(){
	}
	
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public Integer getAge(){
		return this.age;
	}
	
	public void setAge(Integer age){
		this.age = age;
	}
}
