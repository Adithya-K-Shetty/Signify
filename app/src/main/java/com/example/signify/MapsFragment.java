package com.example.signify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.net.PlacesClient;


public class MapsFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private PlacesClient placesClient;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.style_json));
            mMap = googleMap;
            // Add your customizations to the map

            // Get the current location
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            } else {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), location -> {
                            if (location != null) {
                                // Update the map with the current location
                                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.carmarker)).position(currentLocation).title("your location"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.954101081006554,74.91418997235218)).title("MGK Auto Works"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.870668108080803, 74.92053284757642)).title("Om Sai Auto Care"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.870166062249725, 74.91812958847144)).title("Balaji Body Works"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.871170152906714, 74.90491166339409)).title("Cauvery Ford Service"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.873513015469506, 74.89632859516206)).title("Canara car care center"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.878031331436063, 74.88843217238856)).title("Vehiclecare - Planet autowork"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.875854035919435, 74.88559678421052)).title("Ceat Autocare"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.864690420603877, 74.88907285557275)).title("WESTON AUTO TECH"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.852138673935055, 74.86401029414363)).title("Amma auto works"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.879751686687579, 74.85147901452484)).title("Car Garage"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.89447738320186, 74.88426633557754)).title("Antony Peter Dcosta"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.911210077620673, 74.86486860068365)).title("Central mechanical organizaton"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.926268544791663, 74.87207837799858)).title("Smart Cars | Car Cardiac Care Mangalore"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.930618599587264, 74.86521192341294)).title("Maruti 4 wheeler garage"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.909369536214111, 74.85130735252002)).title("MADHU GARAGE"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.927774341726211, 74.84272428428797)).title("TORQUE WRENCH AUTO REPAIR &DETAILING STUDIO"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.93296090553586, 74.83791776607802)).title("SG Automotives"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.929112819985377, 74.83534284434027)).title("Sri Manjunatha Auto Works & Service Station"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.902174561965332, 74.8379177649207)).title("Sri Durga Autoworks"));
                                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(getContext(),R.drawable.baseline_build_24)).position(new LatLng(12.894644716056307, 74.84032102402567)).title("Shriyan Auto Works"));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(currentLocation )
                                        .zoom(18)
//                                        .bearing(90)
//                                        .tilt(30)
                                        .build();
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            }
                        });
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        // Create the location request
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(1000);

        // Create the location callback
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update the map with the new location
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                }
            }
        };

        // Request location updates
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectoreResId){
        Drawable vectorDrawable= ContextCompat.getDrawable(context,vectoreResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}