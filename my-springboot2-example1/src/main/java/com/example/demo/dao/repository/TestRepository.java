package com.example.demo.dao.repository;

import java.lang.Long;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.dao.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long>{
	public List<Test> findAll();
}