package com.LocationLibrary;

import com.LocationLibrary.db.DbConfig;
import com.LocationLibrary.db.model.LocationsModel;
import com.google.android.gms.location.LocationRequest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DemoActivity extends Activity implements OnClickListener{

	private LocationClientUtils utils;
	private Button button;
	private TextView txtLattitude,txtLongitude;
	private int invalideTimeInSeconds=180,intervalToFetchLocation=60;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		setContentView(R.layout.activity_demo);
		
		button = (Button)findViewById(R.id.button1);
		txtLattitude = (TextView)findViewById(R.id.txtLattitude);
		txtLongitude = (TextView)findViewById(R.id.txtLongitude);
		
		button.setOnClickListener(this);
		
		utils = LocationClientUtils.getInstance();
		LocationClientUtils.initializeLocations(getApplicationContext(),DbConfig.getInstance());
		utils.startFetchingLocations(	getApplicationContext(),
		                             	intervalToFetchLocation,
										LocationRequest.PRIORITY_HIGH_ACCURACY,
										0);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		utils.stopFetchingLocations();
		utils.clearLocationsTable();
	}

	@Override
	public void onClick(View v) { 
		
		LocationsModel model = utils.getLatestLocation(invalideTimeInSeconds);
		
		if(model!=null){
			txtLattitude.setText(model.getLattitude()+"");
			txtLongitude.setText(model.getLongitude()+"");
		}else{
			Toast.makeText(	getApplicationContext(),
							"Location Not found " + invalideTimeInSeconds + " seconds prior to current time",
							Toast.LENGTH_SHORT)
					.show();
		}
		
	}
}
