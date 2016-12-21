package com.helper.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.helper.domain.EmailVO;

@Repository
public class EmailDAOImpl implements EmailDAO {

	@Inject
	private SqlSession session;

	private static String namespace = "com.helper.mapper.EmailMapper";

	@Override
	public void create(EmailVO vo) throws Exception {
		session.insert(namespace + ".create", vo);
	}

	@Override
	public void updateAge(EmailVO vo) throws Exception {

		session.update(namespace + ".updateAge", vo);
	}

	@Override
	public EmailVO read(String emailAddress) throws Exception {
		return session.selectOne(namespace + ".read", emailAddress);
	}

	@Override
	public void update(EmailVO vo) throws Exception {
		session.update(namespace + ".update", vo);
	}

	@Override
	public void delete(String emailAddress) throws Exception {
		session.delete(namespace + ".delete", emailAddress);
	}

	@Override
	public List listAll() throws Exception {
		return session.selectList(namespace + ".listAll");
	}

	@Override
	public String readByString(String emailAddress) throws Exception {
		return session.selectOne(namespace + ".getEmailByString", emailAddress);
	}
}
