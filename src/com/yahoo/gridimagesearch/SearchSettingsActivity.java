package com.yahoo.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchSettingsActivity extends Activity {

	Spinner imageSizeSpinner;
	Spinner colorFilterSpinner;
	Spinner imageTypeSpinner;
	EditText siteFilter;

	SearchSettings searchSettings;
	String[] imageSize;
	String[] colorFilter;
	String[] imageType;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_settings);	

        imageSizeSpinner = (Spinner) findViewById(R.id.imageSizeSpinner);
        colorFilterSpinner = (Spinner) findViewById(R.id.colorFilterSpinner);
        imageTypeSpinner = (Spinner) findViewById(R.id.ImageTypeSpinner);
        siteFilter = (EditText) findViewById(R.id.siteFilter);
        searchSettings = new SearchSettings();
        
        ArrayAdapter<CharSequence> imageSizeAdapter = ArrayAdapter.createFromResource(
                this, R.array.size, android.R.layout.simple_spinner_item);
        imageSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSizeSpinner.setAdapter(imageSizeAdapter);
        
        imageSizeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				
                int index = adapter.getSelectedItemPosition();
                
               // storing string resources into Array
               imageSize = getResources().getStringArray(R.array.size);
               searchSettings.setImageSize(imageSize[index]);
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        
        
        ArrayAdapter<CharSequence> colorFilterAdapter = ArrayAdapter.createFromResource(
                this, R.array.color, android.R.layout.simple_spinner_item);
        colorFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorFilterSpinner.setAdapter(colorFilterAdapter);
       
        colorFilterSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				
                int index = adapter.getSelectedItemPosition();
                
               // storing string resources into Array
               colorFilter = getResources().getStringArray(R.array.color);
               searchSettings.setColorFilter(colorFilter[index]);
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        

        ArrayAdapter<CharSequence> imageTypeAdapter = ArrayAdapter.createFromResource(
                this, R.array.type, android.R.layout.simple_spinner_item);
        imageTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageTypeSpinner.setAdapter(imageTypeAdapter);

        imageTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				
                int index = adapter.getSelectedItemPosition();
                
               // storing string resources into Array
               imageType = getResources().getStringArray(R.array.type);
               searchSettings.setImageType(imageType[index]);
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.advanced_search, menu);
		return true;
	}
	
	public void onSaveSearchSettings(View v) {
		Intent i = getIntent();
        searchSettings.setSiteFilter(siteFilter.getText().toString());
		i.putExtra("searchSettings", searchSettings); 
		setResult(RESULT_OK, i);
		finish();
	}

}
