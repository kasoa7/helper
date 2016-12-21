package com.helper.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.helper.domain.BreakfastVO;
import com.helper.domain.DietVO;
import com.helper.domain.DinnerVO;
import com.helper.domain.EmailVO;
import com.helper.domain.LaunchVO;
import com.helper.domain.TotalkcalVO;
import com.helper.persistence.DietDAO;

@Service
public class DietServiceImpl implements DietService {

	@Inject
	private DietDAO dao;

	@Override
	public void regist(DietVO diet) throws Exception {
		dao.create(diet);
	}

	@Override
	public DietVO read(String emailAddress, Date MealDays) throws Exception {
		return dao.read(emailAddress, MealDays);
	}

	@Override
	public List<DietVO> read(String emailAddress) throws Exception {
		return dao.listAll(emailAddress);
	}

	@Override
	public void modify(DietVO diet) throws Exception {
		dao.update(diet);
	}

	@Override
	public void remove(String emailAddress, Date MealDays) throws Exception {
		dao.delete(emailAddress, MealDays);
	}

	@Override
	public List<DietVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	public List<BreakfastVO> BreakfastlistAll(String emailAddress) {
		return dao.BreakfastlistAll(emailAddress);
	}

	@Override
	public List<LaunchVO> LaunchlistAll(String emailAddress) {
		return dao.LaunchlistAll(emailAddress);
	}

	@Override
	public List<DinnerVO> DinnerlistAll(String emailAddress) {
		return dao.DinnerlistAll(emailAddress);
	}

	@Override
	public List<TotalkcalVO> TotalkcallistAll(String emailAddress) {
		return dao.TotalkcallistAll(emailAddress);
	}
}
