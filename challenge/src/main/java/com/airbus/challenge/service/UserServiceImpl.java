package com.airbus.challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airbus.challenge.dao.UserDAO;
import com.airbus.challenge.entity.UserEntity;
import com.airbus.challenge.exception.UserServiceErrorCodesEnum;
import com.airbus.challenge.exception.UserServiceException;
import com.airbus.challenge.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public User getUserByUsernameorEmail(String usernameOrEmail) {
		
		logger.debug("START :: UserServiceImpl :: getUserByUsernameorEmail :: {}", usernameOrEmail);
		UserEntity userEntity = userDAO.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElse(null) ;
		User user = createUserFromUserEntity(userEntity);
		logger.debug("END :: UserServiceImpl :: getUserByUsernameorEmail :: {}", usernameOrEmail);
		
		return user;
	}

	@Override
	public User saveUser(User user) throws UserServiceException{
		
		logger.debug("START :: UserServiceImpl :: saveUser :: {}", user.getUsername());
		if(userDAO.existsByUsername(user.getUsername()))
			throw new UserServiceException(UserServiceErrorCodesEnum.USERNAME_ALREADY_EXIST.toString());
		if(userDAO.existsByEmail(user.getEmail()))
			throw new UserServiceException(UserServiceErrorCodesEnum.EMAIL_ALREADY_EXIST.toString());
		UserEntity userEntity = userDAO.save(createUserEntityFromUser(user));
		user = createUserFromUserEntity(userEntity);
		logger.debug("END :: UserServiceImpl :: saveUser :: {}", user.getUsername());
		
		return user;
	}
	
	public User createUserFromUserEntity(UserEntity userEntity) {
		
		User user = new User();
		user.setUsername(userEntity.getUsername());
		user.setEmail(userEntity.getEmail());
		user.setId(userEntity.getId());
		user.setPassword(userEntity.getPassword());
		return user;
	}
	
public UserEntity createUserEntityFromUser(User user) {
		
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(user.getUsername());
		userEntity.setEmail(user.getEmail());
		userEntity.setId(user.getId());
		userEntity.setPassword(user.getPassword());
		return userEntity;
	}

}
