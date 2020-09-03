package com.saml.dox365.core.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.saml.dox365.core.app.dao.UserDao;
import com.saml.dox365.core.app.domain.Users;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserDao usersDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = usersDao.findUserByName(username);
		Collection<? extends GrantedAuthority> authority = getAuthorities(user.getRoles()); 
		//GrantedAuthority authority = new SimpleGrantedAuthority(String.join(",", user.getRoles()));
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authority);
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(
			HashMap<String, ArrayList<String>> roles) {
			    List<GrantedAuthority> authorities
			      = new ArrayList<>();
			    for (String role: roles.keySet()) {
			        authorities.add(new SimpleGrantedAuthority(role));
			        roles.get(role).stream()
			         .map(p -> new SimpleGrantedAuthority(p))
			         .forEach(authorities::add);
			    }
			    
			    return authorities;
			}
	
	public void insertUser(Users user){
		usersDao.insertUser(user);
	}
	
	

}
