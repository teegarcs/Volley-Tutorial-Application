package com.example.volley_captech_blog.common;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.volley_captech_blog.R;

import com.example.volley_captech_blog.common.SearchClass.SearchResults;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * CapTech Consulting Blog
 * 
 *Adapter being utilized by the ImageSearchRequestActivity. 
 *
 *Here is where the loader is called from the VolleyCaptechApplication
 *that utilizes the Lru cache.  The network image view just has the image url set to it and 
 *volley does the rest of the magic. 
 * 
 * @author Clinton Teegarden
 *
 */
public class ImageSearchResultAdapter extends BaseAdapter {
	private Context context;
	private List<SearchResults> results;
	private ImageLoader loader;
	public ImageSearchResultAdapter(Context context, List<SearchResults> results) {
		this.context = context;
		this.results = results;
		loader = ((VolleyCaptechApplication)context.getApplicationContext()).getImageLoader();
	}

	@Override
	public int getCount() {
		return results.size();
	}

	@Override
	public Object getItem(int position) {
		return results.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null){
			view = LayoutInflater.from(context).inflate(R.layout.image_search_result_element, null, false);
			ViewHolder holder = new ViewHolder();
			holder.title = (TextView)view.findViewById(R.id.resultTitle);
			holder.content=(TextView)view.findViewById(R.id.resultContent);
			holder.resultImage=(NetworkImageView)view.findViewById(R.id.resultImage);
			view.setTag(holder);
		}
		SearchResults selectedResult = (SearchResults)getItem(position);
		ViewHolder holder = (ViewHolder)view.getTag();
		holder.title.setText(selectedResult.getTitle());
		holder.content.setText(Html.fromHtml(selectedResult.getContent()));
		holder.resultImage.setImageUrl(selectedResult.getURL(), loader);
		
		return view;
	}
	
	static class ViewHolder{
		TextView title;
		TextView content;
		NetworkImageView resultImage;
	}

}
