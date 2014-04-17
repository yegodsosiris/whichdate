loadPage = function() {
	fmOn();
	$.getJSON(contextpath+'/rest/events/'+eventId, function(item) {
		ko.applyBindings(new AttendanceViewModel(item));
    }).success(function() {
    	fmOff();
    });
};

$(function() {
	
	
	
	AttendanceViewModel = function(item) {
		var self=this;
		self.user=email;
		self.event=new EventViewModel(item);
		self.invitee=email;
		self.saveAttendance = function() {
			fmOn();
			$.ajax(contextpath+"/rest/event/attendance", {
				data : ko.toJSON(self),
				type : "post",
				contentType : "application/json",
				success : function(result) {
					fmOff('<h4>Thank you</h4>Your availability has been confirmed', 4000);
				},
				error: function(msg) {
					fmError(msg);
				}
			});
		};
	};
	loadPage();
	
	
});