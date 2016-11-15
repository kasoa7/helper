package com.helper.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import com.helper.domain.DietVO;
import com.helper.persistence.DietDAO;

@Service
public class DietServiceImpl implements DietService{
	
	@Inject
	private DietDAO dao;

	@Override
	public void regist(DietVO diet) throws Exception {
		dao.create(diet);
	}

	@Override
	public DietVO read(String emailAddress) throws Exception{
		//System.out.println(test.toString());
		return dao.read(emailAddress);
	}

	@Override
	public void modify(DietVO diet) throws Exception {
		dao.update(diet);
	}

	@Override
	public void remove(String emailAddress) throws Exception {
		dao.delete(emailAddress);
	}

	@Override
	public List<DietVO> listAll() throws Exception {
		return dao.listAll();
	}
	
}
