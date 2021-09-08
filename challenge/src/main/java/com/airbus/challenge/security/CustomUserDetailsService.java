package com.airbus.challenge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airbus.challenge.dao.UserDAO;
import com.airbus.challenge.entity.UserEntity;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserDAO userDao;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		
		UserEntity userEntity = userDao.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
				.orElseThrow(() -> 
				new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
		return UserPrincipal.create(userEntity);
	}
	
	@Transactional
	public UserDetails loadUserById(Long id) {
		UserEntity userEntity = userDao.findById(id).orElseThrow(
				()-> new UsernameNotFoundException("User not found with id: "+ id));
		return UserPrincipal.create(userEntity);
	}

}










