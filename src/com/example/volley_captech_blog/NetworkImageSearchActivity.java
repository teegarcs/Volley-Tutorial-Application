package com.example.volley_captech_blog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.volley_captech_blog.common.ImageSearchResultAdapter;
import com.example.volley_captech_blog.common.SearchClass;
import com.example.volley_captech_blog.common.SearchClass.SearchResults;
import com.example.volley_captech_blog.common.VolleyCaptechApplication;
import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
/**
 * CapTech Consulting Blog
 * 
 * Network Image View via JSONObject Google Search.  
 * In this activity we will accept a search criteria from the user
 * and make a google search with that criteria that will return as a
 * JSONObject.  The object will be parsed by GSON and set to the search class.
 * 
 * In the adapter we will pull the url for the image and set it to the NetworkImageView
 * via the loader from the Application.  The loader is instantiated at the beginning of 
 * the application and utilizes the imageLruCache.  This is easily viewable in a list 
 * view where the view is destroyed and then the image is recalled as the user scrolls back to that row.  Since it 
 * is stored in the cache, the user will not have to wait for the image to restore itself. 
 * 
 * This of course is dependent on the cache size and the size of the images being brought in. 
 * 
 *  from a response. 
 * 
 * @author Clinton Teegarden
 *
 */
public class NetworkImageSearchActivity extends Activity {
	private String url ="http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
	private EditText searchCriteria;
	private Button searchButton;
	private ListView resultList;
	private ProgressBar progressBar;
	private RequestQueue queue;
	private Gson gson;
	private JsonObjectRequest searchRequest, extendedRequest;
	private ImageSearchResultAdapter adapter;
	private String searchText;
	private List<SearchResults> imageResults;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.networkimage_search_activity_view);
		searchCriteria=(EditText)findViewById(R.id.searchText);
		searchButton=(Button)findViewById(R.id.searchButton);
		progressBar=(ProgressBar)findViewById(R.id.progressBar);
		resultList=(ListView)findViewById(R.id.resultsList);
		queue = ((VolleyCaptechApplication)this.getApplicationContext()).getQueue();
		searchButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				searchButton.setEnabled(false);
				progressBar.setVisibility(View.VISIBLE);
				resultList.setVisibility(View.GONE);
				//add the user entered search criteria to the url.  Remove spaces and add +
				searchText = searchCriteria.getText().toString().replace(" ", "+");
				//add rsz=8 to return 8 search results
				String searchURL = url + searchText+"&rsz=8";
				Log.i("URL: ", searchURL);
				searchRequest = new JsonObjectRequest(Request.Method.GET, searchURL, null, new ResponseListener(), new ErrorListener());
				queue.add(searchRequest);
			}
		});
		//make sure we are searching for something
		searchCriteria.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if(TextUtils.isEmpty(s)){
					searchButton.setEnabled(false);
				}else{
					searchButton.setEnabled(true);
				}
			}
		});	
	}
	private class ResponseListener implements Response.Listener<JSONObject>{
		@Override
		public void onResponse(JSONObject response) {
			searchButton.setEnabled(true);
			setUpResults(response);
			
		}
	}
	private class ExtendedResponseListener implements Response.Listener<JSONObject>{
		@Override
		public void onResponse(JSONObject response) {
			addExtendedResults(response);
		}
	}
	private class ErrorListener implements Response.ErrorListener{
		@Override
		public void onErrorResponse(VolleyError error) {
			searchButton.setEnabled(true);
			progressBar.setVisibility(View.GONE);
			Toast.makeText(getApplicationContext(), 
                    "Error, Please Try Again", Toast.LENGTH_LONG).show();
		}
	}
	private void setUpResults(JSONObject response){
		imageResults = new ArrayList<SearchResults>();
		gson = new Gson();
		SearchClass searchClass = gson.fromJson(response.toString(), SearchClass.class);
		//make sure there is data in the response
		if(searchClass.getResponse()!=null){
		SearchResults[] results = searchClass.getResponse().getResults();
		List<SearchResults> tempList = Arrays.asList(results);
		imageResults.addAll(tempList);
		adapter = new ImageSearchResultAdapter(this, imageResults);
		resultList.setAdapter(adapter);
		resultList.setVisibility(View.VISIBLE);
		//get the next 8 results in the list
		String searchURL = url + searchText+"&start=8&rsz=8";
		extendedRequest = new JsonObjectRequest(Request.Method.GET, searchURL, null, new ExtendedResponseListener(), new ErrorListener());
		queue.add(extendedRequest);  
		progressBar.setVisibility(View.GONE);
		}
		
	}
	private void addExtendedResults(JSONObject response){
		SearchClass searchClass = gson.fromJson(response.toString(), SearchClass.class);
		if(searchClass.getResponse()!=null){
		SearchResults[] results = searchClass.getResponse().getResults();
		List<SearchResults> tempList = Arrays.asList(results);
		imageResults.addAll(tempList);
		resultList.setAdapter(adapter);
		}
	}


}
