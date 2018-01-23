package com.example.aroraharsh010.memorableplaces;

import android.app.ActionBar;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
     int location=-1;



    @Override
    public void onMapLongClick(LatLng point) {
        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
        String a=new Date().toString();
        try {

            List<Address> addressList=geocoder.getFromLocation(point.latitude,point.longitude,1);
            if (addressList!=null&&addressList.size()>0)
            {
                a=addressList.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainActivity.places.add(a);
        MainActivity.arrayAdapter.notifyDataSetChanged();
        MainActivity.locations.add(point);
        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title(a)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent i=getIntent();
       location= i.getIntExtra("locationInfo",-1);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       if (location!=-1 && location!=0) {
           mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MainActivity.locations.get(location), 10));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(MainActivity.locations.get(location)));
           mMap.addMarker(new MarkerOptions().position(MainActivity.locations.get(location)).title(MainActivity.places.get(location)));

       }
        mMap.setOnMapLongClickListener(this);
    }
}
