package com.lthwea.finedust.activity;

import android.app.SearchManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lthwea.finedust.R;
import com.lthwea.finedust.controller.DataController;
import com.lthwea.finedust.vo.DefaultVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.lthwea.finedust.R.id.map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveCanceledListener{

    private GoogleMap mMap;
    private Geocoder geocoder;
    private Marker currentMarker;
    private List defaultMarker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x006D05));

        Log.d("MainActivity", "onCreate: call");

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // firebase FCM
       /* FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();*/



        // Google Map Setting
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        if(mapFragment != null){
            mapFragment.getMapAsync(this);
        }else{
            Toast.makeText(getApplicationContext(), "ERROR : mapFragment is null", Toast.LENGTH_SHORT).show();
        }

        //주소로 위도경도 구하기
        geocoder = new Geocoder(this);

        //Network 사용 모드
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    // 툴바
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //search view로 수정
        getMenuInflater().inflate(R.menu.main, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_settings));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                if(currentMarker != null){
                    currentMarker.remove();                     //재검색시 현재 마커 지움
                }

                Address addr = getAddress(s);
                if(addr != null){
                    if("대한민국".equals(addr.getCountryName())){
                        setAddressMarker(addr);
                    }else{
                        Toast.makeText(getApplicationContext(), "주소가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "주소가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("onQueryTextChange", s);
                return false;
            }
        });

        return true;
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 구글맵 터치시 발생하는 리스너 등록
        mMap.setOnMapClickListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveCanceledListener(this);

        Log.d("MainActivity", "onMapReady: call");

        // camera 좌쵸를 서울역 근처로 옮겨 봅니다.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(35.9077570,127.7669220)   // 위도, 경도
        ));

        // 구글지도(지구) 에서의 zoom 레벨은 1~23 까지 가능합니다.
        // 여러가지 zoom 레벨은 직접 테스트해보세요
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(7);
        mMap.animateCamera(zoom);   // moveCamera 는 바로 변경하지만,
        // animateCamera() 는 근거리에선 부드럽게 변경합니다


        DataController dc = new DataController();
        dc.getDefaultData();
        setDefaultMarker();



    }



    public Address getAddress(String addr){
        List<Address> list = null;

        try {
            list = geocoder.getFromLocationName(
                    addr, // 지역 이름
                    1); // 읽을 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("getLatLng","입출력 오류 - 서버에서 주소변환시 에러발생");
        }


        for (int i = 0 ; i < list.size() ; i++){
            Log.d("Address", list.get(i).toString());
        }


        return list.size() > 0 ? list.get(0) : null;
    }



    public void setAddressMarker(Address addr){

        // camera 좌쵸를 서울역 근처로 옮겨 봅니다.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(addr.getLatitude(), addr.getLongitude())   // 위도, 경도
        ));


        // 구글지도(지구) 에서의 zoom 레벨은 1~23 까지 가능합니다.
        // 여러가지 zoom 레벨은 직접 테스트해보세요
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
        mMap.animateCamera(zoom);   // moveCamera 는 바로 변경하지만,
        // animateCamera() 는 근거리에선 부드럽게 변경합니다

        // marker 표시
        // market 의 위치, 타이틀, 짧은설명 추가 가능.
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(addr.getLatitude(), addr.getLongitude()))
                .title(addr.getAddressLine(0))
                .snippet(addr.getFeatureName());
        currentMarker = mMap.addMarker(marker);
        currentMarker.showInfoWindow(); // 마커추가,화면에출력


        // 마커클릭 이벤트 처리
        // GoogleMap 에 마커클릭 이벤트 설정 가능.
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(),marker.getTitle() + " 클릭했음", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }


    @Override
    public void onMapClick(LatLng latLng) {
        Log.d("onMapClick", latLng.latitude + "," + latLng.longitude);
    }


    @Override
    public void onCameraMoveStarted(int i) {
        Log.d("onCameraMoveStarted", i + "\t" + mMap.getCameraPosition().zoom);

        switch (i) {
            case GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE:

                break;
            case GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION:

                break;
            case GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION:

                break;
        }


        /*
        *   6~7.5
        *   7.5~10
        *   10~12
        *   12~15
        *
        * */

    }

    @Override
    public void onCameraMove() {
        Log.d("onCameraMove", "");


    }

    @Override
    public void onCameraIdle() {
        Log.d("onCameraIdle", "");


    }


    @Override
    public void onCameraMoveCanceled() {

    }


    public void setDefaultMarker(){

        defaultMarker = new ArrayList();
        MarkerOptions marker = new MarkerOptions();

        if(DataController.JSON_DEFAULT_LIST != null){
            for(int i = 0 ; i < DataController.JSON_DEFAULT_LIST.size() ; i++){
                DefaultVO vo = (DefaultVO) DataController.JSON_DEFAULT_LIST.get(i);

                marker.position(new LatLng(vo.getLat(), vo.getLng()))
                        .title(vo.getCityName())
                        .snippet(vo.getCityvalue());
                Marker tmp = mMap.addMarker(marker);
                tmp.showInfoWindow(); // 마커추가,화면에출력

                defaultMarker.add(tmp); 
            }
        }else{
            Log.e("setDefaultMarker", "DataController.JSON_DEFAULT_LIST NULL ERROR");
        }



    }







}

//동 -> 15
//구 -> 12
//시 -> 10
//한반도 전체보기 6~7정도