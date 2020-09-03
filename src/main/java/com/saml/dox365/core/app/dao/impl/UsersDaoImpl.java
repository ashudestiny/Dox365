package com.saml.dox365.core.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.saml.dox365.core.app.dao.UserDao;
import com.saml.dox365.core.app.domain.Users;
import com.saml.dox365.core.app.repository.UsersRepository;

@Component
public class UsersDaoImpl implements UserDao{

	@Autowired
	UsersRepository userRepository; 
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public void insertUser(Users user) {
		userRepository.insert(user);
	}

	@Override
	public Users findUserByName(String userName) {
		Users users = mongoTemplate.findOne(Query.query(Criteria.where("username").is(userName)), Users.class);
		return users;
	}

}
