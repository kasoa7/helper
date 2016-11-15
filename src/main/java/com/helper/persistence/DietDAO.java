package com.helper.persistence;

import java.util.List;

import com.helper.domain.DietVO;

public interface DietDAO {
	public void create(DietVO vo)throws Exception;
	
	public DietVO read(String emailAddress)throws Exception;
	
	public void update(DietVO vo)throws Exception;
	
	public void delete(String emailAddress)throws Exception;
	
	public List<DietVO> listAll()throws Exception;
}
