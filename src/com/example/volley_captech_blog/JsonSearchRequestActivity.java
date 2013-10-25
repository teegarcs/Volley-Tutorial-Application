package com.example.volley_captech_blog;

import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.volley_captech_blog.common.JsonSearchResultAdapter;
import com.example.volley_captech_blog.common.SearchClass;
import com.example.volley_captech_blog.common.SearchClass.SearchResults;
import com.google.gson.Gson;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * CapTech Consulting Blog
 * 
 * JSONObject Google Search.  
 * In this activity we will accept a search criteria from the user
 * and make a google search with that criteria that will return as a
 * JSONObject.  The object will be parsed by GSON and set to a class. 
 * 
 * This example will also show you how to retrieve and set cookies
 * 
 * @author Clinton Teegarden
 *
 */
public class JsonSearchRequestActivity extends Activity {
	private String url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
	private EditText searchCriteria;
	private TextView cookieResult;
	private Button searchButton;
	private RelativeLayout cookieArea;
	private ListView resultList;
	private ProgressBar progressBar;
	private RequestQueue queue;
	private Gson gson;
	private JsonObjectRequest searchRequest;
	private JsonSearchResultAdapter adapter;
	private AbstractHttpClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.json_search_activity_view);
		cookieArea=(RelativeLayout)findViewById(R.id.cookieArea);
		searchCriteria=(EditText)findViewById(R.id.searchText);
		cookieResult=(TextView)findViewById(R.id.cookies);
		searchButton=(Button)findViewById(R.id.searchButton);
		progressBar=(ProgressBar)findViewById(R.id.progressBar);
		resultList=(ListView)findViewById(R.id.resultsList);

		//in order to get/set cookies we need to have a http client
		//could be made into singleton, but for example purposes left here. 
		client = new DefaultHttpClient();
		queue = Volley.newRequestQueue(this, new HttpClientStack(client));
		
		searchButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				searchButton.setEnabled(false);
				cookieArea.setVisibility(View.INVISIBLE);
				resultList.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
				//get the clients cookie store 
				CookieStore store = client.getCookieStore();
				//add our own cookie for demo purposes
				Cookie cookie = new BasicClientCookie("Example_Cookie", "80");
				store.addCookie(cookie);
				//add the user entered search criteria to the url.  Remove spaces and add %20
				String searchText = searchCriteria.getText().toString().replace(" ", "%20");
				//add rsz=8 to return 8 search results
				String searchURL =url + searchText+"&rsz=8";
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
			CookieStore store = client.getCookieStore();
			List<Cookie> cookieList = store.getCookies();
			cookieResult.setText(cookieList.get(0).getName());
			cookieArea.setVisibility(View.VISIBLE);
			setUpResults(response);
			//clear the store so that we don't keep adding cookies on each search
			store.clear();
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
	/**
	 * Helper method that will help us set up the results list
	 * and set it to the adapter. 
	 * @param JSONObject 
	 */
	private void setUpResults(JSONObject response) {
		gson = new Gson();
		SearchClass searchClass = gson.fromJson(response.toString(),
				SearchClass.class);
		if (searchClass.getResponse() != null) {
			final SearchResults[] results = searchClass.getResponse().getResults();
			progressBar.setVisibility(View.GONE);
			adapter = new JsonSearchResultAdapter(this, results);
			resultList.setAdapter(adapter);
			resultList.setVisibility(View.VISIBLE);
			resultList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					SearchResults result = results[position];
					Uri uri = Uri.parse(result.getURL());
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				}
			});
		}
	}
	
	
}
