package com.lthwea.finedust.activity;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import com.lthwea.finedust.cnst.MapConst;
import com.lthwea.finedust.controller.DataController;
import com.lthwea.finedust.controller.PrefController;
import com.lthwea.finedust.util.Utils;
import com.lthwea.finedust.vo.MarkerVO;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static com.lthwea.finedust.R.id.map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
//        GoogleMap.OnMapClickListener,
        GoogleMap.OnCameraMoveStartedListener,
//        GoogleMap.OnCameraIdleListener,
//        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnInfoWindowClickListener,
        ClusterManager.OnClusterClickListener<MarkerVO>, ClusterManager.OnClusterInfoWindowClickListener<MarkerVO>, ClusterManager.OnClusterItemClickListener<MarkerVO>,
        ClusterManager.OnClusterItemInfoWindowClickListener<MarkerVO> {


    private GoogleMap mMap;
    private Geocoder geocoder;
    private ClusterManager<MarkerVO> mClusterManager;


    private Marker initMarker;
    private boolean isSettingInitMarker = false;

    private Marker alarmMarker;
    private boolean isSettingAlarmMarker = false;


    private PrefController pref;


    private Toolbar toolbar;

    public static int INTENT_ALARM_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("FineDust");
        setSupportActionBar(toolbar);


        /* 상태바, 타이틀바 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_default_color));
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.color_default_color));
        }



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


        /*navigationView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("navigationView","onclick");

                int size = navigationView.getMenu().size();
                for (int i = 0; i < size; i++) {
                    navigationView.getMenu().getItem(i).setChecked(false);
                }
            }
        });*/

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



       /* for(int i = 0; i < MapConst.testList.size() ; i++){

            String tmp = (String) MapConst.testList.get(i);
            Address adr = getAddress(tmp);
            String[] tmp2 = tmp.split(" ");
            Log.d("getLatLng",  "put(\""+ tmp2[0] + tmp2[1] + "\"," + "new MarkerVO(\"" + tmp2[0] + "\",\"" + tmp2[1] + "\",new LatLng(  " + adr.getLatitude() + "," + adr.getLongitude() + ")) );" );

        }*/


        //Network 사용 모드
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // 초기화면 설정을 위한 pref
        pref = new PrefController(this);
        if(pref.isFirstInitMarker()){
            //처음이면 기본값
            pref.setPrefInitMarker(pref.DEFAULT_LAT, pref.DEFAULT_LNG, pref.DEFAULT_ZOOM);
            //처음인지 아닌지 설정은 한번이라도 설정을 했는지 여부에서 체크한다
        }else{

        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("  MainActivity", "onMapReady: call");

        mMap = googleMap;
        moveCarmeraPrefValue();

        // 구글맵 터치시 발생하는 리스너 등록
//        mMap.setOnMapClickListener(this);
//        mMap.setOnCameraMoveStartedListener(this);
//        mMap.setOnCameraMoveListener(this);
//        mMap.setOnCameraMoveCanceledListener(this);
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

        // for setting init location in google map
        mMap.setOnMarkerDragListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnInfoWindowClickListener(this);

        addItems();
        mClusterManager.cluster();

    }

    private void addItems() {
        DataController dc = new DataController();
        dc.getData();

        Iterator<String> keys = MapConst.markerMap.keySet().iterator();
        Log.e("MapConst.markerMap", "total count : " + MapConst.markerMap.size());
        int errCnt = 0, norCnt = 0;
        while ( keys.hasNext() ) {
            String key = keys.next();
            MarkerVO vo = MapConst.markerMap.get(key);
            if( vo.getPm10Value() == null || vo.getPm10Value().equals("")){
                errCnt++;
                Log.e("MapConst.markerMap", key + " : " + vo.getPm10Value());
            }else {
                mClusterManager.addItem(vo);
                norCnt++;
            }
        }
        Log.e("MapConst.markerMap", "null or empty count : " + errCnt);
        Log.e("MapConst.markerMap", "mClusterManager size : " + norCnt);

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


    //          set Toolbar Title
    @Override
    public void onCameraMoveStarted(int i) {


    }





    /*
                Start Toolbar Search Code
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //search view로 수정
        getMenuInflater().inflate(R.menu.main, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_settings));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("시,군,구,동 검색...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){


            @Override
            public boolean onQueryTextSubmit(String s) {
                if (isSettingInitMarker){
                    stopInitLocationInMap();
                }

                if (isSettingAlarmMarker){
                    stopAlarmMarekrInMap();
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
                if (isSettingInitMarker){
                    stopInitLocationInMap();
                }

                if (isSettingAlarmMarker){
                    stopAlarmMarekrInMap();
                }


                return false;
            }
        });

        return true;
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

        return list.size() > 0 ? list.get(0) : null;
    }

    public void setAddressMarker(Address addr){
        mMap.moveCamera(CameraUpdateFactory.newLatLng( new LatLng(addr.getLatitude(), addr.getLongitude())));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);
        mMap.animateCamera(zoom);

        if(isSettingInitMarker){
            getInitMarker().setPosition(new LatLng(addr.getLatitude(), addr.getLongitude()));
        }
    }


    /**
     *          Navigation Item Event
     *
     * */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.mn_alarm) {

            if(isSettingInitMarker){
                stopInitLocationInMap();
            }

            if (isSettingAlarmMarker){
                stopAlarmMarekrInMap();
            }

            Intent i = new Intent( this, AlarmActivity.class );
            startActivityForResult(i, INTENT_ALARM_CODE);



        } else if (id == R.id.mn_location) {
            if (isSettingAlarmMarker){
                stopAlarmMarekrInMap();
            }

            if( isSettingInitMarker == false){
                isSettingInitMarker = true;
                setInitLocationInMap();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("시작위치 설정중 입니다.");
                builder.setCancelable(true);
                builder.setMessage("시작위치 설정을 취소하시겠습니까?");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopInitLocationInMap();
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();


                Toast.makeText(getApplicationContext(), "실행중 입니다.", Toast.LENGTH_SHORT).show();


            }

        } else if (id == R.id.mn_license) {

            if(isSettingInitMarker){
                stopInitLocationInMap();
            }
            if (isSettingAlarmMarker){
                stopAlarmMarekrInMap();
            }


            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("데이터정보");
            alertDialog.setMessage("데이터출처 : 공공데이터포털");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }/*else if (id == R.id.nav_gallery) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null ){
            Log.d("onActivityResult", "-> " + requestCode + ", " + requestCode + ", " + data.getStringExtra("isSetLocation"));

            if (requestCode == INTENT_ALARM_CODE){
                if("Y".equals(data.getStringExtra("isSetLocation"))){
                    setAlarmMarkerInMap();
                }
            }
        }


    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        if(marker.equals(initMarker)){

            Toast.makeText(MainActivity.this, "initMarker 여기서 뭐하지...", Toast.LENGTH_SHORT).show();

        }else if(marker.equals(alarmMarker)){

            Toast.makeText(MainActivity.this, "alarmMarker 여기서 뭐하지...", Toast.LENGTH_SHORT).show();

        }

    }



    public void setAlarmMarkerInMap(){
        isSettingAlarmMarker = true;

        moveCarmeraPrefValue();

        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(pref.getPrefInitMarkerLat(), pref.getPrefInitMarkerLng()))
                .title("알림 받을 지역을 선택합니다.")
                .snippet("마커를 롱클릭 후 드래그하여 이동해주세요.")
                .draggable(true);

        setAlarmMarker(mMap.addMarker(marker));
        getAlarmMarker().showInfoWindow();


    }



    /**
     *
     *          초기 시작 위치 설정 부분
     *
     * */
    public void setInitLocationInMap(){

        moveCarmeraPrefValue();

        // marker 표시
        // market 의 위치, 타이틀, 짧은설명 추가 가능.
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(pref.getPrefInitMarkerLat(), pref.getPrefInitMarkerLng()))
                .title("앱 실행시 시작위치를 선택합니다.")
                .snippet("마커를 롱클릭 후 드래그하여 이동해주세요.")
                .draggable(true);

        setInitMarker(mMap.addMarker(marker));
        getInitMarker().showInfoWindow();

      /*  mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if(marker.equals(initMarker)){

                    Toast.makeText(MainActivity.this, "여기서 뭐하지...", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    public void stopInitLocationInMap(){
        isSettingInitMarker = false;
        getInitMarker().remove();
    }

    public void stopAlarmMarekrInMap(){
        isSettingAlarmMarker = false;
        getAlarmMarker().remove();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

        LatLng dragPosition = marker.getPosition();
        final double dragLat = dragPosition.latitude;
        final double dragLong = dragPosition.longitude;
        final double zoom = mMap.getCameraPosition().zoom;

        if(marker.equals(initMarker) && isSettingInitMarker == true){

            Log.d("onMarkerDrag", "End"+  dragLat + "," + dragLong + " ," + zoom);

            popInitMarkerDialog(dragLat, dragLong, zoom);




        }else if(marker.equals(alarmMarker) && isSettingAlarmMarker == true){
            final String location = Utils.getNearDistanceLocation(dragLat, dragLong);
            Log.d("onMarkerDragEnd", location + "\t\t ....");

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("알림 위치 설정");
            builder.setCancelable(true);
            builder.setMessage("마커에서 가장 가까운 측정지\n" + location + " 입니다. 알림 설정하시겠습니까?");
            builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "시간과 요일을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    stopAlarmMarekrInMap();

                    Intent i = new Intent(getApplicationContext(), AlarmActivity.class);
                    i.putExtra("ALARM_LOCATION", location);
                    startActivityForResult(i, INTENT_ALARM_CODE);

                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();



        }




    }





    public void popInitMarkerDialog(final Double lat, final Double lng, final Double zoom){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("시작위치 설정");
        builder.setCancelable(true);
        builder.setMessage("현재 위치를 시작위치로 설정하시겠습니까?");
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pref.setPrefInitMarker(lat, lng, zoom);
                pref.setIsFirstInitMarker(false);
                stopInitLocationInMap();
                Toast.makeText(getApplicationContext(), "앱 실행시 시작위치를 변경하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNeutralButton("기본값으로 변경", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "기본값으로 변경하였습니다.", Toast.LENGTH_SHORT).show();
                setPrefInitMarkerDefaultValue();
                moveCarmeraPrefValue();
                stopInitLocationInMap();
            }
        });
        builder.show();

    }







    /*
    *   Pref
    * */
    public void moveCarmeraPrefValue(){
        mMap.moveCamera(CameraUpdateFactory.newLatLng( new LatLng(pref.getPrefInitMarkerLat(),pref.getPrefInitMarkerLng())));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo( (int) pref.getPrefInitMarkerZoom());
        mMap.animateCamera(zoom);
    }

    public void setPrefInitMarkerDefaultValue(){
        pref.setPrefInitMarker(pref.DEFAULT_LAT, pref.DEFAULT_LNG, pref.DEFAULT_ZOOM);
        pref.setIsFirstInitMarker(false);
    }





    /**
     *
     *   Google Map Clustering
     *
     * */
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
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(), (float) Math.floor(mMap.getCameraPosition().zoom + 2)), 300,null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }



    /**
     * Draws profile photos inside markers (using IconGenerator).
     * When there are multiple people in the cluster, draw multiple photos (using MultiDrawable).
     */
    private class MarkerVORenderer extends DefaultClusterRenderer<MarkerVO> {

        private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());

        /*private final ImageView mImageView;
        private final ImageView mClusterImageView;*/

       /* private final TextView mTextView;
        private final TextView mClusterTextView;
*/
        public MarkerVORenderer() {
            super(getApplicationContext(), mMap, mClusterManager);

         /*   View multiProfile = getLayoutInflater().inflate(R.layout.multi_profile, null);
            mClusterIconGenerator.setContentView(multiProfile);
            mClusterTextView = (TextView) multiProfile.findViewById(R.id.tv_map_clustered);
            mTextView = new TextView(getApplicationContext());
            mIconGenerator.setContentView(mTextView);*/

         /*    mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);
            mImageView = new ImageView(getApplicationContext());
           mDimension = (int) getResources().getDimension(R.dimen.custom_profile_image);
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
            int padding = (int) getResources().getDimension(R.dimen.custom_profile_padding);
            mImageView.setPadding(padding, padding, padding, padding);*/

        }

        @Override
        protected void onBeforeClusterItemRendered(MarkerVO vo, MarkerOptions markerOptions) {
            Log.d("singleItem", vo.getSidoName() + " " + vo.getCityName());
            // Draw a single person.
            // Set the info window to show their name.
            //mImageView.setImageResource(R.drawable.ic_menu_camera);
            //Bitmap icon = mIconGenerator.makeIcon();
            //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(vo.getCityName());
            String str = vo.getSidoName() + " " + vo.getCityName() + " " + vo.getPm10Value();
            String val = vo.getPm10Value();

            if(val != null && !"".equals(val)){
                mIconGenerator.setColor(getMarkerColor( Integer.parseInt(val) ));
                //mIconGenerator.setColor(0xE0FFFF);
            }
            //mTextView.setText(str);

            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon(str)))
                    .anchor(mIconGenerator.getAnchorU(), mIconGenerator.getAnchorV());

        }

       @Override
        protected void onBeforeClusterRendered(Cluster<MarkerVO> cluster, MarkerOptions markerOptions) {


            Log.d("onBeforeClusterRendered", cluster.getSize() + "");

           // Draw multiple people.
           // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).

           //Drawable marker;
          /* int ClusterSize = cluster.getSize();

           //marker = getApplication().getResources().getDrawable(R.drawable.ic_menu_camera);
           mClusterIconGenerator.setColor(Color.GREEN);

           LayoutInflater myInflater = (LayoutInflater)getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           View activityView = myInflater.inflate(R.layout.multi_profile, null, false);

           mClusterIconGenerator.setContentView(activityView);
           mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));

           BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(mClusterIconGenerator.makeIcon());
           markerOptions.icon(icon);*/
         /*  List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
           int width = mDimension;
           int height = mDimension;


           MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
           multiDrawable.setBounds(0, 0, width, height);

           mClusterImageView.setImageDrawable(multiDrawable);
           Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
           markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
            */

           int sum = 0;
           int cnt = 0;
           double avg = 0;
           for (MarkerVO v : cluster.getItems()) {
               String val = v.getPm10Value();
               if ( val != null && !"".equals(val)){
                  sum += Integer.parseInt(val);
                   cnt++;
               }
           }
           if(cnt == 0){
               avg = sum / 1;
           }else{
               avg = sum / cnt;
           }

           mClusterIconGenerator.setColor( getMarkerColor( (int) Math.round(avg) ));
           Bitmap icon = mClusterIconGenerator.makeIcon("Avg:" + (int) Math.round(avg));
           markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));

        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 2;

        }
    }

    public int getMarkerColor(int val){
        if( val >= 0 && val <= 30 ){
            return Color.CYAN;
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


    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onCameraMove() {
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


    public Marker getInitMarker() {
        return initMarker;
    }

    public void setInitMarker(Marker initMarker) {
        this.initMarker = initMarker;
    }


    public Marker getAlarmMarker() {
        return alarmMarker;
    }

    public void setAlarmMarker(Marker alarmMarker) {
        this.alarmMarker = alarmMarker;
    }
}

//동 -> 15
//구 -> 12
//시 -> 10
//한반도 전체보기 6~7정도