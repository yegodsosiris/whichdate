package com.whichdate.services.dtoHelper;

import org.dozer.DozerBeanMapper;

import com.whichdate.services.domain.aggregated.WhichDateEvent;
import com.whichdate.services.domain.aggregated.WhichDateInvitee;
import com.whichdate.services.dto.WhichDateEventDto;
import com.whichdate.services.dto.WhichDateInviteeDto;

public class WhichDateEventHelper {
	
	private static DozerBeanMapper mapper;
	
	static {
		mapper = new DozerBeanMapper();
	}
	
	public static WhichDateEventDto getEventDto(WhichDateEvent p) {
		WhichDateEventDto dto = new WhichDateEventDto();
		try {
			mapper.map(p, dto);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return dto;
	}
	
	public static WhichDateInvitee getInvitee(WhichDateInviteeDto at) {
		WhichDateInvitee invitee = new WhichDateInvitee();
		mapper.map(at, invitee);
		return invitee;
	}
	
	public static WhichDateInviteeDto getInviteeDto(WhichDateInvitee at) {
		WhichDateInviteeDto invitee = new WhichDateInviteeDto();
		mapper.map(at, invitee);
		return invitee;
	}

	
	public static WhichDateEvent getEvent(WhichDateEventDto p) {
		WhichDateEvent dto = new WhichDateEvent();
		mapper.map(p, dto);
		return dto;
	}
	
}
