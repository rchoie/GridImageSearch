package com.yahoo.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	public static final int PAGE_SIZE = 8;
	public static final int REQUEST_CODE = 123;

	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	Button btnLoadMore;
	
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	SearchSettings searchSettings = new SearchSettings();
	int start = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);				
			}
			
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    	case R.id.action_settings:
	    		startActivityForResult(new Intent(this, SearchSettingsActivity.class), SearchActivity.REQUEST_CODE);
	    		break;

	    	default:
	    		break;
	    }

	    return true;
	}
	  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SearchActivity.REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				searchSettings = (SearchSettings) data.getSerializableExtra("searchSettings");
		     }
		}
	}
	
	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnLoadMore = (Button) findViewById(R.id.btnLoadMore);
	}
	
	public String setupImageSearchQuery(String query, int start, SearchSettings searchSettings) {
	
		String imageSearchQuery = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";
		
		imageSearchQuery += "&rsz=" + SearchActivity.PAGE_SIZE;
		imageSearchQuery += "&start=" + start;
		imageSearchQuery += "&q=" + Uri.encode(query);
		
		if (searchSettings.getColorFilter() != null) {
			imageSearchQuery += "&imgcolor=" + Uri.encode(searchSettings.getColorFilter());
		}
		if (searchSettings.getImageSize() != null) {
			imageSearchQuery += "&imgsz=" + Uri.encode(searchSettings.getImageSize());
		}
		if (searchSettings.getImageType() != null) {
			imageSearchQuery += "&imgtype=" + Uri.encode(searchSettings.getImageType());
		}
		if (searchSettings.getSiteFilter() != null) {
			imageSearchQuery += "&as_sitesearch=" + Uri.encode(searchSettings.getSiteFilter());
		}
 		
		return imageSearchQuery;
	}
	
	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();

		start = 0;
		String imageSearchQuery = setupImageSearchQuery(query, start, searchSettings);
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(imageSearchQuery,
				   new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					if (imageJsonResults.length() > 0) {
						btnLoadMore.setVisibility(View.VISIBLE);
					}
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	
	}
	
	public void onLoadMore(View v) {
		
		start += SearchActivity.PAGE_SIZE;
		
		String query = etQuery.getText().toString();

		String imageSearchQuery = setupImageSearchQuery(query, start, searchSettings);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(imageSearchQuery,
				   new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
}
