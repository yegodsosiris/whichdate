	<div class="inviteeContainer" data-bind="visible: inviteeMode()">
		<div class="btn-group">
			<button class="btn btn-primary" data-bind="click: $root.unsetInviteeMode">Cancel</button>
			<button class="btn btn-primary" data-bind="click: $root.addProposedInvitee">Add this contact</button>
		</div>
		<div class="dateContainer" data-bind="with: proposedInvitee">
			<p>
			<div class="input-prepend">
				<span class="add-on">@</span>
				<input autocomplete="off" size="32" class="contact_email_full" type="text" placeholder="Type Email or Name"><button class="btn clearEmail"><i class="icon-remove"></i></button>
			</div>
			<input type="text" size="32" class="contact_email" type="text" data-bind="value: email" disabled="disabled">
			<input type="text" size="32" class="contact_fullname" type="text" data-bind="value: fullname" disabled="disabled">
			<div data-bind="visible: isNew">
				<label for="importance">Attendance:</label> 
				<select class="importance" data-bind="value:importance">
					<option value="5">Required</option>
					<option value="3">Desired</option>
					<option value="1">Optional</option>
				</select>
			</div>
		</div>
	</div>
	<div data-bind="visible: inviteeMultiMode()">
		<div class="btn-group">
			<button class="btn btn-primary" data-bind="click: setInviteeMultiMode.bind($data, false)">Done</button>
		</div>
		<table class="table table-striped table-hover table-bordered">
			<thead>
				<tr>
					<th class="thhead"></th>
					<th class="thhead">Full Name</th>
					<th class="thhead">Email</th>
				</tr>
			</thead>
			<tbody data-bind="foreach: contacts">
				<tr style="cursor:pointer;cursor:hand" data-bind="click: $parent.addContact">
					<td width="1" data-bind="visible: invited()" class="statusFree"><i class="icon-thumbs-up"></i></td>
					<td width="1" data-bind="visible: !invited()" class="statusBusy"><i class="icon-thumbs-down"></i></td>
					<td data-bind="text: fullname"></td>
					<td data-bind="text: email"></td>
				</tr>
			</tbody>
		</table>
		<div class="btn-group">
			<button class="btn btn-primary" data-bind="click: setInviteeMultiMode.bind($data, false)">Done</button>
		</div>
	</div>