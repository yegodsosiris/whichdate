<button class="btn btn-primary addDateButton" data-bind="click: $root.setDateMode">Add a date</button>
<p>
<table class="table table-striped table-hover table-bordered">
	<thead>
		<tr>
			<th class="thhead" width="50%">From</th>
			<th class="thhead" width="50%" colspan="2">To</th>
		</tr>
	</thead>
	<tbody id="myEventsTable" data-bind="foreach: proposedDates">
		<tr data-bind="visible: !deleteMode()">
			<td data-bind="text: fromDateAsString"></td>
			<td data-bind="text: toDateAsString"></td>
			<td width="1px">
				<button class="btn btn-small" data-bind="click: setDeleteMode"><i class="icon-trash"></i></button>
			</td>
		</tr>
		<tr data-bind="visible: deleteMode()">
			<td colspan="3">Remove this date? 
				<button class="btn btn-danger btn-small" data-bind="click: $parent.deleteDate">Remove</button>
				<button class="btn btn-warning btn-small" data-bind="click: unsetDeleteMode">Cancel</button>
			</td>
		</tr>
	</tbody>
</table>