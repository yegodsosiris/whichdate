package com.whichdate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.googleapi.util.URLConnectorHelper;

@Controller
public class AttendingJspController extends BaseController{
	
	@RequestMapping("/attend")
	public String attendEvent(@RequestParam String i, @RequestParam String e, ModelMap model) throws Exception {

		model.addAttribute("contacts", "''");
		model.addAttribute("email", URLConnectorHelper.decryptString((String) URLConnectorHelper.decode(e)));
		model.addAttribute("eventId",  i);
		return "attending";
	}
}
