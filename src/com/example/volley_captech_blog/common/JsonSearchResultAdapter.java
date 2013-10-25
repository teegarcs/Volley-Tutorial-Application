package com.example.volley_captech_blog.common;

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
 *Adapter being utilized by the JsonSearchRequestActivity. 
 * 
 * @author Clinton Teegarden
 *
 */
public class JsonSearchResultAdapter extends BaseAdapter {
	private Context context; 
	private SearchResults[] results;
	public JsonSearchResultAdapter(Context context, SearchResults[] results) {
		this.context=context;
		this.results = results;
	}

	@Override
	public int getCount() {
		return results.length;
	}

	@Override
	public Object getItem(int position) {
		return results[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view==null){
			view=LayoutInflater.from(context).inflate(R.layout.json_search_result_element, null, false);
			ViewHolder holder = new ViewHolder();
			holder.title = (TextView)view.findViewById(R.id.resultTitle);
			holder.content=(TextView)view.findViewById(R.id.resultContent);
			view.setTag(holder);
		}
		SearchResults selectedResult = (SearchResults)getItem(position);
		ViewHolder holder = (ViewHolder)view.getTag();
		holder.title.setText(selectedResult.getTitle());
		holder.content.setText(Html.fromHtml(selectedResult.getContent()));
		return view;
	}
	
	static class ViewHolder{
		TextView title;
		TextView content;
	}

}
