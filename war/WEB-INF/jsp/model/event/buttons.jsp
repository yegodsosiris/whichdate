<div class="btn-group" data-bind="visible: isNew()">
	<button class="btn btn-primary" data-bind="click: $parent.cancelAction"><i class=icon-home></i></button>
	<button data-bind="click: $parent.saveAction" class="btn btn-primary">Save</button>
</div>	
<div class="btn-group" data-bind="visible: !isNew()">
  <button data-bind="click: $parent.closeAction" class="btn btn-primary"><i class=icon-home></i></button>
  <button data-bind="click: $parent.updateAction" class="btn btn-primary">Save event</button>
  <button data-bind="visible: sendUpdatedInvites() || sendInvites(), click: $parent.inviteButtonAction" class="btn btn-primary">Notifications</button>
  <button data-bind="click:setDeleteMode" class="btn btn-primary"><i class=icon-trash></i></button>
  <button id="refreshSiteButton" onclick="reloadPage()" class="btn btn-primary"><i class=icon-refresh></i></button>
</div>