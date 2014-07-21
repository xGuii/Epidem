package com.faculdade.epidem;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.faculdade.epidem.interfaces.OnTaskCompleted;
import com.faculdade.epidem.model.Coordenada;
import com.faculdade.epidem.resources.ServiceTaskCoordenadas;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends Activity implements OnClickListener, OnTaskCompleted, LocationListener {

	public static final String TAG = "Map";
	
	private ArrayList<Coordenada> coordenadas;
	private LocationManager locationManager;
	private LatLng position;
	private Button btnOverMap;
	private Marker marker;
	private String provider;
	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		btnOverMap = (Button) findViewById(R.id.btnOverMap);
    	btnOverMap.setOnClickListener(this);
    	
    	//Buscando o gerenciador de localização
    	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	
    	// Definindo o criterio para seleção do provedor de localização -> default;
    	Criteria criteria = new Criteria();
    	provider = locationManager.getBestProvider(criteria, false);
    	Location location = locationManager.getLastKnownLocation(provider);
    	if(location != null){
        	position = new LatLng(location.getLatitude(), location.getLongitude());
    		map.setMyLocationEnabled(true);
    		map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13));
    		
    		marker = map.addMarker(new MarkerOptions()
    							.title("Localização atual.")
    							.snippet("Você está aqui!")
    							.position(position));
    		onLocationChanged(location);
    	} else {
    		Toast.makeText(this, "Localização não disponível.", Toast.LENGTH_SHORT).show();
    	}
    	
		if (savedInstanceState == null) {
			
		}
	}
	
	public void animateMarker(final Marker marker, final LatLng toPosition,
            final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = map.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final LinearInterpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) (elapsed/ duration));
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

	@Override
	protected void onResume() {
		super.onResume();
	    this.listarCoordenadas();
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	@Override
	protected void onPause() {
		super.onStop();
	    locationManager.removeUpdates(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btnOverMap:
					Intent intent = new Intent(this, Coordinates.class);
					intent.putExtra("LATITUDE", position.latitude);
					intent.putExtra("LONGITUDE", position.longitude);
					
					startActivity(intent);				
				break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onTaskCompleted(Object coordenadas) {
		this.coordenadas = (ArrayList<Coordenada>)coordenadas;
        	for(Coordenada coordenada : this.coordenadas){
	            LatLng loc = new LatLng(coordenada.getLatitude(), coordenada.getLongitude());
	            
	            map.setMyLocationEnabled(true);
	            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 13));
	            map.addMarker(new MarkerOptions()
	            		.title(coordenada.getTipoTitle())
	                    .snippet(coordenada.getDescricao())
	                    .position(loc));
        }
	}

	@Override
	public void onLocationChanged(Location location) {
		position = new LatLng(location.getLatitude(), location.getLongitude());
		animateMarker(marker, position, false);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	private void listarCoordenadas(){
		try{
			ServiceTaskCoordenadas stc = new ServiceTaskCoordenadas(this, "", this);
			stc.execute();
		} catch (Exception e) {
			Log.i(TAG, "Falha ao obter coordenadas.", e);
		}
	}

}
