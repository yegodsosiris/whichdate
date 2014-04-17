package com.whichdate.services.dto.report;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.whichdate.services.dto.WhichDateDateDto;
import com.whichdate.services.dto.WhichDateInviteeDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhichDateEventReportDto {

	Long id;
	List<WhichDateDateReportDto> dateReportDtos = new ArrayList<WhichDateDateReportDto>();
	private boolean calendarEventCreated;

	
	public List<WhichDateDateReportDto> getDateReportDtos() {
		return dateReportDtos;
	}
	
	public Long getId() {
		return id;
	}
	
	public boolean isCalendarEventCreated() {
		return calendarEventCreated;
	}

	public void createReport(Long id, boolean calendarEventCreated, List<WhichDateDateDto> proposedDates, List<WhichDateInviteeDto> invitees) {
		this.id=id;
		this.calendarEventCreated=calendarEventCreated;
		if (proposedDates != null) {
			int topScore = 0;
			for (WhichDateDateDto dateDto : proposedDates) {
				dateReportDtos.add(new WhichDateDateReportDto(dateDto, invitees));
			}
			for (WhichDateDateReportDto dateReportDto : dateReportDtos) {
				if (dateReportDto.getScore() > topScore) {
					topScore = dateReportDto.getScore();
				}
			}
			for (WhichDateDateReportDto dateReportDto : dateReportDtos) {
				if (dateReportDto.getScore() == topScore && dateReportDto.possible()) {
					dateReportDto.setBestMatch();
				}
			}
		}
	}
}
