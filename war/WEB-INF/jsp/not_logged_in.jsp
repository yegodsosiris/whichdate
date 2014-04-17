<%@include file="include/head.jsp"%>
<div class="navbar">
  <div class="navbar-inner">
    <a class="brand" href="#">MyEvents</a>
  </div>
</div>
<div class="container-fluid">
		<h3>Welcome to JTE Organiser</h3>
		This app is in development mode. It's functionality and any saved data is subject to change without notification.
		<p>Before you continue you need to login using your Google account
		<p><p><button class="btn btn-primary" id="loginButton">login</button>
	
</div>
<script>
$(function() {

	$("#loginButton").click(function() {
		location.href=("/login");
	});
});
</script>
<%@include file="include/footer.jsp"%>