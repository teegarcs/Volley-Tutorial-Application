package com.example.volley_captech_blog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Priority;
import com.android.volley.toolbox.StringRequest;
import com.example.volley_captech_blog.common.CustomStringRequest;
import com.example.volley_captech_blog.common.VolleyCaptechApplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
/**
 * CapTech Consulting Blog
 * 
 * String Request. 
 * 
 * The first button will first check the 
 * queue cache and see if it has results for the url being requested. 
 * If it does, it will return the string from the.  If it does not, it will make a
 * network call. 
 * 
 * The second button will be used to clear the cache for this url so that 
 * a fresh request will be made the next time the first button is selected. 
 * 
 * 
 * @author Clinton Teegarden
 *
 */
public class SimpleRequestActivity extends Activity {
	private String url = "https://dl.dropboxusercontent.com/u/57707756/CapTech_Volley_Blog/stringResponse.json";
	private Button makeRequest, clearCache;
	private TextView results;
	private ProgressBar progressBar;
	private RequestQueue queue;
	private CustomStringRequest stringRequest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_request_activity_view);
		makeRequest = (Button)findViewById(R.id.makeRequest);
		clearCache = (Button)findViewById(R.id.clearCache);
		results = (TextView)findViewById(R.id.requestResult);
		progressBar=(ProgressBar)findViewById(R.id.progressBar);
		queue = ((VolleyCaptechApplication) this.getApplicationContext()).getQueue();
		stringRequest = new CustomStringRequest(url, new ResponseListener(), new ErrorListener());
		//show how to set priority utilizing custom method/class.
		stringRequest.setPriority(Priority.IMMEDIATE);
		//make request button listener 
		makeRequest.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				results.setText("");
				//check the cache and see if it has a response
				if(queue.getCache().get(url)!=null){
					//response exists
					String cachedResponse = new String(queue.getCache().get(url).data);
					results.setText("From Cache: " + cachedResponse);
				}else{
					//no response
					progressBar.setVisibility(View.VISIBLE);
					queue.add(stringRequest);
				}	
			}
		});
		//clear cache button listener
		clearCache.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//clear the cache for this url if it exists. 
				if(queue.getCache().get(url)!=null){
					queue.getCache().remove(url);
				}
			}
		});
	}
	
	private class ResponseListener implements Response.Listener<String>{
		@Override
		public void onResponse(String response) {
			progressBar.setVisibility(View.GONE);
			results.setText("From Network: "+ response);
		}
	}
	
	private class ErrorListener implements Response.ErrorListener{
		@Override
		public void onErrorResponse(VolleyError error) {
			progressBar.setVisibility(View.GONE);
			results.setText("Error, Please Try Again.");
		}
		
	}


}
