package com.helper.persistence;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.helper.domain.BreakfastVO;
import com.helper.domain.DietVO;
import com.helper.domain.DinnerVO;
import com.helper.domain.LaunchVO;
import com.helper.domain.TotalkcalVO;

public interface DietDAO {
	public void create(DietVO vo) throws Exception;

	public DietVO read(String emailAddress, Date MealDays) throws Exception;

	public List listAll(String emailAddress) throws Exception;

	public void update(DietVO vo) throws Exception;

	public void delete(String emailAddress, Date MealDays) throws Exception;

	public List<DietVO> listAll() throws Exception;

	public List<BreakfastVO> BreakfastlistAll(String emailAddress);

	public List<LaunchVO> LaunchlistAll(String emailAddress);

	public List<DinnerVO> DinnerlistAll(String emailAddress);

	public List<TotalkcalVO> TotalkcallistAll(String emailAddress);
}
