/**
 * 
 */
package com.faculdade.epidem.model;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author guilhermeramos
 *
 */
public class LocationTracker implements OnMyLocationChangeListener  {
	LatLng location;
	GoogleMap map;
	
	/**
	 * 
	 */
	public LocationTracker() {
		// TODO Auto-generated constructor stub
	}

	public LocationTracker(GoogleMap map){
		super();
		this.map = map;
	}
	
	@Override
	public void onMyLocationChange(Location location) {
		this.location = new LatLng(location.getLatitude(), location.getLongitude());
		if(this.map != null){
			this.map.addMarker(new MarkerOptions()
	    						.title("Sua Localizacao")
	    						.snippet("Sua licalizacao atual.")
	    						.position(this.location));
			this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(this.location, 16.0f));
		}
	}
	
}
