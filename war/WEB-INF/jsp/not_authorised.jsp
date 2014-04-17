<%@include file="include/head.jsp"%>

<div class="navbar">
  <div class="navbar-inner">
    <a class="brand" href="#">Which Date</a>
  </div>
</div>
<div class="container-fluid">
	<h2>Welcome to Which Date</h2>
	This application has been written to work on both desktop and mobile browsers. It enables you to create a proposed calendar event
	when you don't know what date is best suited for your guests.
	<p><p>
	Once signed in you can:
	<ol>
		<li>Create an event</li>
		<li>Assign multiple proposed dates to that event</li>
		<li>Add the people you want to attend.</li>
	</ol>
	Once completed you send out invites to those people (invitees) and they respond by letting you know what dates they can attend. 
	<p><p>
	The application will then work out the best matched date. You can specify if a person is:
	<ul>
		<li>Required - HAS to attend - event cannot go ahead without them</li>
		<li>Desired - event can proceed if they cannot attend but is marked down because of that</li>
		<li>Optional - if they cannot attend then this is no biggy.</li>
	</ul>
	
	(The invitees do not see this information)
	<p><p>
	When you have a best matched date you simply click a button to automatically create a calendar event which then sends an official
	Google Calendar event to all parties.
	<h3>What you need</h3>
	It's free. All you need is a google account and you're away - simply sign in and all your contacts will be made available for you to choose from.
	
	<h3>Access required</h3>
	We need to have your authorisation to read from your contacts in order to help create events from your contact list
	
	
	<div id="authdetails">
		<h4>What access is required</h4>
		<ul>
			<li><b>Manage your contacts</b><br>We will read your contacts. The required permission reads 'Manage Contacts' as Google does not supply a read-only permission for contacts.</li>
			<li><b>View basic information about your account</b><br>We need your basic details such as your name from Google.</li>
			<li><b>View your email address</b><br>We need your email address for sending emails.</li>
			<li><b>Perform these operations when I'm not using the application</b><br>Off-line access is required so you are not asked to authorise this access again every time you come back to this application</li>
		</ul>
	
	<p><p><button class="btn btn-primary" id="authorise">Sign in and Authorise Google</button>
	</div>

</div>

<%@include file="include/footer.jsp"%>