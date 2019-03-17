# NearByPlaces


----------------------------------------------------------------------------------------------------------------------------------------
Problem Definition :
----------------------------------------------------------------------------------------------------------------------------------------
Design an application to create a local building as object. The map will have details about major highlights of building.  On click function will produce either a image/video frame of highlight point. [Minimum  4 points in map].

----------------------------------------------------------------------------------------------------------------------------------------
Problem description :
----------------------------------------------------------------------------------------------------------------------------------------

The mapping and directions app is getting a refreshed look that highlights points of interest more clearly. Areas that include several points of interest will now be shaded customed on the map, for instance. Google determines these clusters by using an algorithmic process for finding regions with the highest density of bars, restaurants, and shops. Other types of locations, like schools and hospitals, will be colour coded as well.
Google has also changed the way names of places, streets, and transportation stations appear, in order to make them more distinguishable from other elements on the map. The goal is to make the map look cleaner and less cluttered.  
As a result using the google application programming interfaces the android app will be created which will locate the place which we searched in the radius of 1000 . Eg. If we search for temples then it will give all the temples near by which is in the radius of 1000 and markers will be placed on them. Also, clicking upon snippet will redirect us to that specific marker’s location details.

----------------------------------------------------------------------------------------------------------------------------------------
Technology stack :
----------------------------------------------------------------------------------------------------------------------------------------
1.	Google Maps API for map preview with android application
2.	Google Maps Places API for fetching the places on the map
3.	Java JDK + JRE environment Version 1.8 for backend development
4.	XML for UI development
----------------------------------------------------------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------------------------------------------------------
Functions used in application :
----------------------------------------------------------------------------------------------------------------------------------------

1.	public void requestApi(String place)
    This method takes various information in the form of strings and add it to String builder. This method gives information such as location in the form of longitude and latitudes, The radius in which the desired places is to be searched, the place which we search, the google key to actiate map utilities , and pass it on to an async task (DownloadUrl).

2.	public void openWebPage(String url)
    This is a user defined method, which redirects us to the google map details page when we click on the particular marker’s s info window.

3.	public void onMapReady(GoogleMap googleMap)
    onMapReady(com.google.android.gms.maps.GoogleMap googleMap) Manipulates the map once available. This callback is triggered when the map is ready to be used. This is where we can add markers or lines, add listeners or move the camera.

4.	protected void onPostExecute(String s)
    On onPostExecute() we have options to whether intent to next activity on perform next action and send the process data. If we have initialized the progress bar,then we need to hide it because at this method the async thread is returned to main thread to show results.


----------------------------------------------------------------------------------------------------------------------------------------
Key Functionality :
----------------------------------------------------------------------------------------------------------------------------------------
       The app basically focuses on to highlight specific points of a specific building and those points are highlighted using markers, this is done with the help of google places api, These markers are already there beacause our app is generalised  that is we pass some place in the edit text and search for it and our app gives information about those places which is in the radius of 1000 meters and places markers on it. When we click on that marker, we get a snippet with a title and some information, also when we click on that window we get redirected to google maps information page which is related to that marker location.

