package com.helper.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.helper.domain.BreakfastVO;
import com.helper.domain.DietVO;
import com.helper.domain.DinnerVO;
import com.helper.domain.LaunchVO;
import com.helper.domain.TotalkcalVO;

public interface DietService {
	public void regist(DietVO diet) throws Exception;

	public DietVO read(String emailAddress, Date transMealDays) throws Exception;

	public List<DietVO> read(String emailAddress) throws Exception;

	public void modify(DietVO diet) throws Exception;

	public void remove(String emailAddress, Date MealDays) throws Exception;

	public List<DietVO> listAll() throws Exception;

	public List<TotalkcalVO> TotalkcallistAll(String emailAddress);

	public List<BreakfastVO> BreakfastlistAll(String emailAddress);

	public List<LaunchVO> LaunchlistAll(String emailAddress);

	public List<DinnerVO> DinnerlistAll(String emailAddress);
}
