package com.helper.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import com.helper.domain.EmailVO;
import com.helper.persistence.EmailDAO;

@Service
public class EmailServiceImpl implements EmailService {

	@Inject
	private EmailDAO dao;

	@Override
	public void regist(EmailVO vo) throws Exception {
		dao.create(vo);
	}

	@Override
	public void updateAge(EmailVO vo) throws Exception {
		dao.updateAge(vo);
	}

	@Override
	public EmailVO read(String emailAddress) throws Exception {
		return dao.read(emailAddress);
	}

	@Override
	public void modify(EmailVO diet) throws Exception {
		dao.update(diet);
	}

	@Override
	public void remove(String emailAddress) throws Exception {
		dao.delete(emailAddress);
	}

	@Override
	public List<EmailVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	public String getEmailByString(String emailAddress) throws Exception {
		return dao.readByString(emailAddress);
	}
}
