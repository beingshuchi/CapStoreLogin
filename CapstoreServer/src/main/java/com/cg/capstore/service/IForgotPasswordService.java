package com.cg.capstore.service;

import com.cg.capstore.exception.UserNotFoundException;

public interface IForgotPasswordService {
	String forgotPassword(String email) throws UserNotFoundException;
}
