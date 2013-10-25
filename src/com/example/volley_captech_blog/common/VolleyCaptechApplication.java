package com.example.volley_captech_blog.common;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.app.Application;
/**
 * CapTech Consulting Blog
 * 
 * Runs at the start of the application and is responsible for initializing
 * the queues, ImageLoader, and image cache.  
 * 
 * @author Clinton Teegarden
 *
 */
public class VolleyCaptechApplication extends Application{
	private ImageLruCache imageCache;
	private RequestQueue queue;
	private ImageLoader imageLoader;
	public void onCreate() {
		imageCache = new ImageLruCache(ImageLruCache.getDefaultLruCacheSize());
		queue =Volley.newRequestQueue(this);
		imageLoader = new ImageLoader(queue, imageCache);
	}
	/**
	 * Used to return the singleton Image cache
	 * We do this so that if the same image is loaded 
	 * twice on two different activities, the cache still remains
	 * @return ImageLruCach
	 */
	public ImageLruCache getCache() {
		return imageCache;
	}
	/**
	 * Used to return the singleton RequestQueue
	 * @return RequestQueue
	 */
	public RequestQueue getQueue() {
		return queue;
	}
	/**
	 * Used to return the singleton imageloader 
	 * that utilizes the image lru cache. 
	 * @return ImageLoader
	 */
	public ImageLoader getImageLoader(){
		return imageLoader;
	}

}
