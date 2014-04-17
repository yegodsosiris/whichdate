<div data-bind="with: eventReportDto" class="accordion" id="accordion2">
<div  class="alert alert-success" data-bind="visible:calendarEventCreated()">A Google Calendar Event has been created</div>
<div  class="alert alert-error" data-bind="visible:calendarEventCreatedFailed()">Failed to create. Please try again</div>
  <div class="accordion-group">
	<div data-bind="foreach: dateReportDtos">
		<div class="reportDivider accordion-heading">
			<table  class="table table-striped table-hover table-bordered">
				<thead>
					<tr>
						<th colspan="3" data-bind="text: dateRange()">
					<tr>
				</thead>
				<tbody>
					<tr>
						<td class="statusFree" width="50%" data-bind="visible: status=='Good', text: 'STATUS: ' + status"></td>
						<td class="statusMaybe" width="50%" data-bind="visible: status=='Pending' || status=='Possible', text: 'STATUS: ' + status"></td>
						<td class="statusBusy" width="50%" data-bind="visible: status=='Not Possible', text: 'STATUS: ' + status"></td>
						<td width="50%"><span data-bind="text: 'RATING: ' + score + '/' + max"></span><span data-bind="visible: bestMatch"> <i class="icon-star"></i></span></td>
						<td width="1">
        						<button data-bind="attr: {toggleid: 'reportdetails'+uid()}" class="toggleme btn btn-primary">More</button>
      					</td>
					</tr>
					<tr data-bind="visible: bestMatch && score > 0 && status!='Not Possible'">
						<td colspan="3" data-bind="visible: !creatingCalendarEvent()"><button data-bind="click:setCreatingCalendarEvent" class="btn btn-primary">Create Google Calendar Event</button></td>
						<td colspan="3" data-bind="visible: creatingCalendarEvent()">
							<div class="btn-group">
								<button data-bind="click:unsetCreatingCalendarEvent" class="btn btn-warning">Cancel</button>
								<button data-bind="click:$parent.createCalendarEvent" class="btn btn-success">Confirm</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			
      	<div data-bind="attr: {id: 'reportdetails'+uid()}" class="imclosed" style="display:none">
			<table  class="table table-striped table-hover table-bordered">
				<thead>
					<tr>
						<th>Who</th>
						<th>Attend</th>
						<th>Rqd</th>
						<th>Stat</th>
					<tr>
				</thead>
				<tbody data-bind="foreach: inviteeReportDtos">
					<tr>
						<td width="50%" data-bind="text: fullname"></td>
						<td class="statusFree" width="50%" data-bind="visible: availability==2"><span class="label label-success">Free</span></td>
						<td class="statusMaybe" width="50%" data-bind="visible: availability==1"><span class="label label-warning">Maybe</span></td>
						<td width="50%" data-bind="visible: availability==-1"><span class="label label-info">Waiting</span></td>
						<td class="statusBusy" width="50%" data-bind="visible: availability==0"><span class="label label-important">Busy</span></td>
						
						<td width="50%" data-bind="visible: importance ==5">Req</td>
						<td width="50%" data-bind="visible: importance ==3">Des</td>
						<td width="50%" data-bind="visible: importance ==1">Opt</td>
						
						<td class="statusFree" width="50%" data-bind="visible: result == 3"><span class="label label-success">Good</span></td>
						<td class="statusMaybe" width="50%" data-bind="visible: result == 2"><span class="label label-warning">Ok</span></td>
						<td class="statusBusy" width="50%" data-bind="visible: result == 1"><span class="label label-important">Bad</span></td>
						<td width="50%" data-bind="visible: result == 0"><b>?</b></td>
					</tr>
				</tbody>
			</table>
		 </div>
		</div>
	
	</div>
  </div>
</div>