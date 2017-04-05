package com.lthwea.finedust.activity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.lthwea.finedust.R;
import com.lthwea.finedust.controller.DataController;
import com.lthwea.finedust.vo.DefaultVO;
import com.lthwea.finedust.vo.MarkerVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.lthwea.finedust.R.id.map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
//        GoogleMap.OnMapClickListener,
//        GoogleMap.OnCameraMoveStartedListener,
//        GoogleMap.OnCameraMoveListener,
//        GoogleMap.OnCameraIdleListener,
//        GoogleMap.OnCameraMoveCanceledListener,
        ClusterManager.OnClusterClickListener<MarkerVO>, ClusterManager.OnClusterInfoWindowClickListener<MarkerVO>, ClusterManager.OnClusterItemClickListener<MarkerVO>,
        ClusterManager.OnClusterItemInfoWindowClickListener<MarkerVO> {


    private GoogleMap mMap;
    private Geocoder geocoder;
    private Marker currentMarker;
    private List defaultMarkerList;
    private boolean isDefaultMarkerVisible;

    private ClusterManager<MarkerVO> mClusterManager;



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
//        mMap.setOnMapClickListener(this);
//        mMap.setOnCameraMoveStartedListener(this);
//        mMap.setOnCameraMoveListener(this);
//        mMap.setOnCameraMoveCanceledListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.9077570,127.7669220), 6));

        mClusterManager = new ClusterManager<MarkerVO>(this, mMap);
        mClusterManager.setRenderer(new MarkerVORenderer());
        //mMap.setOnCameraIdleListener(mClusterManager);
        final CameraPosition[] mPreviousCameraPosition = {null};
        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition position = mMap.getCameraPosition();
                if(mPreviousCameraPosition[0] == null || mPreviousCameraPosition[0].zoom != position.zoom) {
                    mPreviousCameraPosition[0] = mMap.getCameraPosition();
                    mClusterManager.cluster();
                }
            }
        });

        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        addItems();
        mClusterManager.cluster();

        Log.d("MainActivity", "onMapReady: call");


        // 구글지도(지구) 에서의 zoom 레벨은 1~23 까지 가능합니다.
        // 여러가지 zoom 레벨은 직접 테스트해보세요
//        CameraUpdate zoom = CameraUpdateFactory.zoomTo(6);
//        mMap.animateCamera(zoom);   // moveCamera 는 바로 변경하지만,
        // animateCamera() 는 근거리에선 부드럽게 변경합니다


/*        DataController dc = new DataController();
        String json = dc.getDefaultData();*/

/*

        mClusterManager = new ClusterManager<DefaultVO>(this, mMap);
        List<DefaultVO> items = null;
        try {
            items = new VOReader().read(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mClusterManager.addItems(items);

*/

 //       defaultMarkerList = new ArrayList();

       /* try {
            dc.getDefaultData2();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

       /* setDefaultMarker();
        dc.getSeoulData();
*/


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


//    @Override
//    public void onMapClick(LatLng latLng) {
//        Log.d("onMapClick", latLng.latitude + "," + latLng.longitude);
//    }

//
//    @Override
//    public void onCameraMoveStarted(int i) {
//
//        double currentZoom = mMap.getCameraPosition().zoom;
//
//        Log.d("onCameraMoveStarted", i + "\t" + currentZoom);
//
//
//      /*  if( currentZoom <= 10 ){
//
//            if( isDefaultMarkerVisible == false && defaultMarkerList != null) {
//                for (int j = 0; j < defaultMarkerList.size(); j++) {
//                    Marker m = (Marker) defaultMarkerList.get(j);
//                    m.setVisible(true);
//                }
//
//                isDefaultMarkerVisible = true;
//            }
//
//        }else if(currentZoom > 10 && currentZoom <= 12){
//            Log.d("currentZoom", "open");
//            if( isDefaultMarkerVisible == true && defaultMarkerList != null){
//                for(int j = 0; j < defaultMarkerList.size() ; j++){
//                    Marker m = (Marker) defaultMarkerList.get(j);
//                    m.setVisible(false);
//                }
//
//                isDefaultMarkerVisible = false;
//            }
//        }*/
//
//
//
//        /*
//        *   6~7.5           //전체
//        *   7.5~10          //시
//        *   10~12           //구
//        *   12~15           //동
//        *
//        * */
//
//      /*  switch (i) {
//            case GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE:
//
//                break;
//            case GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION:
//
//                break;
//            case GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION:
//
//                break;
//        }
//
//        */
///*
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.8696, 151.2094), 10));
//
//        IconGenerator iconFactory = new IconGenerator(this);
//        addIcon(iconFactory, "Default", new LatLng(-33.8696, 151.2094));
//
//        iconFactory.setColor(Color.CYAN);
//        addIcon(iconFactory, "Custom color", new LatLng(-33.9360, 151.2070));
//
//        iconFactory.setRotation(90);
//        iconFactory.setStyle(IconGenerator.STYLE_RED);
//        addIcon(iconFactory, "Rotated 90 degrees", new LatLng(-33.8858, 151.096));
//
//        iconFactory.setContentRotation(-90);
//        iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
//        addIcon(iconFactory, "Rotate=90, ContentRotate=-90", new LatLng(-33.9992, 151.098));
//
//        iconFactory.setRotation(0);
//        iconFactory.setContentRotation(90);
//        iconFactory.setStyle(IconGenerator.STYLE_GREEN);
//        addIcon(iconFactory, "ContentRotate=90", new LatLng(-33.7677, 151.244));
//
//     iconFactory.setRotation(0);
//        iconFactory.setContentRotation(0);
//        iconFactory.setStyle(IconGenerator.STYLE_ORANGE);
//        addIcon(iconFactory, makeCharSequence(), new LatLng(-33.77720, 151.12412));*/
//
//    }

