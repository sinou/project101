package com.droid.srini.map21;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by srinivasane on 2/18/2015.
 */
public class Coordinates  implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback {

    private double latitude;
    private double longitude;
    private GoogleApiClient mGoogleApiClient;
    private Context applicationContext;

    public Coordinates(Context applicationContext){
        this.applicationContext = applicationContext;

        setmGoogleApiClient();
        mGoogleApiClient.connect();
        setLatitude();
        setLongitude();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude() {
        String temp = "" + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        this.latitude = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient).getLatitude();
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude() {
        this.longitude = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient).getLongitude();
    }

    public GoogleApiClient getmGoogleApiClient() {

        return mGoogleApiClient;
    }

    public void setmGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(applicationContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }


}
