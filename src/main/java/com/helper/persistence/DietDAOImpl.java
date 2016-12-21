package com.helper.persistence;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.helper.domain.BreakfastVO;
import com.helper.domain.DietVO;
import com.helper.domain.DinnerVO;
import com.helper.domain.LaunchVO;
import com.helper.domain.TotalkcalVO;

@Repository
public class DietDAOImpl implements DietDAO {

	@Inject
	private SqlSession session;

	private static String namespace = "com.helper.mapper.DietMapper";

	@Override
	public void create(DietVO vo) throws Exception {
		session.insert(namespace + ".create", vo);
	}

	@Override
	public DietVO read(String emailAddress, Date MealDays) throws Exception {
		Map<String, String> emailvalue = new HashMap<String, String>();
		Map<String, Date> datevalue = new HashMap<String, Date>();
		emailvalue.put("emailAddress", emailAddress);
		datevalue.put("Mealdays", MealDays);

		Map<Object, Object> merge = new HashMap<Object, Object>();
		merge.putAll(emailvalue);
		merge.putAll(datevalue);

		return session.selectOne(namespace + ".read", merge);
	}

	@Override
	public List listAll(String emailAddress) throws Exception {
		return session.selectList(namespace + ".listAll", emailAddress);
	}

	@Override
	public void update(DietVO vo) throws Exception {

		session.update(namespace + ".update", vo);
	}

	@Override
	public void delete(String emailAddress, Date MealDays) throws Exception {
		Map<String, String> emailvalue = new HashMap<String, String>();
		Map<String, Date> datevalue = new HashMap<String, Date>();
		emailvalue.put("emailAddress", emailAddress);
		datevalue.put("Mealdays", MealDays);

		Map<Object, Object> merge = new HashMap<Object, Object>();
		merge.putAll(emailvalue);
		merge.putAll(datevalue);

		session.delete(namespace + ".delete", merge);
	}

	@Override
	public List listAll() throws Exception {
		return session.selectList(namespace + ".listAll");
	}

	@Override
	public List<BreakfastVO> BreakfastlistAll(String emailAddress) {

		return session.selectList(namespace + ".breakfast", emailAddress);
	}

	@Override
	public List<LaunchVO> LaunchlistAll(String emailAddress) {
		return session.selectList(namespace + ".launch", emailAddress);
	}

	@Override
	public List<DinnerVO> DinnerlistAll(String emailAddress) {
		return session.selectList(namespace + ".dinner", emailAddress);
	}

	@Override
	public List<TotalkcalVO> TotalkcallistAll(String emailAddress) {
		return session.selectList(namespace + ".totalkcal", emailAddress);
	}

}
