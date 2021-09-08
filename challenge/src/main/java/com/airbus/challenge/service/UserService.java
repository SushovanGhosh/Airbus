package com.airbus.challenge.service;

import com.airbus.challenge.exception.UserServiceException;
import com.airbus.challenge.model.User;

public interface UserService {

	public User getUserByUsernameorEmail(String usernameOrEmail);
	public User saveUser(User user) throws UserServiceException;
}
