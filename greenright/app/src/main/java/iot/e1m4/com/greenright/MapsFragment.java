package iot.e1m4.com.greenright;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.app.AppConfig;
import info.app.AppController;


public class MapsFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        LocationListener {

    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 15000;
    private static final int FASTEST_UPDATE_INTERVAL_MS = 15000;

    private static final int DEFAULT_ZOOM = 15;
    private final LatLng DEFAULT_LOCATION = new LatLng(37.500763, 127.036842);

    private GoogleMap mMap = null;
    private MapView mapView = null;
    private GoogleApiClient googleApiClient = null;
    private Marker currentMarker = null;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;


    private final static int MAXENTRIES = 5;
    private String[] LikelyPlaceNames = null;
    private String[] LikelyAddresses = null;
    private String[] LikelyAttributions = null;
    private LatLng[] LikelyLatLngs = null;


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }




    private CameraPosition mCameraPosition;
    private void getDeviceLocation(float cameraZoom) {
        if (mLocationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(googleApiClient);
        }
        // Set the map's camera position to the current location of the device.
        if (mCameraPosition != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
        } else if (mLastKnownLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()), cameraZoom));
        } else {
            Log.d(TAG, "Current location is null. Using defaults.");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, cameraZoom));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button storeListViewBtn;
    Button cupListViewBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView)layout.findViewById(R.id.map);
        mapView.getMapAsync(this);
        storeListViewBtn = (Button) layout.findViewById(R.id.storeListViewButton);
        storeListViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMap == null) return;
                //////////////////////////가맹점 리스트 받아오기
                viewStoreListMarker();
            }
        });
        cupListViewBtn = (Button) layout.findViewById(R.id.cupListViewButton);
        cupListViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMap == null) return;
                //////////////////////////수거함 리스트 받아오기
                viewCupListMarker();
            }
        });
        return layout;
    }


    static StringRequest stringRequest;
    private void viewCupListMarker() {
        stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_BOX_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mMap.clear();

                        try {
                            JSONArray jArray = new JSONArray(response);
                            JSONObject jObject;
                            LatLng storeLatLng;
                            for (int i=0; i <jArray.length(); i++){
                                jObject =jArray.getJSONObject(i);
                                storeLatLng = new LatLng(jObject.getDouble("recycleBoxLat"),
                                        jObject.getDouble("recycleBoxLong"));
                                mMap.addMarker(new MarkerOptions().position(storeLatLng)
                                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ///리스트 가져오기 실패
                Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().
                addToRequestQueue(stringRequest);
    }


    private void viewStoreListMarker() {
        stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_STORE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mMap.clear();

                        try {
                            JSONArray jArray = new JSONArray(response);
                            JSONObject jObject;
                            LatLng storeLatLng;
                            for (int i=0; i <jArray.length(); i++){
                                jObject =jArray.getJSONObject(i);
                                storeLatLng = new LatLng(jObject.getDouble("storeLat"), jObject.getDouble("storeLong"));
                                mMap.addMarker(new MarkerOptions().position(storeLatLng)
                                        .title(jObject.getString("storeName")));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ///리스트 가져오기 실패
                Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().
                addToRequestQueue(stringRequest);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        if ( googleApiClient != null && googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if ( googleApiClient != null) googleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        if ( googleApiClient != null || googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.stopAutoManage(getActivity());
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if ( googleApiClient != null ) {
            googleApiClient.unregisterConnectionCallbacks(this);
            googleApiClient.unregisterConnectionFailedListener(this);

            if ( googleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
                googleApiClient.disconnect();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수
        MapsInitializer.initialize(getActivity().getApplicationContext());

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.mMap = googleMap;

        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

            if ( hasFineLocationPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            } else {
                if ( googleApiClient == null) {
                    buildGoogleApiClient();
                }
                if ( ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    mMap.setMyLocationEnabled(true);
                }
            }
        } else {
            if ( googleApiClient == null) {
                buildGoogleApiClient();
            }

            mMap.setMyLocationEnabled(true);
        }

        updateLocationUI();
        getDeviceLocation(DEFAULT_ZOOM);

    }


    //마커 클릭 이벤트 리스너
    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();
        googleApiClient.connect();
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if ( !checkLocationServicesStatus()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("위치 서비스 비활성화");
            builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
                    "위치 설정을 수정하십시오.");
            builder.setCancelable(true);
            builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent callGPSSettingIntent =
                            new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
        }

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL_MS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ( ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                LocationServices.FusedLocationApi
                        .requestLocationUpdates(googleApiClient, locationRequest, this);
            }
        } else {
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(googleApiClient, locationRequest, this);

            this.mMap.getUiSettings().setCompassEnabled(true);
            this.mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }

    }

    @Override
    public void onConnectionSuspended(int cause) {
        if ( cause ==  CAUSE_NETWORK_LOST )
            Log.e(TAG, "onConnectionSuspended(): Google Play services " +
                    "connection lost.  Cause: network lost.");
        else if (cause == CAUSE_SERVICE_DISCONNECTED )
            Log.e(TAG,"onConnectionSuspended():  Google Play services " +
                    "connection lost.  Cause: service disconnected");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Location location = new Location("");
        location.setLatitude(DEFAULT_LOCATION.latitude);
        location.setLongitude((DEFAULT_LOCATION.longitude));

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged call..");
        updateLocationUI();
        getDeviceLocation(mMap.getCameraPosition().zoom);
        //Toast.makeText(getActivity(), "위치변경", Toast.LENGTH_SHORT).show();

    }



}
