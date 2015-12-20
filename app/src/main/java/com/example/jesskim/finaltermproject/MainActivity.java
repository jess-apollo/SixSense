package com.example.jesskim.finaltermproject;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends AppCompatActivity {

    // 객체 선언
    public Button btn_record;
    public Button btn_update;

    public Marker m_myPosition;
    public TextView tv_log;

    private GoogleMap map;
    double lat, lng;

    final static int U_Activity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Main", "onCreate");
        btn_record = (Button) findViewById(R.id.btn_record);
        btn_update = (Button) findViewById(R.id.btn_update);
        tv_log = (TextView) findViewById(R.id.tv_log);

        // Location Manager 참조 얻기
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // GPS Provider 사용가능 여부
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // Network Provider 사용가능 여부
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();

                final LatLng l_myPosition = new LatLng( lat, lng );

                map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                m_myPosition = map.addMarker(new MarkerOptions().position(l_myPosition).title("myPosition"));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(l_myPosition, 15));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                tv_log.setText("onStatusChanged");
            }

            @Override
            public void onProviderEnabled(String provider) {
                tv_log.setText("onProviderEnabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                tv_log.setText("onProviderDisabled");
            }
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        /*// 수동으로 위치 구하기
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if(lastKnownLocation != null) {
            lng = lastKnownLocation.getLongitude();
            lat = lastKnownLocation.getLatitude();
            Log.d("Main", "longtitud=" + lng + ", latitude=" + lat);

            final LatLng l_myPosition = new LatLng( lat, lng );

            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            m_myPosition = map.addMarker(new MarkerOptions().position(l_myPosition).title("myPosition"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(l_myPosition, 15));
        }*/


        // UpdateActivity로 이동
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] place = {lng, lat};
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("Place", place);    // 인텐트에 데이터 심어 보내기

                Toast.makeText(getApplicationContext(), "현재 위치에서 메모를 저장합니다.", Toast.LENGTH_SHORT).show();

                startActivityForResult(intent, U_Activity);
            }
        });

        //RecordActivity로 이동
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });
    }
}
