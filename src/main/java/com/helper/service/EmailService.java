package com.helper.service;

import java.util.List;

import com.helper.domain.EmailVO;

public interface EmailService {
	public void regist(EmailVO diet)throws Exception;
	
	public EmailVO read(String emailAddress)throws Exception;
	
	public void modify(EmailVO diet)throws Exception;
	
	public void remove(String emailAddress)throws Exception;
	
	public List<EmailVO> listAll()throws Exception;
}
