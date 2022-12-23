package com.example.hw1.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hw1.Interfaces.CallBack_Location;
import com.example.hw1.Models.UserItems;
import com.example.hw1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment_Map extends Fragment {

    protected View view;
    private GoogleMap google_Map;
    private CallBack_Location callBack_location;
    //private EditText records_TXT_map;

    public static Fragment_Map newInstance() {
        Fragment_Map fragment_map = new Fragment_Map();
        return fragment_map;
    }

   /* public void setCallBack_location(CallBack_Location callBack_location){
        this.callBack_location = callBack_location;
    }*/
    

    /*@Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //SupportMapFragment mapFragment= (SupportMapFragment)getSupportFragmentManager()
    }*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        if(view==null)
            view = inflater.inflate(R.layout.fragment_map, container, false);

        //findViews(view);
        //setMap("lplplp");

        // Initialize map fragment
        SupportMapFragment supportMapFragment =(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(googleMap -> {
            this.google_Map = googleMap;});


        /*// Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // When map is loaded
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        // When clicked on map
                        // Initialize marker options
                        MarkerOptions markerOptions=new MarkerOptions();
                        // Set position of marker
                        markerOptions.position(latLng);
                        // Set title of marker
                        markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                        // Remove all marker
                        googleMap.clear();
                        // Animating to zoom the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                        // Add marker on map
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });*/

        return view;
    }

    private void findViews(View view) {
        //records_TXT_map = view.findViewById(R.id.records_TXT_map);
    }

    public void locationReady(UserItems userItems) {
        double lat = userItems.getLat();
        double lon = userItems.getLon();
        String name = userItems.getName();
        LatLng latLng = new LatLng(lat,lon);
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(new LatLng(lat,lon));
        markerOptions.title(name);
        google_Map.clear();
        google_Map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
        google_Map.addMarker(markerOptions);
        Log.d("LALA", name+lat+lon);

    }

}