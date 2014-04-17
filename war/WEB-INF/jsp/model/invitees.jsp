<div class="btn-group">
	<button class="btn btn-primary addDateButton" data-bind="click: $root.setInviteeMode">Search</button>
	<button class="btn btn-primary" data-bind="click: setInviteeMultiMode.bind($data, true)">List</button>
	<button data-bind="click: $root.inlineEditAction, visible: !$root.inlineedit()" class="btn btn-primary">Edit</button>
	<button data-bind="click: $root.closeInlineEditAction, visible: $root.inlineedit()" class="btn btn-primary"><i class="icon-ok"></i></button>
</div>
<p>
<table class="table table-striped table-hover table-bordered">
	<thead>
		<tr>
			<th class="thhead">Full Name</th>
			<th class="thhead">Email</th>
			<th class="thhead" colspan="2">Importance</th>
			<th class="thhead" colspan="2">&nbsp;</th>
		</tr>
	</thead>
	<tbody data-bind="foreach: invitees">
		<tr data-bind="visible: !deleteMode()">
			<td data-bind="text: fullname()"></td>
			<td data-bind="visible: !$root.inlineedit(), text: email()"></td>
			<td data-bind="visible: !$root.inlineedit() && importance()==5">Required</td>
			<td data-bind="visible: !$root.inlineedit() && importance()==3">Desired</td>
			<td data-bind="visible: !$root.inlineedit() && importance()==1">Optional</td>
			<td data-bind="visible: $root.inlineedit()">
				<select id="importance" data-bind="value:importance">
					<option value="5">Required</option>
					<option value="3">Desired</option>
					<option value="1">Optional</option>
				</select>
			</td>
			<td width="1px">
				<a class="btn btn-small" data-bind="visible: !$root.inlineedit(), click: setDeleteMode"><i class="icon-trash"></i></a>
			</td>
			<td data-bind="visible: !$root.inlineedit()">
				<a class="btn btn-small" data-bind="attr: {href: 'attend?i='+$parent.parentId()+'&e='+encEmail}"><i class="icon-play"></i></a>
			</td>
		</tr>
		<tr data-bind="visible: deleteMode()">
			<td colspan="3">Remove this invitee? 
				<button class="btn btn-danger btn-small" data-bind="click: $parent.deleteInvitee">Remove</button>
				<button class="btn btn-warning btn-small" data-bind="click: unsetDeleteMode">Cancel</button>
			</td>
		</tr>
	</tbody>
</table>
