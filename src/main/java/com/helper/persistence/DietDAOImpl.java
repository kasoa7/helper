package com.helper.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.helper.domain.DietVO;

@Repository
public class DietDAOImpl implements DietDAO{
	
	@Inject
	private SqlSession session;
	
	private static String namespace="com.helper.mapper.DietMapper";

	@Override
	public void create(DietVO vo) throws Exception {
		session.insert(namespace+".create",vo);
	}

	@Override
	public DietVO read(String emailAddress) throws Exception {
		return session.selectOne(namespace+".read",emailAddress);
	}

	@Override
	public void update(DietVO vo) throws Exception {

		session.update(namespace+".update",vo);
	}

	@Override
	public void delete(String emailAddress) throws Exception {
		session.delete(namespace+".delete", emailAddress);
	}

	@Override
	public List listAll() throws Exception {
		return session.selectList(namespace+".listAll");
	}
	
}
