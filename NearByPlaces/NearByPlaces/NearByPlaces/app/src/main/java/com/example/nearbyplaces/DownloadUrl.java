package com.example.nearbyplaces;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUrl extends AsyncTask<Object, String, String> {

    GoogleMap googleMap;
    String url;
    InputStream inputStream;
    BufferedReader bufferedReader;
    String data;

    @Override
    protected String doInBackground(Object... objects) {
        googleMap = (GoogleMap) objects[0];
        url = (String) objects[1]; //url object array
        try {

            URL newUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) newUrl.openConnection();
            httpURLConnection.connect(); // here the url passed from request api is fetched to process forward 

            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            data = stringBuilder.toString();
            Log.d("DownloadUrl","\n"+data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for(int i=0;i<jsonArray.length();i++){
                JSONObject resultObject = jsonArray.getJSONObject(i); //here the json object has the results of the maps after search
                JSONObject locationObject = resultObject.getJSONObject("geometry").getJSONObject("location");
                String latitude = locationObject.getString("lat");
                String longitude = locationObject.getString("lng");

                LatLng latLng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));

//                JSONArray photosArrayObject = resultObject.getJSONArray("photos");
//                JSONObject photosObject = photosArrayObject.getJSONObject(0);
//                JSONArray htmlArrayObject = photosObject.getJSONArray("html_attributions");
//                JSONObject htmlObject = htmlArrayObject.getJSONObject(0);


                String name = resultObject.getString("name");   //here the place name is passed
                String vicinity = resultObject.getString("vicinity");  // proximity of the place


                //following are some marker properties through which we can customise it.
                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.title(name); 
                markerOptions.snippet(vicinity);
                markerOptions.position(latLng);
                Marker marker=googleMap.addMarker(markerOptions);
                marker.showInfoWindow(); // info window is shown which has intent within it

                Circle circle = googleMap.addCircle(new CircleOptions() //just a customisation to the marker
                        .center(latLng)
                        .radius(5)
                        .strokeColor(Color.RED)
                        .fillColor(Color.BLUE));


            }
            Log.d("DownloadUrl","\n"+s);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
