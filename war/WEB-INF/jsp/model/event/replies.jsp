<div data-bind="foreach: proposedDates">
	<table  class="table table-striped table-hover table-bordered"">
		<thead>
			<tr>
				<th colspan="2" data-bind="text: fromDateAsString()">
			<tr>
		</thead>
		<tbody data-bind="foreach: attendees">
			<tr>
				<td width="50%" data-bind="text: fullname"></td>
				<td class="statusFree" width="50%" data-bind="visible: availability()==2"><i class="icon-thumbs-up"></i></td>
				<td class="statusMaybe" width="50%" data-bind="visible: availability()==1"><b>?</b></td>
				<td class="statusBusy" width="50%" data-bind="visible: availability()==0"><i class="icon-thumbs-down"></i></td>
			</tr>
		</tbody>
	</table>
</div>