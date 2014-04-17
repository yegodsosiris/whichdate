package com.whichdate.services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googleapi.service.GoogleAPIService;
import com.googleapi.user.GoogleUser;
import com.whichdate.services.repository.WhichDateUserRepository;

@Service
public class WhichDateUserServiceImpl implements WhichDateUserService {
	
	@Autowired
	WhichDateUserRepository userRepository;
	
	@Autowired
	GoogleAPIService googleAPIService;


	public void saveUser(GoogleUser user) {
		userRepository.saveUser(user);
	}

	
	public GoogleUser getUser(String email) {
		return userRepository.getUser(email);
	}

	
	public void updateUser(GoogleUser googleUser) {
		userRepository.updateUser(googleUser);
	}

}
