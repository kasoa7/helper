package com.helper.persistence;

import java.util.List;

import com.helper.domain.EmailVO;

public interface EmailDAO {
	public void create(EmailVO vo)throws Exception;
	
	public EmailVO read(String emailAddress)throws Exception;
	
	public void update(EmailVO vo)throws Exception;
	
	public void delete(String emailAddress)throws Exception;
	
	public List<EmailVO> listAll()throws Exception;
}
