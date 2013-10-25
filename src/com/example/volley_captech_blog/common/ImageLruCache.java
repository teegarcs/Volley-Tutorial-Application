package com.example.volley_captech_blog.common;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;
/**
 * CapTech Consulting Blog
 * 
 * ImageLruCache used to return previously loaded images from the cache. 
 * 
 * 
 * @author Clinton Teegarden
 *
 */
public class ImageLruCache extends LruCache<String, Bitmap> implements ImageCache {
	/*
	 * Used to calculate the cache size we would like to have
	 * Makes sure we do not get an OOM exception. 
	 */
	public static int getDefaultLruCacheSize() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;
		return cacheSize;
	}
	/**
	 * Constructor methods below.  First one is called
	 * and gets the cache size from the custom method and 
	 * builds a cache of that size. 
	 */
	public ImageLruCache() {
		this(getDefaultLruCacheSize());
	}
	public ImageLruCache(int size){
		super(size);
	}
	/**
	 * Used to return the size of an image
	 */
	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight() / 1024;
	}
	/**
	 * returns the bitmap from the cache by passing the url
	 */
	@Override
	public Bitmap getBitmap(String url) {
		return get(url);
	}
	/**
	 * sets the image to the cache using the url as a primary key.
	 */
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url, bitmap);
	}

}