/*
    @Override
    public void onCameraMove() {
        Log.d("onCameraMove", "onCameraMove");


    }

    @Override
    public void onCameraIdle() {
        Log.d("onCameraIdle", "onCameraIdle");


    }

    @Override
    public void onCameraMoveCanceled() {
        Log.d("onCameraMoveCanceled", "cancle");
    }
*/


    public void setDefaultMarker(){

        defaultMarkerList = new ArrayList();
       // MarkerOptions marker = new MarkerOptions();

        IconGenerator iconFactory = new IconGenerator(this);

        if(DataController.JSON_DEFAULT_LIST != null){
            for(int i = 0 ; i < DataController.JSON_DEFAULT_LIST.size() ; i++){
                DefaultVO vo = (DefaultVO) DataController.JSON_DEFAULT_LIST.get(i);

                int val = Integer.parseInt(vo.getCityvalue());
                int color = getMarkerColor(val);
                iconFactory.setColor(color);
                addIcon(iconFactory, vo.getCityName() + ":" +vo.getCityvalue(), new LatLng(vo.getLat(), vo.getLng()));

            }

            isDefaultMarkerVisible = true;
        }else{
            Log.e("setDefaultMarker", "DataController.JSON_DEFAULT_LIST NULL ERROR");
        }

    }

    private void addIcon(IconGenerator iconFactory, String text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        Marker tmp = mMap.addMarker(markerOptions);

        defaultMarkerList.add(tmp);
    }

    public int getMarkerColor(int val){
        if( val >= 0 && val <= 30 ){
            return Color.BLUE;
        } else if( val >= 31 && val <= 80 ) {
            return Color.GREEN;
        } else if( val >= 81 && val <= 150 ) {
            return Color.YELLOW;
        } else if( val >= 151 ){
            return Color.RED;
        } else{
            return Color.BLACK;
        }

    }

    @Override
    public boolean onClusterClick(Cluster<MarkerVO> cluster) {
        Log.d("onClusterClick", cluster.getSize()+"");

        // Show a toast with some info when the cluster is clicked.
        String firstName = cluster.getItems().iterator().next().getCityName();
        Toast.makeText(this, cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();

            // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
            // inside of bounds, then animate to center of the bounds.

        // Create the builder to collect all essential cluster items for the bounds.
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
                builder.include(item.getPosition());
        }
        // Get the LatLngBounds
        final LatLngBounds bounds = builder.build();
        // Animate camera to the bounds
        try {
            //mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(), (float) Math.floor(mMap.getCameraPosition().zoom + 1)), 300,null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    @Override
    public void onClusterInfoWindowClick(Cluster<MarkerVO> cluster) {
        // Does nothing, but you could go to a list of the users.
    }

    @Override
    public boolean onClusterItemClick(MarkerVO item) {
        // Does nothing, but you could go into the user's profile page, for example.
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(MarkerVO item) {
        // Does nothing, but you could go into the user's profile page, for example.
    }

    private void addItems() {
        DataController dc = new DataController();
        dc.getData();

        if(DataController.JSON_DEFAULT_LIST != null){
            for(int i = 0 ; i < DataController.JSON_DEFAULT_LIST.size() ; i++){
                MarkerVO vo = (MarkerVO) DataController.JSON_DEFAULT_LIST.get(i);
                mClusterManager.addItem(vo);

            }

        }else{
            Log.e("setDefaultMarker", "DataController.JSON_DEFAULT_LIST NULL ERROR");
        }

 /*
        mClusterManager.addItem(new MarkerVO(new LatLng(37.5665350,126.9779690), "서울1", "1", "10"));
        mClusterManager.addItem(new MarkerVO(new LatLng(37.6665350,126.9779690), "서울2", "1", "10"));
        mClusterManager.addItem(new MarkerVO(new LatLng(37.6665350,126.9879690), "서울2", "1", "10"));
        mClusterManager.addItem(new MarkerVO(new LatLng(37.6665350,126.9679690), "서울2", "1", "10"));
        mClusterManager.addItem(new MarkerVO(new LatLng(37.6765350,126.9779690), "서울2", "1", "10"));
      mClusterManager.addItem(new MarkerVO(new LatLng(35.8714350,128.6014450), "서울1", "2", "10"));
        mClusterManager.addItem(new MarkerVO(new LatLng(35.1595450,126.8526010), "서울1", "3", "10"));
        mClusterManager.addItem(new MarkerVO(new LatLng(33.4890110,126.4983020), "서울1", "4", "10"));
        mClusterManager.addItem(new MarkerVO(new LatLng(37.8699840,127.7433860), "서울1", "5", "10"));
        mClusterManager.addItem(new MarkerVO(new LatLng(37.4138000,127.5183000), "경기", "5", "10"));
        mClusterManager.addItem(new MarkerVO(new LatLng(37.2635730,127.0286010), "수원", "5", "10"));
*/

    }

/*

    private LatLng position() {
        return new LatLng(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683));
    }

    private double random(double min, double max) {
        return mRandom.nextDouble() * (max - min) + min;
    }
*/




    /**
     * Draws profile photos inside markers (using IconGenerator).
     * When there are multiple people in the cluster, draw multiple photos (using MultiDrawable).
     */
    private class MarkerVORenderer extends DefaultClusterRenderer<MarkerVO> {

        private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());

        /*private final ImageView mImageView;
        private final ImageView mClusterImageView;*/

  /*      private final TextView mTextView;
        private final TextView mClusterTextView;
*/

        public MarkerVORenderer() {
            super(getApplicationContext(), mMap, mClusterManager);
/*
            View multiProfile = getLayoutInflater().inflate(R.layout.multi_profile, null);
            mClusterIconGenerator.setContentView(multiProfile);
            mClusterTextView = (TextView) multiProfile.findViewById(R.id.tv_map_marker);
            mTextView = new TextView(getApplicationContext());
            mIconGenerator.setContentView(mTextView);
            */
         /*    mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);
            mImageView = new ImageView(getApplicationContext());
           mDimension = (int) getResources().getDimension(R.dimen.custom_profile_image);
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
            int padding = (int) getResources().getDimension(R.dimen.custom_profile_padding);
            mImageView.setPadding(padding, padding, padding, padding);*/

        }

        @Override
        protected void onBeforeClusterItemRendered(MarkerVO vo, MarkerOptions markerOptions) {
            Log.d("asadsadsad", vo.getCityName());
            // Draw a single person.
            // Set the info window to show their name.
            //mImageView.setImageResource(R.drawable.ic_menu_camera);
            //Bitmap icon = mIconGenerator.makeIcon();
            //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(vo.getCityName());
            String str = vo.getCityName() + " " + vo.getSidoName() + " " + vo.getPm10Value();
            //mTextView.setText(str);
            mIconGenerator.setColor(Color.YELLOW);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon(str)))
                    .anchor(mIconGenerator.getAnchorU(), mIconGenerator.getAnchorV());
            super.onBeforeClusterItemRendered(vo, markerOptions);

        }



       @Override
        protected void onBeforeClusterRendered(Cluster<MarkerVO> cluster, MarkerOptions markerOptions) {


            Log.d("onBeforeClusterRendered", cluster.getSize() + "");

           // Draw multiple people.
           // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).

           //Drawable marker;
           int ClusterSize = cluster.getSize();

           //marker = getApplication().getResources().getDrawable(R.drawable.ic_menu_camera);
           mClusterIconGenerator.setColor(Color.GREEN);

           LayoutInflater myInflater = (LayoutInflater)getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           View activityView = myInflater.inflate(R.layout.multi_profile, null, false);

           mClusterIconGenerator.setContentView(activityView);
           mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));

           BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(mClusterIconGenerator.makeIcon());
           markerOptions.icon(icon);


        }



        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            Log.d("shouldRenderAsCluster", cluster.getSize()+"");

            // Always render clusters.
            //return cluster.getSize() > 5;
            return cluster.getSize() > 5;

        }




    }

}

//동 -> 15
//구 -> 12
//시 -> 10
//한반도 전체보기 6~7정도