<div data-bind="visible:sendingMode()">


	<button data-bind="click: $parent.cancelSendMode" class="btn btn-primary">Cancel</button>

	<div class="event-header">
	  <div class="title">Notifications</div>
	</div>
	
	<div data-bind="visible: sendInvites()">
		<h4>Send Invites</h4>
		Send out Invites?<p>
		<div class="btn-group">
			<button data-bind="click: $parent.sendInvitesAction" class="btn btn-primary">Send Invites</button>
		</div>
	</div>
	
	<div data-bind="visible: sendUpdatedInvites()">
		<h4>Send Notifications</h4>
		You have previously sent invites out. You may wish to send a quick update message to inform 
		your invitees that the event details have changed such as adding or removing proposed dates.
		<div data-bind="visible: sendUpdatedInvites()">
		
		 <h5>Type your message here.</h5>
		 If you leave this empty then only the original invitation will be sent<p>
			<textarea data-bind="value: emailTemplate.body"></textarea><p>
		</div>
		Choose the invitees you wish to receive the notification:
		<table class="table table-striped table-hover table-bordered">
			<thead>
				<tr>
					<th class="thhead"></th>
					<th class="thhead">Full Name</th>
					<th class="thhead">Email</th>
				</tr>
			</thead>
			<tbody data-bind="foreach: invitees">
				<tr style="cursor:pointer;cursor:hand" data-bind="click: $parent.addNotifier">
					<td width="1" data-bind="visible: onNotificationList()" class="statusFree"><i class="icon-thumbs-up"></i></td>
					<td width="1" data-bind="visible: !onNotificationList()" class="statusBusy"><i class="icon-thumbs-down"></i></td>
					<td data-bind="text: fullname"></td>
					<td data-bind="text: email"></td>
				</tr>
			</tbody>
		</table>
		<div class="btn-group">
	  		<button data-bind="click: $parent.sendInviteUpdateAction" class="btn btn-primary">Send notifications</button>
  		</div>
		
		<h4>Send Reminders and new Invites</h4>
		Send reminder invites only to new Invitees and Invitees that have not yet responded.<p>
		<div class="btn-group">
	  		<button data-bind="visible: sendUpdatedInvites(), click: $parent.sendReminderAction" class="btn btn-primary">Send</button>
		</div>
		<p>
		<button data-bind="click: $parent.cancelSendMode" class="btn btn-primary">Cancel</button>
	</div>


</div>