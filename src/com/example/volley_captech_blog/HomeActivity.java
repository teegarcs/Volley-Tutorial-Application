package com.example.volley_captech_blog;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * CapTech Consulting Blog
 * 
 * Home Activity where all demos will begin. 
 * 
 * @author Clinton Teegarden
 *
 */
public class HomeActivity extends Activity {
	private Button simpleRequest, imageRequest, jsonSearchRequest, networkImageSearch;
	private Intent intent;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity_view);
		context = this;
		simpleRequest = (Button)findViewById(R.id.simpleRequest);
		imageRequest = (Button)findViewById(R.id.imageRequest);
		jsonSearchRequest = (Button)findViewById(R.id.jsonSearch);
		networkImageSearch=(Button)findViewById(R.id.imageSearch);
		
		simpleRequest.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				intent = new Intent(context, SimpleRequestActivity.class);
				startActivity(intent);
			}
		});
		
		imageRequest.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				intent = new Intent(context, ImageRequestActivity.class);
				startActivity(intent);
			}
		});
		
		jsonSearchRequest.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				intent = new Intent(context, JsonSearchRequestActivity.class);
				startActivity(intent);
			}
		});
		
		networkImageSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				intent = new Intent(context, NetworkImageSearchActivity.class);
				startActivity(intent);
			}
		});
	}

}
