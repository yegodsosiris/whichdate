<div data-bind="visible: editMode()">

<%@ include file="event/new_date.jsp" %>
<%@ include file="event/new_invitee.jsp" %>
<%@ include file="event/notifications.jsp" %>


 
<!-- 
	<div data-bind="text: 'event.editMode '+editMode()"></div>
	<div data-bind="text: 'event.isNew '+isNew()"></div>
	<div data-bind="text: 'event.dateMode '+dateMode()"></div>
	<div data-bind="text: 'event.inviteeMode '+inviteeMode()"></div>
 -->
 <div class="mainContainer" data-bind="visible: !dateMode() && !inviteeMode() && !inviteeMultiMode() && !sendingMode()">
 
	<%@ include file="event/buttons.jsp" %>
	<div data-bind="visible: deleteMode()">
		<h4>Do you want to delete this event?</h4>
		<button class="btn btn-danger btn-small" data-bind="click:  $parent.deleteAction">Delete</button>
		<button class="btn btn-warning btn-small" data-bind="click: unsetDeleteMode">Cancel</button>
	</div>
	
	<div class="event-header">
	  <div class="title" data-bind="text: title"></div>
	  <div class="subtitle" data-bind="text: location"></div>
	</div>
	

	<div class="bs-docs-example">
		<ul id="myTab" class="nav nav-tabs">
			<li class="active"><a data-bind="attr: {href: '#details'+uid()}" data-toggle="tab"><i class=icon-pencil></i></a></li>
			<li><a data-bind="attr: {href: '#dates'+uid()}" data-toggle="tab">Dates</a></li>
			<li><a data-bind="attr: {href: '#invitees'+uid()}" data-toggle="tab">Invites</a></li>
			<li><a data-bind="attr: {href: '#report'+uid()}" data-toggle="tab">Replies</a></li>
			<li><a data-bind="attr: {href: '#help'+uid()}" data-toggle="tab">Help</a></li>
			<!-- <li><a data-bind="attr: {href: '#email'+uid()}" data-toggle="tab"><i class=icon-envelope></i></a></li>
			<li><a data-bind="attr: {href: '#settings'+uid()}" data-toggle="tab"><i class="icon-cog"></i></a></li> -->
		</ul>
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane fade in active" data-bind="attr: {id: 'details'+uid()}">
				<label for="title">Event Title</label>
				<input type="text" id="title" data-bind="value: title"	placeholder="Event Title"> <br>
				<label for="location">Location</label>
				<input type="text" id="location" data-bind="value: location" placeholder="Location"><br>
				<label for="description">Description</label>
				<textarea id="description" data-bind="value: description" rows="6" cols="30" style="width: 80%" placeholder="Description"></textarea>
			</div>
			<div class="tab-pane fade" data-bind="attr: {id: 'dates'+uid()}">
				<%@include file="proposed_dates.jsp"%>
			</div>
			<div class="tab-pane fade" data-bind="attr: {id: 'invitees'+uid()}">
				<%@include file="invitees.jsp"%>
			</div>

			<div class="tab-pane fade" data-bind="attr: {id: 'report'+uid()}">
				<%@ include file="event/report.jsp"%>
			</div>
			
			<div class="tab-pane fade" data-bind="attr: {id: 'help'+uid()}">
				<%@ include file="event/help.jsp"%>
			</div>
			<!-- 
			<div class="tab-pane fade" data-bind="attr: {id: 'email'+uid()}">
				<%@ include file="email.jsp" %>
			</div>
			
			<div class="tab-pane fade" data-bind="attr: {id: 'settings'+uid()}">
				<div>
					<label for="attendanceQuota">Attendance Quota:</label>
					 <select id="attendanceQuota" data-bind="value:quota">
					 	<option value="0">0</option>
					 	<option value="0">10</option>
					 	<option value="0">20</option>
					 	<option value="0">30</option>
					 	<option value="0">40</option>
					 	<option value="0">50</option>
					 	<option value="0">60</option>
					 	<option value="0">70</option>
					 	<option value="0">80</option>
					 	<option value="0">90</option>
					 	<option value="0">100</option>
					 </select>
				</div>
				<fieldset>
					<legend>Cut off date</legend>
					Set this date if you wish...
					<input id="hasCutoffDate" type="checkbox" data-bind="checked: hasCutoffDate">
					<input type="text" class="dateField" data-bind="value: cutoff">

				</fieldset>
			</div> -->
		</div>
	</div>

		

</div>
</div>