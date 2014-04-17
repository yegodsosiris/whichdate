$(function() {
	
	function EventsController(contacts ) {
		var self = this;
		
		// Construct required models.
		self.events = ko.observableArray([]);
		self.newevent;
		
		// provide state
		self.editMode = ko.observable(false);
		self.inlineedit = ko.observable(false);
		
		self.newevent = new EventViewModel(null, contacts);
		// initialise models from RESTful call
	    $.getJSON(contextpath+"/rest/events", function(allData) {
	        var mappedTasks = $.map(allData, function(item) { 
	        	return new EventViewModel(item, contacts); 
	        });
	        self.events(mappedTasks);
	    }).complete(function() {
	    	fmOff('');
	    });

	    
	    self.inlineEditAction = function() {
	    	self.inlineedit(true);
	    };
	    
	    self.sendInvitesAction = function(event) {
	    	event.update();
	    	event.setInvitesSent();
	    	fmOn('Sending invites...');
	        $.ajax(contextpath+"/rest/invites/"+event.parentId(), {
	            type: "post", 
	            contentType: "application/json",
	            success : function(result) {
					fmOff('Sent');
				}
	        });
	        event.setSendingMode(false);
	    };
	    
	    self.sendInviteUpdateAction = function(event) {
	    	event.update();
	    	fmOn('Sending invites...');
	        $.ajax(contextpath+"/rest/inviteupdate", {
	        	data : ko.toJSON(new EventViewModelDto(event)),
				async:false,
				type : "post",
				contentType : "application/json",
				success : function(result) {
					fmOff('Sent');
				},
				error: function(msg) {
					fmError(msg);
				}
	        });
	        event.setSendingMode(false);
	    };
	    
	    self.sendReminderAction = function(event) {
	    	event.update();
	    	event.setInvitesSent();
	    	fmOn('Sending invites...');
	        $.ajax(contextpath+"/rest/reminders/"+event.parentId(), {
	            type: "post", 
	            contentType: "application/json",
	            success : function(result) {
					fmOff('Sent');
				}
	        });
	        event.setSendingMode(false);
	    };
	    
	    self.cancelSendMode = function(event) {
	        event.setSendingMode(false);
	    };
	    
	    self.closeInlineEditAction = function() {
	    	self.inlineedit(false);
	    };
	    
	    self.setDateMode = function(event) {
	    	event.setDateMode(true);
	    	enableDatePicker();
	    };
	    
	    self.setInviteeMode = function(event) {
	    	event.setInviteeMode(true);
	    };
	    
	    self.unsetInviteeMode = function(event) {
	    	event.setInviteeMode(false);
	    };
	    
	    self.addProposedInvitee = function(event) {
	    	event.addProposedInvitee();
	    	event.setInviteeMode(false);
	    };
	    
	    self.addProposedDate = function(event) {
	    	event.addProposedDate();
	    	event.setDateMode(false);
	    };
	    
	    self.unsetDateMode = function(event) {
	    	event.setDateMode(false);
	    };
	    
	    self.resetNew = function() {
	    	self.newevent = new EventViewModel();
	    };
		
	    // Controller actions
	    self.deleteAction = function(event) {
	    	self.editMode(false);
	    	self.events.remove(event);
	    	event.deleteEvent();
	    };
	    self.updateAction = function(event) {
	    	event.update();
	        event.setSendingMode(true);
	    };
	    self.inviteButtonAction = function(event) {
	        event.setSendingMode(true);
	    };
	    self.saveAction = function(event) {
	    	event.isNew(false);
		    event.editMode(false);
		    self.editMode(false);
	    	event.save();
	    	self.events.push(event);
	    	self.resetNew();
	    };
		self.createAction = function() {
			self.editMode(true);
		    self.newevent.editMode(true);
		    enableDatePicker();
		};
		self.cancelAction = function() {
			self.editMode(false);
			self.newevent.editMode(false);
		};
		self.rowAction = function(item) {
		    self.editMode(true);
		    item.editMode(true);
		   // enableDatePicker();
		   // enableEditor();
		};
		self.closeAction = function(item) {
			self.editMode(false);
		    item.editMode(false);
		};
	}
	ko.applyBindings(new EventsController(my_contacts));
	
});