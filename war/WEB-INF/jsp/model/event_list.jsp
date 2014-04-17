<h4>Open</h4>
<table class="table table-striped table-hover table-bordered">
	<thead>
		<tr>
			<th class="thhead">Event Title</th>
			<th class="thhead" colspan="2">Location</th>
		</tr>
	</thead>
	<tbody id="myEventsTable" data-bind="foreach: events">
		<tr data-bind="visible:!calendarEventCreated()">
			<td data-bind="text: title"></td>
			<td data-bind="text: location()"></td>
			<td width="1">
				<button data-bind="click: $parent.rowAction" class="btn btn-primary btn-small">Open</button>
			</td>
		</tr>
	</tbody>
</table>
<h4>Added to Google Calendar</h4>
<table class="table table-striped table-hover table-bordered">
	<thead>
		<tr>
			<th class="thhead">Event Title</th>
			<th class="thhead" colspan="2">Location</th>
		</tr>
	</thead>
	<tbody id="myEventsTable" data-bind="foreach: events">
		<tr data-bind="visible:calendarEventCreated()">
			<td data-bind="text: title"></td>
			<td data-bind="text: location()"></td>
			<td width="1">
				<button data-bind="click: $parent.rowAction" class="btn btn-primary btn-small">Open</button>
			</td>
		</tr>
	</tbody>
</table>