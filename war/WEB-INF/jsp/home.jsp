<%@include file="include/head.jsp"%>
	
<script>

	var my_contacts = ${contacts};
	var contextpath='${contextpath}';
	
	$(function() {
		fmOn('<h4>Loading your events</h4>Please wait....');
	});

</script>

<body>

<div class="navbar">
  <div class="navbar-inner">
    <a onclick="reloadPage()" class="brand" href="#">Which Date</a>
    <button id="logout" class="btn btn-primary">Logout</button>
  </div>
</div>
 
<div id="maincontainer" style="display:none" class="container-fluid">
<!-- 
	<div data-bind="text: 'editMode ' + editMode()"></div>
	<div data-bind="text: 'newevent.editMode ' + newevent.editMode()"></div>
 -->
 
	<%@ include file="include/error.jsp" %>
	<div id="loadmessage" style="display:none" "class="alert alert-success">Info</div>

	
	<div data-bind="visible: !editMode()">
		<div class="btn-group">
			<button class="btn btn-primary" data-bind="click: createAction">New Event</button>
			<button onclick="reloadPage()" class="btn btn-primary"><i class=icon-refresh></i></button>
		</div>
		<p>
		<%@ include file="model/event_list.jsp" %>
	</div>
	
	<div  data-bind="foreach: events">
	 <%@ include file="model/event.jsp" %>
	</div>
	
	<div data-bind="with: newevent, visible: newevent.editMode()">
		<%@ include file="model/event.jsp" %>
	</div>
</div>

  	<script type='text/javascript' src='resources/js/viewmodel.js'></script>
  	<script type='text/javascript' src='resources/js/controller.js'></script>

  	
<%@include file="include/footer.jsp"%>