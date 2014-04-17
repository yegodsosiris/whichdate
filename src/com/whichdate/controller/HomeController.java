package com.whichdate.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.googleapi.user.GoogleUser;

@Controller
public class HomeController extends BaseController  {
		
	
	public static final String HOME = "/";

	@RequestMapping(HOME)
	public String homepage(HttpServletRequest request, ModelMap model) throws Exception {
		GoogleUser googleUser = getCurrentUser(model);
		if (googleUser==null || StringUtils.isEmpty(googleUser.getAuthCode())) {
			return "not_authorised";
		}
		addContactsToModel(model);
		return "home";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletResponse response, ModelMap model, @RequestParam(required = false) String redirect) {
		response.addCookie(new Cookie(BaseController.COOKIE_KEY, null));
		return "redirect:"+HomeController.HOME;
	}

}
