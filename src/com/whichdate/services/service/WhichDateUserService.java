package com.whichdate.services.service;

import com.googleapi.service.GoogleUserService;
import com.googleapi.user.GoogleUser;

public interface WhichDateUserService extends GoogleUserService {
	public void saveUser(GoogleUser user);
	public GoogleUser getUser(String email);
}
