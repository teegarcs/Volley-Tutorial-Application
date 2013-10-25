package com.example.volley_captech_blog.common;

import com.google.gson.annotations.SerializedName;

/**
 * CapTech Consulting Blog
 * 
 *Class that de-serializes all google search responses
 * 
 * @author Clinton Teegarden
 *
 */

public class SearchClass {
	@SerializedName("responseData")
	private ResponseData response;
	
	public ResponseData getResponse(){
		return response;
	}

	public class ResponseData {
		@SerializedName("results")
		private SearchResults[] results;

		public SearchResults[] getResults() {
			return results;
		}
	}

	public class SearchResults {
		@SerializedName("titleNoFormatting")
		private String Title;
		
		@SerializedName("content")
		private String Content;
		
		@SerializedName("url")
		private String URL;
		
		public String getTitle(){
			return Title;
		}
		public String getContent(){
			return Content;
		}
		public String getURL(){
			return URL;
		}
	}
}
