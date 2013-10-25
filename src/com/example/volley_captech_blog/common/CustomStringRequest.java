package com.example.volley_captech_blog.common;


import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class CustomStringRequest extends StringRequest {
	private Priority lowPriority = Priority.LOW;
	public CustomStringRequest(String url, Listener<String> listener,
            ErrorListener errorListener) {
       super(url, listener, errorListener);
       
	}
	@Override
	public Priority getPriority() {
	    return lowPriority;
	}

	public void setPriority(Priority priority) {
	    lowPriority = priority;
	}

}
