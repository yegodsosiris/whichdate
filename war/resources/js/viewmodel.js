$(function() {
	
	EmailViewModel = function(data) {
		var self=this;
		self.subject='';
		self.body='';
		if (data) {
			self.subject=data.subject;
			self.body=data.body;
		}
	};

	EventViewModelDto = function(data) {
		var self=this;
		self.emailTemplate = data.emailTemplate;
		self.parentId = data.parentId;
		self.title = data.title();
		self.quota = data.quota();
		self.invitesSent = data.invitesSent();
		self.description = data.description;
		self.email = data.email;
		self.location = data.location();
		self.invitees = data.invitees();
		self.notifiers = data.notifiers();
		self.proposedDates = data.proposedDates();
	};

	EventViewModel = function(data, contacts) {
	    
		var self = this;
		
		self.calendarEventCreated=ko.observable(false);

		self.contacts = ko.observableArray([]);
		self.notifiers = ko.observableArray([]);
	    if (contacts) {
			var mappedInvitees = $.map(contacts, function(item) {
				return new InviteeViewModel(item);
			});
			self.contacts(mappedInvitees);
		}
	    self.findContact = function(email) {
	    	for (i=0;i<self.contacts().length;i++) {
	    		if (self.contacts()[i].email()==email) {
	    			var c = self.contacts()[i];
	    			return c;
	    		}
	    	}
	    };
		
		
		self.invitees = ko.observableArray([]);
		self.hasCutoffDate = ko.observable(false);
		self.cutoff = Date.today().toString('yyyy-MM-dd');
		self.proposedDate = new DateViewModel();
		self.proposedInvitee = new InviteeViewModel();
		self.proposedDates = ko.observableArray([]);
		
		self.email = '';
		
		self.isNew = ko.observable(true);
		self.editMode = ko.observable(false);
		self.dateMode = ko.observable(false);
		self.emailMode = ko.observable(true);
		self.inviteeMode = ko.observable(false);
		self.inviteeMultiMode = ko.observable(false);
		self.invitesSent = ko.observable(false);
		self.sendingMode = ko.observable(false);
		
		self.parentId = ko.observable(null);
		self.title = ko.observable('');
		self.quota = ko.observable("0");
		self.description = '';
		self.location = ko.observable('');
		self.eventReportDto = null;

		self.emailTemplate = new EmailViewModel();
		
		self.deleteMode = ko.observable(false);
		self.setDeleteMode = function() {
			self.deleteMode(true);
		};
		self.unsetDeleteMode = function() {
			self.deleteMode(false);
		};
		
		self.addNotifier = function(contact) {
			if(contact.onNotificationList()) {
				contact.onNotificationList(false);
				self.notifiers.remove(contact);
			}
			else {
				contact.onNotificationList(true);
				self.notifiers.push(contact);
			}
		};
		
		self.addContact = function(contact) {
			if(contact.invited()) {
				contact.invited(false);
				self.invitees.remove(contact);
			}
			else {
				contact.invited(true);
				self.invitees.push(contact);
			}
		};
	    
		self.uid=function() {
			return 'event';
		};
	    self.setDateMode = function(mode) {
	    	self.dateMode(mode);
	    };
	    self.setSendingMode = function(mode) {
	    	if (self.sendInvites() || self.sendUpdatedInvites()) {
		    	self.sendingMode(mode);
	    	}
	    };
	    self.setInvitesSent = function() {
	    	self.invitesSent(true);
	    };
	    self.setInviteeMode = function(mode) {
	    	self.inviteeMode(mode);
	    };
	    self.setInviteeMultiMode = function(mode) {
	    	self.inviteeMultiMode(mode);
	    };
		if (data) {
			self.uid =  function() {
				return 'event'+self.parentId();
			};
			self.calendarEventCreated=ko.observable(data.calendarEventCreated);
			self.emailTemplate = new EmailViewModel(data.emailTemplate);
			self.parentId = ko.observable(data.parentId);
			self.isNew = ko.observable(false);
			self.title = ko.observable(data.title);
			self.quota = ko.observable(data.quota);
			self.invitesSent(data.invitesSent);
			self.description = data.description;
			self.email = data.email;
			self.location = ko.observable(data.location);
			self.eventReportDto = new EventReportDto(data.eventReportDto);
			if (data.invitees) {
				var mappedInvitees = $.map(data.invitees, function(item) {
					var c = self.findContact(item.email);
					if(c) {
						c.importance = ko.observable(item.importance);
						c.encEmail=item.encEmail;
						c.invited(true);
						return c;
					}
				});
				self.invitees(mappedInvitees);
			}
			if (data.proposedDates) {
				var mapedDates = $.map(data.proposedDates, function(item) {
					return new DateViewModel(item);
				});
				self.proposedDates(mapedDates);
			}
		}
		self.deleteEvent = function() {
	    	fmOn('Deleting ....');
	        $.ajax(contextpath+"/rest/events/"+self.parentId(), {
	            type: "delete", 
	            contentType: "application/json",
	            success : function(result) {
					fmOff('Deleted');
				},
				error: function(msg) {
					fmError(msg);
				}
	        });
	    };
		self.editLink = function() {
			return contextpath+"/rest/event/" + self.parentId;
		};
		self.addProposedDate = function(date) {
			var dt = new DateViewModel();
			dt.from (new Date(self.proposedDate.from()).toString('yyyy-MM-dd'));
			dt.to (new Date(self.proposedDate.to()).toString('yyyy-MM-dd'));
			dt.fromTime =  self.proposedDate.fromTime;
			dt.toTime =  self.proposedDate.toTime;
			dt.allDay(self.proposedDate.allDay());
			self.proposedDates.push(dt);
		};
		self.addProposedInvitee = function(item) {	
			var c = self.findContact(self.proposedInvitee.email());
			if (c) {
				c.importance = ko.observable(self.proposedInvitee.importance());
				c.encEmail=self.proposedInvitee.encEmail;
				c.invited(true);
				self.invitees.push(c);
			}
		};
		self.addDate = function() {
			self.proposedDates.push(new DateViewModel());
		};
		self.deleteInvitee = function(invitee) {
			invitee.invited(false);
			self.invitees.remove(invitee);
		};
		self.save = function() {
			fmOn('Saving ....');
			$.ajax(contextpath+"/rest/events", {
				data : ko.toJSON(new EventViewModelDto(self)),
				async:false,
				type : "post",
				contentType : "application/json",
				success : function(result) {
					fmOff('Saved');
					self.parentId(result.parentId);
					self.email = result.email;
					var mappedInvitees = $.map(result.invitees, function(item) {
						var c = self.findContact(item.email);
						if(c) {
							c.importance = ko.observable(item.importance);
							c.encEmail=item.encEmail;
							c.invited(true);
							return c;
						}
					});
					self.invitees(mappedInvitees);
					self.isNew = ko.observable(false);
				},
				error: function(msg) {
					fmError(msg);
				}
			});
		};
		self.update = function() {
			fmOn('Updating ....');
			$.ajax(contextpath+"/rest/events", {
				data : ko.toJSON(new EventViewModelDto(self)),
				type : "put",
				contentType : "application/json",
				success : function(result) {
					fmOff('Updated');
				},
				error: function(msg) {
					fmError(msg);
				}
			});
		};
		self.deleteDate = function(date) {
			self.proposedDates.remove(date);
		};

		self.sendInvites = ko.computed(function() {
			invlength = self.invitees().length;
			datelength = self.proposedDates().length;
			return invlength > 0 && datelength > 0 && !self.invitesSent();
		}, this);

		self.sendUpdatedInvites = ko.computed(function() {
			invlength = self.invitees().length;
			datelength = self.proposedDates().length;
			return invlength > 0 && datelength > 0 && self.invitesSent();
		}, this);
	};
	
	EventReportDto = function(data) {
		console.log(ko.toJSON(data));
		var self = this;
		self.id=data.id;
		self.calendarEventCreated=ko.observable(data.calendarEventCreated);
		self.calendarEventCreatedFailed=ko.observable(false);
		self.createCalendarEvent = function(date) {
	    	fmOn('Creating ....');
			self.calendarEventCreatedFailed=ko.observable(false);
	        $.ajax(contextpath+"/rest/calendar/create", {
	            type: "post", 
				data : ko.toJSON(new CalendarEventDto(date, self.id)),
	            contentType: "application/json",
	            success : function(result) {
	            	if(result==true) {
	            		date.unsetCreatingCalendarEvent();
	            		self.calendarEventCreated(true);
	            	}
	            	else {
	            		self.calendarEventCreatedFailed=ko.observable(true);
	            	}
					fmOff('Google Calendar Event Created');
				},
				error: function(msg) {
					fmError(msg);
				}
	        });
	    };
		self.dateReportDtos = ko.observableArray([]);
		
		if (data) {
			if (data.dateReportDtos) {
				var mappedInvitees = $.map(data.dateReportDtos, function(item) {
					return new DateReportDto(item);
				});
				self.dateReportDtos(mappedInvitees);
			}
		}
	};
	
	CalendarEventDto = function(data, id) {
		var self = this;
		self.eventId = id;
		self.from = data.from;
		self.to = data.to;
	};
	
	DateReportDto = function(data) {
		var self = this;
		
		self.creatingCalendarEvent = ko.observable(false);
		self.setCreatingCalendarEvent = function() {
			self.creatingCalendarEvent(true);
		};
		self.unsetCreatingCalendarEvent = function() {
			self.creatingCalendarEvent(false);
		};
		
		self.from = null;
		self.fromTime = null;
		self.to = null;
		self.toTime = null;
		self.allDay = ko.observable(data.allDay);
		self.inviteeReportDtos = ko.observableArray([]);
		self.score = 0;
		self.max=0;
		self.status = '';
		self.bestMatch = false;
		if (data) {
			self.uid =  function() {
				return 'report'+self.from()+self.to();
			};
			self.from = ko.observable(new Date(data.from).toString('yyyy-MM-dd'));
			self.to = ko.observable(new Date(data.to).toString('yyyy-MM-dd'));
			self.fromTime = data.fromTime;
			self.toTime = data.toTime;
			self.status=data.status;
			self.bestMatch = data.bestMatch;
			self.score=data.score;
			self.max=data.max;
			if (data.inviteeReportDtos) {
				var mappedInvitees = $.map(data.inviteeReportDtos, function(item) {
					return new InviteeReportDto(item);
				});
				self.inviteeReportDtos(mappedInvitees);
			}
		}
		
		self.fromDateAsString = ko.computed(function() {
			var d =  Date.parse(self.from()).toString('ddd dd MMMM yyyy');
			return self.allDay() ? d : d + ' [' + self.fromTime + ']';
		}, this);
		
		self.toDateAsString = ko.computed(function() {
			var d =  Date.parse(self.to()).toString('ddd dd MMMM yyyy');
			return self.allDay() ? '' : d + ' [' + self.toTime + ']';
		}, this);
		
		self.dateRange = function() {
			return self.allDay() ? self.fromDateAsString() : 
				self.sameDay() ? self.fromDateAsString() + ' - [' + self.toTime + ']' : self.fromDateAsString() + ' - ' + self.toDateAsString();
		};
		
		self.sameDay = ko.computed(function() { 
			return self.to() == self.from();
		}, this);
		
	};
	
	InviteeReportDto = function(data) {
		var self=this;
		self.result = data.result;
		self.fullname  = data.fullname;
		self.availability = data.availability;
		self.importance = data.importance;
	};

	DateViewModel = function(data) {
		var self = this;
		self.from = ko.observable(Date.today().toString('yyyy-MM-dd'));
		self.to = ko.observable(Date.today().toString('yyyy-MM-dd'));
		self.fromTime =  '00:00';
		self.toTime =  '00:00';
		self.allDay = ko.observable(true);
		self.deleteMode = ko.observable(false);
		
		self.unregistered = ko.observable(false);
		
		self.setDeleteMode = function() {
			self.deleteMode(true);
		};
		
		self.unsetDeleteMode = function() {
			self.deleteMode(false);
		};
		

		self.attendees = ko.observableArray([]);

		if (data) {
			
			self.from = ko.observable(new Date(data.from).toString('yyyy-MM-dd'));
			self.to = ko.observable(new Date(data.to).toString('yyyy-MM-dd'));
			
			self.fromTime = data.fromTime;
			self.toTime = data.toTime;
			self.allDay = ko.observable(data.allDay);

			if (data.attendees) {
				var mappedInvitees = $.map(data.attendees, function(item) {
					return new AttendeeViewModel(null, null, null, item);
				});
				self.attendees(mappedInvitees);
			}
		}
		
		self.fromDateAsString = ko.computed(function() {
			var d =  Date.parse(self.from()).toString('ddd dd MMMM yyyy');
			return self.allDay() ? d : d + ' [' + self.fromTime + ']';
		}, this);
		
		self.toDateAsString = ko.computed(function() {
			var d =  Date.parse(self.to()).toString('ddd dd MMMM yyyy');
			return self.allDay() ? '' : d + ' [' + self.toTime + ']';
		}, this);
		
		self.dateRange = function() {
			return self.allDay() ? self.fromDateAsString() : 
				self.sameDay() ? self.fromDateAsString() + ' - [' + self.toTime + ']' : self.fromDateAsString() + ' - ' + self.toDateAsString();
		};
		
		self.sameDay = ko.computed(function() { 
			return self.to() == self.from();
		}, this);
		

		self.addAttendee = function(email, attend) {
			var exists = false;
			ko.utils.arrayForEach(self.attendees(), function(item) {
			       if(item.email()==email) {
			    	   item.availability(attend);
			    	   exists=true;
			    	   self.unregistered(true);
			       }
			});
			if (!exists) {
				self.attendees.push(new AttendeeViewModel('Me', email, attend));
				self.unregistered(true);
			}
		};
		

		self.setInitInvitee = function(email) {
			ko.utils.arrayForEach(self.attendees(), function(item) {
				var e = item.email();
				var a = item.availability();
			       if(e==email) {
			    	   if (a==-1) {
			    		   self.unregistered(true);
			    	   }
			    	   else {
			    		   self.unregistered(false);
			    	   }
			       }
			});
		};
		
		self.deleteAttendee = function(invitee) {
			self.attendees.remove(invitee);
		};
	};

	AttendeeViewModel = function(fullname, email, availability, data) {
		var self=this;
		self.email = ko.observable(email);
		self.availability = ko.observable(availability);
		self.isFree = function(attendee) {
			if(attendee!=self.email()) return false;
			return self.availability()==2;
		};
		self.isBusy = function(attendee) {
			if(attendee!=self.email()) return false;
			return self.availability()==0;
		};
		self.isMaybe = function(attendee) {
			if(attendee!=self.email()) return false;
			return self.availability()==1;
		};
		self.fullname = ko.observable(fullname);
		if (data) {
			self.email = ko.observable(data.email);
			self.availability = ko.observable(data.availability);
			self.fullname = (data.fullname);
		}
	};

	InviteeViewModel = function(data) {
		var self = this;
		self.importance = ko.observable("3");
		self.isNew = ko.observable(true);
		self.email = ko.observable('');
		self.encEmail = '';
		self.invited = ko.observable(false);
		self.onNotificationList = ko.observable(false);
		self.fullname = ko.observable('');

		self.deleteMode = ko.observable(false);
		self.setDeleteMode = function() {
			self.deleteMode(true);
		};
		self.unsetDeleteMode = function() {
			self.deleteMode(false);
		};
		
		if (data) {
			self.isNew(false);
			self.importance = ko.observable(data.importance ? data.importance : '3');
			self.email = ko.observable(data.email);
			self.encEmail = data.encEmail;
			self.fullname = ko.observable(data.fullname);
		}
	};


});