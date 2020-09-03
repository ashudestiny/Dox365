package com.saml.dox365.core.app.dao;

import com.saml.dox365.core.app.domain.Users;

public interface UserDao {

	public void insertUser(Users users);

	public Users findUserByName(String userName);
}
