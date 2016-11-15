package com.helper.service;

import java.util.List;

import com.helper.domain.DietVO;

public interface DietService {
	public void regist(DietVO diet)throws Exception;
	
	public DietVO read(String emailAddress)throws Exception;
	
	public void modify(DietVO diet)throws Exception;
	
	public void remove(String emailAddress)throws Exception;
	
	public List<DietVO> listAll()throws Exception;
}
