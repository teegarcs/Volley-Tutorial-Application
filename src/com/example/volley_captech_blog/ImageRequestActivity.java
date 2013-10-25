package com.example.volley_captech_blog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.volley_captech_blog.common.VolleyCaptechApplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
/**
 * CapTech Consulting Blog
 * 
 * ImageLoader example.  Here we will load an image 
 * from a server and set it to an image view. 
 * It will retrieve the image from the network the first time 
 * and then use the cache to return the image all other runs
 * 
 * @author Clinton Teegarden
 *
 */
public class ImageRequestActivity extends Activity {
	private String url = "https://dl.dropboxusercontent.com/u/57707756/CapTech_Volley_Blog/ctvlogo.png";
	private Button makeRequest;
	private ImageView imageView;
	private RequestQueue queue;
	private ImageLoader imageLoader;
	private ImageListener imageListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_request_activity_view);
		makeRequest = (Button)findViewById(R.id.makeRequest);
		imageView = (ImageView)findViewById(R.id.imageLoader);
		queue = ((VolleyCaptechApplication)this.getApplicationContext()).getQueue();
		imageLoader = ((VolleyCaptechApplication)this.getApplicationContext()).getImageLoader();
		imageListener = ImageLoader.getImageListener(imageView, R.drawable.no_image, R.drawable.error_image);
		makeRequest.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				imageLoader.get(url, imageListener);
			}
		});
	}
}
