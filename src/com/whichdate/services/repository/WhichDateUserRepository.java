package com.whichdate.services.repository;

import com.googleapi.user.GoogleUser;

public interface WhichDateUserRepository {
	public void saveUser(GoogleUser user);
	public GoogleUser getUser(String email);
	public void updateUser(GoogleUser googleUser);
}
