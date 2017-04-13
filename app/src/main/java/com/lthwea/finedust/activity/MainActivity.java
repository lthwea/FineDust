package com.lthwea.finedust.activity;

import android.Manifest;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.lthwea.finedust.cnst.MyConst;
import com.lthwea.finedust.controller.DataController;
import com.lthwea.finedust.controller.PrefController;
import com.lthwea.finedust.util.Utils;
import com.lthwea.finedust.vo.MarkerVO;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static com.lthwea.finedust.R.id.map;
import static com.lthwea.finedust.util.Utils.getNearDistanceLocation;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
//        GoogleMap.OnMapClickListener,
//        GoogleMap.OnCameraMoveStartedListener,
//        GoogleMap.OnCameraIdleListener,
//        GoogleMap.OnCameraMoveCanceledListener,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        LocationListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnInfoWindowClickListener,
        ClusterManager.OnClusterClickListener<MarkerVO>, ClusterManager.OnClusterInfoWindowClickListener<MarkerVO>, ClusterManager.OnClusterItemClickListener<MarkerVO>,
        ClusterManager.OnClusterItemInfoWindowClickListener<MarkerVO> {


    public boolean isDustType = true;   // ture:미세먼지정보,  false:초미세먼지정보


    private GoogleMap mMap;
    private Geocoder geocoder;
    private ClusterManager<MarkerVO> mClusterManager;
    private int DEFAULT_MIN_ZOOM_LEVEL = 12;
    private int DEFAULT_MYLOCATION_ZOOM_LEVEL = 13;

    private Marker initMarker;
    private boolean isSettingInitMarker = false;
    private Marker alarmMarker;
    private boolean isSettingAlarmMarker = false;


    private PrefController pref;


    private Toolbar toolbar;
    private NavigationView navigationView;
    public int actionBarHeight;
    public LinearLayout ll_status;

   // private boolean IS_GPS_USE = false;

//    private long APP_RUN_TIME_MILLIS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("전국 미세먼지 정보");
        ;
        setSupportActionBar(toolbar);


        TypedValue tv = new TypedValue();
        actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        // 상태바 툴바 밑으로
        ll_status = (LinearLayout) findViewById(R.id.ll_status);
        ll_status.setPadding(0, actionBarHeight, 0, 0);


        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-3655876992422407~1028933775");
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


        Log.d("MainActivity", "onCreate: call");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (isDustType)
            navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setChecked(true);
        else navigationView.getMenu().getItem(0).getSubMenu().getItem(1).setChecked(true);


        // firebase FCM
       /* FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();*/


        // Google Map Setting
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(getApplicationContext(), "ERROR : mapFragment is null", Toast.LENGTH_SHORT).show();
        }


        //Network 사용 모드
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // 초기화면 설정을 위한 pref
        pref = new PrefController(this);
        if (pref.isFirstInitMarker()) {
            //처음이면 기본값
            pref.setPrefInitMarker(pref.DEFAULT_LAT, pref.DEFAULT_LNG, pref.DEFAULT_ZOOM);
            //처음인지 아닌지 설정은 한번이라도 설정을 했는지 여부에서 체크한다
        } else {

        }




       /* for(int i = 0; i < MyConst.testList.size() ; i++){

            String tmp = (String) MyConst.testList.get(i);
            Address adr = getAddress(tmp);
            String[] tmp2 = tmp.split(" ");
            Log.d("getLatLng",  "put(\""+ tmp2[0] + tmp2[1] + "\"," + "new MarkerVO(\"" + tmp2[0] + "\",\"" + tmp2[1] + "\",new LatLng(  " + adr.getLatitude() + "," + adr.getLongitude() + ")) );" );

        }*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("  MainActivity", "onMapReady: call");

        mMap = googleMap;
        mMap.setPadding(0, actionBarHeight + 30, 0, 0);

        //주소로 위도경도 구하기
        geocoder = new Geocoder(this);

        //  xx동수준 줌 레벨 제한
        mMap.setMaxZoomPreference(DEFAULT_MIN_ZOOM_LEVEL);
        // disable rotation
        mMap.getUiSettings().setRotateGesturesEnabled(false);


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
                Log.d("onCameraIdle", "aa");
                CameraPosition position = mMap.getCameraPosition();
                if (mPreviousCameraPosition[0] == null || mPreviousCameraPosition[0].zoom != position.zoom) {
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
//        mMap.setOnCameraMoveListener(this);
//        mMap.setOnInfoWindowClickListener(this);

        addItems();
        mClusterManager.cluster();


        /*if (android.os.Build.VERSION.SDK_INT >= M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                setMyLocationEnabled(true);
                Log.d("checkLocationPermission", "onMapReady");
            }
        } else {
            //Not in api-23, no need to prompt
            setMyLocationEnabled(true);
        }*/

        // Google Map current My location Perminssion check
        if (android.os.Build.VERSION.SDK_INT >= M) {
            Log.d("PermissionsResult", "oncreate");
            checkLocationPermission();
        }

        //가장 가까운 지역의 정보로 이동
        LatLng l = moveCameraPostion();

        //가장 가까운 지역 정보 토스트
        showNearLocationInfoToast(l.latitude, l.longitude);


        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Log.d("onMyLocationButtonClick", "call");
                LatLng l = moveCameraPostion();
                //가장 가까운 지역 정보 토스트
                showNearLocationInfoToast(l.latitude, l.longitude);
                return true;
            }
        });

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("onRestart", "onRestart");

        checkIntentData();

    }


    private void addItems() {
        DataController dc = new DataController();
        dc.getData();

        Iterator<String> keys = com.lthwea.finedust.cnst.MyConst.markerMap.keySet().iterator();
        Log.e("MyConst.markerMap", "total count : " + com.lthwea.finedust.cnst.MyConst.markerMap.size());
        int errCnt = 0, norCnt = 0;
        while (keys.hasNext()) {
            String key = keys.next();
            MarkerVO vo = com.lthwea.finedust.cnst.MyConst.markerMap.get(key);
            if (vo.getPm10Value() == null || vo.getPm10Value().equals("")) {
                errCnt++;
                Log.e("MyConst.markerMap", key + " : " + vo.getPm10Value());
            } else {
                mClusterManager.addItem(vo);
                norCnt++;
            }
        }
        Log.e("MyConst.markerMap", "null or empty count : " + errCnt);
        Log.e("MyConst.markerMap", "mClusterManager size : " + norCnt);
        com.lthwea.finedust.cnst.MyConst.CURRENT_DATA_DATE = (String) (com.lthwea.finedust.cnst.MyConst.markerMap.get("서울강남구").getDataTime());
        com.lthwea.finedust.cnst.MyConst.CURRENT_MARKER_NUMBER = norCnt;

    }


    // show near location info toast
    public void showNearLocationInfoToast(double lat, double lng) {
        String msg = Utils.getNearDistanceInfo(lat, lng);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    /*
                Start Toolbar Search Code
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //search view로 수정
        getMenuInflater().inflate(R.menu.menu_search_toolbar, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_settings));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("시,군,구,동 검색...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                if (isSettingInitMarker) {
                    stopInitLocationInMap();
                }

                if (isSettingAlarmMarker) {
                    stopAlarmMarekrInMap();
                }


                Address addr = getAddress(s);
                if (addr != null) {
                    if ("대한민국".equals(addr.getCountryName())) {
                        setAddressMarker(addr);
                    } else {
                        Toast.makeText(getApplicationContext(), "주소가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "주소가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (isSettingInitMarker) {
                    stopInitLocationInMap();
                }

                if (isSettingAlarmMarker) {
                    stopAlarmMarekrInMap();
                }


                return false;
            }
        });

        return true;
    }


    public Address getAddress(String addr) {
        List<Address> list = null;

        try {
            list = geocoder.getFromLocationName(
                    addr, // 지역 이름
                    1); // 읽을 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("getLatLng", "입출력 오류 - 서버에서 주소변환시 에러발생");
        }

        return list.size() > 0 ? list.get(0) : null;
    }


    public void setAddressMarker(Address addr) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(addr.getLatitude(), addr.getLongitude())));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(DEFAULT_MIN_ZOOM_LEVEL);
        mMap.animateCamera(zoom);
    }


    /**
     * Navigation Item Event
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.mn_alarm) {

            if (isSettingInitMarker) {
                stopInitLocationInMap();
            }

            if (isSettingAlarmMarker) {
                stopAlarmMarekrInMap();
            }


            com.lthwea.finedust.cnst.MyConst.intentVO.setInitData();
            Intent i = new Intent(this, AlarmListActivity.class);
            startActivity(i);


        } else if (id == R.id.mn_location) {
            if (isSettingAlarmMarker) {
                stopAlarmMarekrInMap();
            }

            if (isSettingInitMarker == false) {
                isSettingInitMarker = true;
                setInitLocationInMap();
            } else {
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


                //Toast.makeText(getApplicationContext(), "실행중 입니다.", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.mn_license) {

            if (isSettingInitMarker) {
                stopInitLocationInMap();
            }
            if (isSettingAlarmMarker) {
                stopAlarmMarekrInMap();
            }


            String msg = "전국 " + com.lthwea.finedust.cnst.MyConst.CURRENT_MARKER_NUMBER + "개 시군구\n미세먼지, 초미세먼지 정보\n";
            msg += "기준 : " + com.lthwea.finedust.cnst.MyConst.CURRENT_DATA_DATE + "\n";
            msg += "제공 : 공공데이터포털";

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("데이터 정보");
            builder.setCancelable(true);
            builder.setMessage(msg);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();


        } else if (id == R.id.mn_dust || id == R.id.mn_su_dust) {
            if (isSettingAlarmMarker) {
                stopAlarmMarekrInMap();
            }

            if (isSettingInitMarker) {
                stopInitLocationInMap();
            }

            if (id == R.id.mn_dust) {
                if (isDustType == false) {
                    isDustType = true;
                    toolbar.setTitle("전국 미세먼지 정보");
                    showToast("전국 미세먼지 정보입니다.");
                    updateClusterManager();
                    changeTextViewStatus();
                    navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setChecked(true);
                    navigationView.getMenu().getItem(0).getSubMenu().getItem(1).setChecked(false);
                } else {
                    showToast("미세먼지 데이터 사용중 입니다.");
                }

            } else if (id == R.id.mn_su_dust) {
                if (isDustType == true) {
                    isDustType = false;
                    toolbar.setTitle("전국 초미세먼지 정보");
                    showToast("전국 초미세먼지 정보입니다.");
                    updateClusterManager();
                    changeTextViewStatus();
                    navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setChecked(false);
                    navigationView.getMenu().getItem(0).getSubMenu().getItem(1).setChecked(true);
                } else {
                    showToast("초미세먼지 데이터 사용중 입니다.");
                }

            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;

    }

    public void updateClusterManager() {

        mClusterManager.clearItems();
        mMap.clear();
        addItems();
        mClusterManager.cluster();

        LatLng defaultLatLng = new LatLng(pref.DEFAULT_LAT, pref.DEFAULT_LNG);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLatLng));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo((float) 5.5);
        mMap.animateCamera(zoom);



        /*double zoomLevel = 0;
        if( mMap.getCameraPosition().zoom + 2 >= DEFAULT_MIN_ZOOM_LEVEL){
            zoomLevel = mMap.getCameraPosition().zoom - 2;
        }else{
            zoomLevel = mMap.getCameraPosition().zoom + 2;
        }
        CameraUpdate zoom = CameraUpdateFactory.zoomTo( (float) zoomLevel);
        mMap.animateCamera(zoom);*/
    }


