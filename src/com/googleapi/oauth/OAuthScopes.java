package com.googleapi.oauth;

public enum OAuthScopes {
	
	Adsense_Management("https://www.googleapis.com/auth/adsense"),
	Google_Affiliate_Network("https://www.googleapis.com/auth/gan"),
	Analytics("https://www.googleapis.com/auth/analytics.readonly"),
	Google_Books("https://www.googleapis.com/auth/books"),
	Blogger("https://www.googleapis.com/auth/blogger"),
	Calendar("https://www.googleapis.com/auth/calendar"),
	Google_Cloud_Storage("https://www.googleapis.com/auth/devstorage.read_write"),
	Contacts("https://www.google.com/m8/feeds/"),
	Content_API_for_Shopping("https://www.googleapis.com/auth/structuredcontent"),
	Chrome_Web_Store("https://www.googleapis.com/auth/chromewebstore.readonly"),
	Documents_List("https://docs.google.com/feeds/"),
	Google_Drive("https://www.googleapis.com/auth/drive"),
	Google_Drive_Files("https://www.googleapis.com/auth/drive.file"),
	Gmail("https://mail.google.com/mail/feed/atomv"),
	Google_Plus("https://www.googleapis.com/auth/plus.me"),
	Groups_Provisioning("https://apps-apis.google.com/a/feeds/groups/"),
	Google_Latitude("https://www.googleapis.com/auth/latitude.all.best https://www.googleapis.com/auth/latitude.all.city"),
	Moderator("https://www.googleapis.com/auth/moderator"),
	Nicknames_Provisioning("https://apps-apis.google.com/a/feeds/alias/"),
	Orkut("https://www.googleapis.com/auth/orkut"),
	Picasa_Web("https://picasaweb.google.com/data/"),
	Sites("https://sites.google.com/feeds/"),
	Spreadsheets("https://spreadsheets.google.com/feeds/"),
	Tasks("https://www.googleapis.com/auth/tasks"),
	URL_Shortener("https://www.googleapis.com/auth/urlshortener"),
	Userinfo_Email("https://www.googleapis.com/auth/userinfo.email"),
	Userinfo_Profile("https://www.googleapis.com/auth/userinfo.profile"),
	User_Provisioning("https://apps-apis.google.com/a/feeds/user/"),
	Webmaster_Tools("https://www.google.com/webmasters/tools/feeds/"),
	YouTube("https://gdata.youtube.com");
	
	private String scope;
	private OAuthScopes(String scope) {
		this.scope=scope;
	}
	
	public String getScope() {
		return scope;
	}

	
}
