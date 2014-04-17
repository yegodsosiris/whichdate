	<div id="emailContainer" data-bind="with: emailTemplate, visible: emailMode()">
		<input data-bind="value: subject" placeholder="Email Subject"><p>
		<textarea rows="15" cols="200" data-bind="value: body" id="emailbody"></textarea>
	</div>
	
	