package com.example.nearbyplaces;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; //Object of google map
    EditText placesedt;   // EditText object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        placesedt = findViewById(R.id.placetxt);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);  
    }
	
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			//check the permissions if not given
            return;
        }
        googleMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(21.1794873,79.0601579)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(21.1794873,79.0601579)).title("Rcoem")).showInfoWindow();

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
				//This method will fetch the information taht which marker's snippet is clicked and pass it to the method openWebPage
                Log.d("MapsActivity","INSIDE ONINFOWINDOWCLICK");
                String markerString = marker.getSnippet();
                openWebPage(markerString);
            }
        });
    }
    public void requestApi(String place){
		//This method takes the basic information about the plotting and getting of location
        StringBuilder stringBuilder = new StringBuilder();   
        stringBuilder.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");  
        stringBuilder.append("location=21.1794873,79.0601579");    //location from which we want see the near by places
        stringBuilder.append("&radius="+1000);      // The radius of searching near by places
        stringBuilder.append("&keyword="+place);    //The edit text string we gave is fetched here
        stringBuilder.append("&key="+"AIzaSyCYkpb4CN8UW2m6FovXfUW6ktcUFqgknOM");  // google api key
        Log.d("MapsActivity:","Inside reuestApi");    //log is printed 
        String url=stringBuilder.toString();     
        Object[] objects=new Object[2];
        objects[0]=mMap;
        objects[1]=url;
        DownloadUrl downloadUrl=new DownloadUrl();     //passed to Async task all the object's info got from here 
        downloadUrl.execute(objects);     
    }

    public void openWebPage(String url) {

        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+url);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri); //Intent to pass to the google maps when clicked on onInfoWindowClick to view info of that particular searched place
        mapIntent.setPackage("com.google.android.apps.maps");  // package of google map   
        startActivity(mapIntent);

    }

    public void start(View view) {
        mMap.clear();
        String place = placesedt.getText().toString(); //this line takes the text from  edit text
        if(place.equals(""))    //if edit text is empty then it will show a alert toast
            Toast.makeText(getApplicationContext(),"Place field empty",Toast.LENGTH_SHORT);
        else
            requestApi(place); //the place is passed to request api to get basic properties of it
    }
}

