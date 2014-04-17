<%@include file="include/head.jsp"%>
<script>
	var eventId = '${eventId}';
	var email = '${email}';
	var contextpath='${contextpath}';
</script>


<div class="navbar">
  <div class="navbar-inner">
    <a class="brand" href="#">Which Date</a>
  </div>
</div>
<div id="maincontainer" style="display:none" class="container-fluid">

	<div class="event-header">
	  <div class="title" data-bind="text: event.title"></div>
	  <div class="subtitle" data-bind="text: event.location"></div>
	</div>
	
	Welcome <b><span data-bind="text:user"></span></b>. You have been invited to attend 
	<span data-bind="text: event.title"></span> @ <span data-bind="text: event.location"></span>.
	<p>
	<div class="mainmessage" data-bind="text:event.description"></div><p>
	<p>Once you have indicated your availability for the proposed dates please make sure you
	click the [Confirm] button at the bootom of this page.
	
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th width="50%">Date</th>
				<th width="50%">Are you available?</th>
			</tr>
		</thead>
		<tbody data-bind="foreach: event.proposedDates">
			<tr>
				<td data-bind="text: dateRange()"></td>
				<td>
					<div data-bind="visible: setInitInvitee($root.user) || unregistered()" class="btn-group">
						<button class="btn btn-primary" data-bind="click: addAttendee.bind($data, '${email}', '2')">Free</button>
						<button class="btn btn-primary" data-bind="click: addAttendee.bind($data, '${email}', '0')">Busy</button>
						<button class="btn btn-primary" data-bind="click: addAttendee.bind($data, '${email}', '1')">Maybe</button>
					</div>
					<div data-bind="foreach: attendees">
						<div data-bind="visible: isFree($root.user)" class="btn-group">
							<button class="btn btn-success" data-bind="click: $parent.addAttendee.bind($data, $root.user, '2')">Free</button>
							<button class="btn btn-primary" data-bind="click: $parent.addAttendee.bind($data, $root.user, '0')">Busy</button>
							<button class="btn btn-primary" data-bind="click: $parent.addAttendee.bind($data, $root.user, '1')">Maybe</button>
						</div>
						<div data-bind="visible: isBusy($root.user)" class="btn-group">
							<button class="btn btn-primary" data-bind="click: $parent.addAttendee.bind($data, $root.user, '2')">Free</button>
							<button class="btn btn-danger" data-bind="click: $parent.addAttendee.bind($data, $root.user, '0')">Busy</button>
							<button class="btn btn-primary" data-bind="click: $parent.addAttendee.bind($data, $root.user, '1')">Maybe</button>
						</div>
						<div data-bind="visible: isMaybe($root.user)" class="btn-group">
							<button class="btn btn-primary" data-bind="click: $parent.addAttendee.bind($data, $root.user, '2')">Free</button>
							<button class="btn btn-primary" data-bind="click: $parent.addAttendee.bind($data, $root.user, '0')">Busy</button>
							<button class="btn btn-warning" data-bind="click: $parent.addAttendee.bind($data, $root.user, '1')">Maybe</button>
						</div>
					</div>
					<button onclick="whosgoing(this)" data-ids="dave" style="width:100%" class="btn btn-success">View Invitees</button>
					<table class="table table-striped table-bordered whosgoing" style="display:none">
						<tbody data-bind="foreach: attendees">
							<tr>
								<td width="50%" data-bind="text: fullname"></td>
								<td class="statusFree" width="50%" data-bind="visible: availability()==2"><span class="label label-success">Free</span></td>
								<td class="statusMaybe" width="50%" data-bind="visible: availability()==1"><span class="label label-warning">Maybe</span></td>
								<td width="50%" data-bind="visible: availability()==-1"><span class="label label-info">Waiting</span></td>
								<td class="statusBusy" width="50%" data-bind="visible: availability()==0"><span class="label label-important">Busy</span></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
	
	<%@ include file="include/error.jsp" %>
<button id="confirmAttendance" class="btn btn-primary" data-bind="click: saveAttendance">Confirm</button>
<p><p>Once you have indicated your availability for the proposed dates please make sure you
	click the [Confirm] button above.
</div>

<script type='text/javascript' src='resources/js/viewmodel.js'></script>
<script type='text/javascript' src='resources/js/attending.js'></script>

<%@include file="include/footer.jsp"%>