/*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "MAIN " + requestCode + " " +  resultCode);

        //0 3
        //1 4
        if ((requestCode == MyConst.I_MAIN_TO_LIST_REQ_CODE && resultCode == MyConst.I_LIST_TO_MAIN_RES_CODE) ||
                (requestCode == MyConst.I_MAIN_TO_ALARM_REQ_CODE && resultCode == MyConst.I_ALARM_TO_MAIN_RES_CODE) ||
                (requestCode == MyConst.I_MAIN_TO_ALARM_REQ_CODE && resultCode == MyConst.I_MAIN_TO_LIST_REQ_CODE)) {
            {
                if (data != null) {
                    if ("Y".equals(data.getStringExtra(MyConst.ALARM_IS_SET_LOCATION_TAG))) {
                        setAlarmMarkerInMap();
                    }
                }

            }
        }
    }
*/




    /*public void checkAlarmIntentData(){
        Intent intent = getIntent();
        String isSetAlarmLocation = intent.getStringExtra(MyConst.ALARM_IS_SET_LOCATION_TAG);
        if("Y".equals(isSetAlarmLocation)){
            setAlarmMarkerInMap();
        }
    }*/

    @Override
    public void onInfoWindowClick(Marker marker) {

//        if (marker.equals(initMarker)) {
//
//            Toast.makeText(MainActivity.this, "initMarker 여기서 뭐하지...", Toast.LENGTH_SHORT).show();
//
//        } else if (marker.equals(alarmMarker)) {
//
//            Toast.makeText(MainActivity.this, "alarmMarker 여기서 뭐하지...", Toast.LENGTH_SHORT).show();
//
//        }

    }


    public void setAlarmMarkerInMap() {
        isSettingAlarmMarker = true;

        LatLng location = moveCameraPostion();
        MarkerOptions marker = new MarkerOptions();
        marker.position(location)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_map_marker1))
                .title("알림 받을 지역을 선택합니다.")
                .snippet("마커를 롱클릭 후 드래그하여 이동해주세요.")
                .draggable(true);

        setAlarmMarker(mMap.addMarker(marker));
        getAlarmMarker().showInfoWindow();


        if (checkLocationPermission()) {
            popAlarmMarkerDialog(location.latitude, location.longitude);
        }

    }


    /**
     * 초기 시작 위치 설정 부분
     */
    public void setInitLocationInMap() {

        LatLng location = moveCameraPostion();

        // marker 표시
        // market 의 위치, 타이틀, 짧은설명 추가 가능.
        MarkerOptions marker = new MarkerOptions();
        marker.position(location)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_map_marker1))
                .title("앱 실행시 시작위치를 선택합니다.")
                .snippet("마커를 롱클릭 후 드래그하여 이동해주세요.")
                .draggable(true);

        setInitMarker(mMap.addMarker(marker));
        getInitMarker().showInfoWindow();

        if (checkLocationPermission()) {
            popInitMarkerDialog(location.latitude, location.longitude, (double) DEFAULT_MIN_ZOOM_LEVEL);
        }

      /*  mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if(marker.equals(initMarker)){

                    Toast.makeText(MainActivity.this, "여기서 뭐하지...", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    public void stopInitLocationInMap() {
        isSettingInitMarker = false;
        getInitMarker().remove();
    }

    public void stopAlarmMarekrInMap() {
        isSettingAlarmMarker = false;
        getAlarmMarker().remove();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.d("onMarkerDrag", "call");

        LatLng dragPosition = marker.getPosition();
        final double dragLat = dragPosition.latitude;
        final double dragLong = dragPosition.longitude;
        final double zoom = mMap.getCameraPosition().zoom;

        if (marker.equals(initMarker) && isSettingInitMarker == true) {

            Log.d("onMarkerDrag", "End" + dragLat + "," + dragLong + " ," + zoom);
            popInitMarkerDialog(dragLat, dragLong, zoom);
        } else if (marker.equals(alarmMarker) && isSettingAlarmMarker == true) {
            popAlarmMarkerDialog(dragLat, dragLong);
        }


    }


    public void popInitMarkerDialog(final Double lat, final Double lng, final Double zoom) {

        String loc = Utils.getNearDistanceLocation(lat, lng);
        String msg = "현재 위치에서 가장 가까운 측정지인\n" + loc + "를\n시작위치로 설정하시겠습니까?";


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("시작위치 설정");
        builder.setCancelable(true);
        builder.setMessage(msg);
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
                moveCameraPostion();
                stopInitLocationInMap();
            }
        });
        builder.show();

    }


    public void popAlarmMarkerDialog(final Double lat, final Double lng) {

        final String location = getNearDistanceLocation(lat, lng);
        Log.d("popAlarmMarkerDialog", location + "\t\t ....");

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("알림 위치 설정");
        builder.setCancelable(true);
        builder.setMessage("마커에서 가장 가까운 측정지\n" + location + " 입니다. 알림 설정하시겠습니까?");
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "시간과 요일을 선택해주세요.", Toast.LENGTH_SHORT).show();
                stopAlarmMarekrInMap();

                MyConst.intentVO.setLocName(location);
                Intent i = new Intent(getApplicationContext(), AlarmListActivity.class);
                startActivity(i);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }


    /*
    *   Pref
    * */
    public LatLng moveCameraPostion() {
        int i = 0;
        Log.d("checkLocationPermission", ++i + "번째");
        LatLng userLocation = null;

        if (checkLocationPermission() == true) {

            //Toast.makeText(this, "현재 위치에서 가장 가까운 측정지로 이동합니다.", Toast.LENGTH_SHORT).show();


            LocationManager lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);
            Location myLocation = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            //
            //Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = lm.getBestProvider(criteria, false);
                myLocation = lm.getLastKnownLocation(provider);
            }

            Log.d("onMyLocationButtonClick", "call  " + myLocation);

            if (myLocation != null) {
                LatLng l = Utils.getNearDistanceLatLng(myLocation.getLatitude(), myLocation.getLongitude());
                userLocation = new LatLng(l.latitude, l.longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, DEFAULT_MYLOCATION_ZOOM_LEVEL), null);
            } else {
                showToast("현재 위치를 가져올 수 없습니다. \nGPS를 활성화 해주세요.\nGPS가 켜져있다면, 다시 실행해주세요.");
            }

        }


        if (userLocation == null) {
            userLocation = new LatLng(pref.getPrefInitMarkerLat(), pref.getPrefInitMarkerLng());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo((int) pref.getPrefInitMarkerZoom());
            mMap.animateCamera(zoom);

        }

        return userLocation;

    }


    public void setPrefInitMarkerDefaultValue() {
        pref.setPrefInitMarker(pref.DEFAULT_LAT, pref.DEFAULT_LNG, pref.DEFAULT_ZOOM);
        pref.setIsFirstInitMarker(false);
    }


    public void checkIntentData(){
        boolean b = com.lthwea.finedust.cnst.MyConst.intentVO.isAlarmMarker();
        if(b == true){
            setAlarmMarkerInMap();
        }
        com.lthwea.finedust.cnst.MyConst.intentVO.setAlarmMarker(false);
    }


    /**
     * Google Map Clustering
     */
    @Override
    public boolean onClusterClick(Cluster<MarkerVO> cluster) {
        Log.d("onClusterClick", cluster.getSize() + "");

        // Show a toast with some info when the cluster is clicked.
        //String firstName = cluster.getItems().iterator().next().getCityName();
        Toast.makeText(this, cluster.getSize() + "개 지역의 평균입니다. 클릭시 확대 됩니다.", Toast.LENGTH_SHORT).show();

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
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cluster.getPosition(), (float) Math.floor(mMap.getCameraPosition().zoom + 2)), 300, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    /**
     * Draws profile photos inside markers (using IconGenerator).
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
            //Log.d("singleItem", vo.getSidoName() + " " + vo.getCityName());
            // Draw a single person.
            // Set the info window to show their name.
            //mImageView.setImageResource(R.drawable.ic_menu_camera);
            //Bitmap icon = mIconGenerator.makeIcon();
            //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(vo.getCityName());

            // String str = vo.getSidoName() + " " + vo.getCityName() + " " + vo.getPm10Value();
            String str;

            if (isDustType) {
                str = vo.getCityName() + " " + vo.getPm10Value();
            } else {
                str = vo.getCityName() + " " + vo.getPm25Value();
            }


            String val = isDustType == true ? vo.getPm10Value() : vo.getPm25Value();
            if (val != null && !"".equals(val)) {
                mIconGenerator.setColor(getMarkerColor(Integer.parseInt(val)));
                mIconGenerator.setTextAppearance(R.style.iconGenTextSingle);
            }

            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon(str)))
                    .anchor(mIconGenerator.getAnchorU(), mIconGenerator.getAnchorV());

        }

        @Override
        protected void onBeforeClusterRendered(Cluster<MarkerVO> cluster, MarkerOptions markerOptions) {


            //Log.d("onBeforeClusterRendered", cluster.getSize() + "");

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


            int sum = 0;
            int cnt = 0;
            double avg = 0;
            for (MarkerVO v : cluster.getItems()) {
                String val = isDustType == true ? v.getPm10Value() : v.getPm25Value();
                if (val != null && !"".equals(val)) {
                    sum += Integer.parseInt(val);
                    cnt++;
                }
            }
            if (cnt == 0) {
                avg = sum / 1;
            } else {
                avg = sum / cnt;
            }

            mClusterIconGenerator.setColor(getMarkerColor((int) Math.round(avg)));
            mClusterIconGenerator.setTextAppearance(R.style.iconGenTextCluster);
            Bitmap icon = mClusterIconGenerator.makeIcon((int) Math.round(avg) + "");
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));

        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 2;

        }

    }

    public int getMarkerColor(int val) {
        if (val >= 0 && val <= 30) {
            //return this.getColor(R.color.color_dust_2);
            return ContextCompat.getColor(this, R.color.color_dust_1);
        } else if (val >= 31 && val <= 80) {
            return ContextCompat.getColor(this, R.color.color_dust_2);
        } else if (val >= 81 && val <= 150) {
            return ContextCompat.getColor(this, R.color.color_dust_3);
        } else if (val >= 151) {
            return ContextCompat.getColor(this, R.color.color_dust_4);
        } else {
            return Color.WHITE;
        }
    }


    @Override
    public void onClusterInfoWindowClick(Cluster<MarkerVO> cluster) {
        // Does nothing, but you could go to a list of the users.
    }

    @Override
    public boolean onClusterItemClick(MarkerVO item) {
        Log.d("onClusterItemClick", item.getSidoName() + "," + item.getCityName());

        String str = item.getSidoName() + " " + item.getCityName() + " 상세정보\n";
        str += "미세먼지 : " + item.getPm10Value() + "(" + Utils.getPm10ValueStatus(item.getPm10Value()) + ")\n";
        str += "초미세먼지 : " + item.getPm25Value() + "(" + Utils.getPm25ValueStatus(item.getPm25Value()) + ")\n";
        str += "기준 : " + item.getDataTime();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

        return false;
    }


    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 버튼 입력시 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }

//    private final long BASE_DATA_UPDATE_TIME = 1000 * 60 * 60;    //1시간
//    public void checkAppRunTimeMills(){
//        long tempTime = System.currentTimeMillis();
//        long intervalTime = tempTime - APP_RUN_TIME_MILLIS;
//
//        if (0 <= intervalTime && BASE_DATA_UPDATE_TIME >= intervalTime) {     //지남
//
//        } else {        //안지남
//
//        }
//
//    }
//    @Override
//    public void onCameraMoveStarted(int i) {
//
//    }

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



    // gps 권한 동의
    // https://developers.google.com/android/guides/permissions
    public boolean checkLocationPermission() {
       /* Log.d("checkLocationPermission", "checkLocationPermission");
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else {
            return true;
        }*/

        Log.d("PermissionsResult", "checkLocationPermission");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
            Log.d("PermissionsResult", "false" );
            return false;
        } else {
            // permission has been granted, continue as usual
            Log.d("PermissionsResult", "true mMap : " + mMap);
            if(mMap != null){
                mMap.setMyLocationEnabled(true);
            }
            return true;
        }


    }

    private static final int REQUEST_LOCATION = 2;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("PermissionsResult", requestCode + " ");

        /*switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    Log.d("checkLocationPermission", "onRequestPermissionsResult");
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "위치 권한 동의를 거부하였습니다.", Toast.LENGTH_LONG).show();
                    Log.d("checkLocationPermission", "위치 권한 동의를 거부하였습니다.");
                }
                return;
            }
        }*/

        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
                // permission has been granted, continue as usual
                Log.d("PermissionsResult", "권한동의");
                moveCameraPostion();
            } else {
                // Permission was denied or request was cancelled
                Log.d("PermissionsResult", "권한동의실");

            }
        }else{

        }

    }




    public void changeTextViewStatus(){
        TextView tv1 = (TextView) findViewById(R.id.tv_status1);
        TextView tv2 = (TextView) findViewById(R.id.tv_status2);
        TextView tv3 = (TextView) findViewById(R.id.tv_status3);
        TextView tv4 = (TextView) findViewById(R.id.tv_status4);

        if(isDustType){
            tv1.setText("0~30(좋음)");
            tv2.setText("31~80(보통)");
            tv3.setText("81~150(나쁨)");
            tv4.setText("151~(매우나쁨)");
        }else{
            tv1.setText("0~15(좋음)");
            tv2.setText("16~50(보통)");
            tv3.setText("51~100(나쁨)");
            tv4.setText("100~(매우나쁨)");
        }

    }

    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

//동 -> 15
//구 -> 12
//시 -> 10
//한반도 전체보기 6~7정도