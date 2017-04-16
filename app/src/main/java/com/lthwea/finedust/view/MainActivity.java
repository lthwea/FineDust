package com.lthwea.finedust.view;

import android.Manifest;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
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
import com.lthwea.finedust.widget.WidgetSettingActivity;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static com.lthwea.finedust.R.id.map;
import static com.lthwea.finedust.util.Utils.getNearDistanceLocation;
import static com.lthwea.finedust.util.Utils.getPm10MarkerColor;


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


    private static final String TAG = "MainActivity";


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


    private Marker widgetMarker;
    public static boolean isSettingWidgetMarker = false;
    public static boolean isEndWidgetMarker = false;

    private PrefController pref;

    private Toolbar toolbar;
    private NavigationView navigationView;
    public int actionBarHeight;
    public LinearLayout ll_status;
    TextView mTitle;


    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 세로 고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        setFullAd();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
        }


        Intent widgetIntent = getIntent();
        String s = widgetIntent.getAction();
        Log.d("MainActivity", "onCreate: call" + "\t" + s);
        if(MyConst.WIDGET_TO_MAIN_INTENT_ACTION.equals(widgetIntent.getAction())){
            Intent wIntent = new Intent(this, WidgetSettingActivity.class);
            startActivity(wIntent);
        }

        mTitle = (TextView) toolbar.findViewById(R.id.tv_toolbar_title);
        mTitle.setTextSize(20);
        mTitle.setText("전국 미세먼지 정보");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();



        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        TypedValue tv = new TypedValue();
        actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        // 상태바 툴바 밑으로
        ll_status = (LinearLayout) findViewById(R.id.ll_status);
        ll_status.setPadding(0, actionBarHeight, 0, 0);


        if (isDustType)
            navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setChecked(true);
        else navigationView.getMenu().getItem(0).getSubMenu().getItem(1).setChecked(true);



        if( !Utils.isConnectNetwork(this) ){
            Log.e(TAG, "인터넷 사용 불가");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("알림");
            builder.setCancelable(true);
            builder.setMessage("인터넷을 사용할 수 없습니다. 확인 후 다시 이용해주세요.");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.show();
        }else{


          /*  AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("FFA58C3D70AD52031C762233496B51FE").build();
            mAdView.loadAd(adRequest);*/

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3655876992422407/2505666979");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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


        } // end of Network check

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MainActivity", "onMapReady: call");

        mMap = googleMap;
        mMap.setPadding(0, actionBarHeight + 30, 0, 0);

        //주소로 위도경도 구하기
        geocoder = new Geocoder(this);

        //  xx동수준 줌 레벨 제한
        mMap.setMaxZoomPreference(DEFAULT_MIN_ZOOM_LEVEL);

        // Map 내 불필요한 아이콘 제거
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);


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


        // Google Map current My location Perminssion check
        if (android.os.Build.VERSION.SDK_INT >= M) {
            Log.d("PermissionsResult", "oncreate");
            checkLocationPermission();
        }

        //가장 가까운 지역의 정보로 이동
        LatLng l = moveCameraPostion();

        //가장 가까운 지역 정보 토스트
        //showNearLocationInfoToast(l.latitude, l.longitude);


        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Log.d("onMyLocationButtonClick", "call");
                LatLng l = moveCameraPostion();
                //showNearLocationInfoToast(l.latitude, l.longitude);
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
            dc.getSidoData();
            dc.getForecastData();

            Iterator<String> keys = MyConst.markerMap.keySet().iterator();
            Log.e("MyConst.markerMap", "total count : " + MyConst.markerMap.size());
            int errCnt = 0, norCnt = 0;
            while (keys.hasNext()) {
                String key = keys.next();
                MarkerVO vo = MyConst.markerMap.get(key);
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
            MyConst.CURRENT_DATA_DATE = (String) (MyConst.markerMap.get("서울강남구").getDataTime());
            MyConst.CURRENT_MARKER_NUMBER = norCnt;

    }

    /*
                Start Toolbar Search Code
     * */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //search view로 수정
        getMenuInflater().inflate(R.menu.menu_search_toolbar, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_settings));
        final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

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

                if(isSettingWidgetMarker){
                    stopWidgetLocationInMap();
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


                searchView.setIconified(true);
                searchView.clearFocus();        // hide keyboard
                // collapse the action view
                if (menu != null) {
                    (menu.findItem(R.id.action_settings)).collapseActionView();
                }


                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (isSettingInitMarker) {
                    stopInitLocationInMap();
                }

                if (isSettingAlarmMarker) {
                    stopAlarmMarekrInMap();
                }

                if(isSettingWidgetMarker){
                    stopWidgetLocationInMap();
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

            if(isSettingWidgetMarker){
                stopWidgetLocationInMap();
            }


            MyConst.intentVO.setInitData();
            Intent i = new Intent(this, AlarmListActivity.class);
            startActivity(i);


        } else if (id == R.id.mn_location) {
            if (isSettingAlarmMarker) {
                stopAlarmMarekrInMap();
            }

            if(isSettingWidgetMarker){
                stopWidgetLocationInMap();
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

            if(isSettingWidgetMarker){
                stopWidgetLocationInMap();
            }

            String msg = "전국 " + MyConst.CURRENT_MARKER_NUMBER + "개 시군구\n미세먼지, 초미세먼지 정보\n";
            msg += "기준 : " + MyConst.CURRENT_DATA_DATE + "\n";
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

            if(isSettingWidgetMarker){
                stopWidgetLocationInMap();
            }


            if (id == R.id.mn_dust) {
                if (isDustType == false) {
                    isDustType = true;
                    mTitle.setText("전국 미세먼지 정보");
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
                    mTitle.setText("전국 초미세먼지 정보");
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
        mClusterManager.setRenderer(new MarkerVORenderer());
        mClusterManager.cluster();
    }



    @Override
    public void onInfoWindowClick(Marker marker) {

    }


    public void setAlarmMarkerInMap() {
        isSettingAlarmMarker = true;

        LatLng location = moveCameraPostion();
        MarkerOptions marker = new MarkerOptions();
        marker.position(location)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_map_marker1))
                .title("알람 받을 지역을 선택합니다.")
                .snippet("마커를 롱클릭 후 드래그하여 이동해주세요.")
                .draggable(true);

        setAlarmMarker(mMap.addMarker(marker));
        getAlarmMarker().showInfoWindow();


        if (checkLocationPermission()) {
            popAlarmMarkerDialog(location.latitude, location.longitude);
        }

    }

    public void setWidgetMarkerInMap() {

        LatLng location = moveCameraPostion();
        MarkerOptions marker = new MarkerOptions();
        marker.position(location)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_map_marker1))
                .title("위젯으로 설정할 지역을 선택합니다.")
                .snippet("마커를 롱클릭 후 드래그하여 이동해주세요.")
                .draggable(true);

        widgetMarker = mMap.addMarker(marker);
        widgetMarker.showInfoWindow();


        if (checkLocationPermission()) {
            popWidgetMarkerDialog(location.latitude, location.longitude);
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

    }

    public void stopInitLocationInMap() {
        isSettingInitMarker = false;
        getInitMarker().remove();
    }

    public void stopAlarmMarekrInMap() {
        isSettingAlarmMarker = false;
        getAlarmMarker().remove();
    }
    public void stopWidgetLocationInMap() {
        isSettingWidgetMarker = false;
        widgetMarker.remove();
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
        } else if (marker.equals(widgetMarker) && isSettingWidgetMarker == true) {
            popWidgetMarkerDialog(dragLat, dragLong);
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
                displayAD();
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
        builder.setTitle("알람 위치 설정");
        builder.setCancelable(true);
        builder.setMessage("마커에서 가장 가까운 측정지\n" + location + " 입니다. 알람 설정하시겠습니까?");
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

    public void popWidgetMarkerDialog(final Double lat, final Double lng) {

        final String location = getNearDistanceLocation(lat, lng);
        Log.d("popWidgetMarkerDialog", location + "\t\t ....");

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위젯 지역 설정");
        builder.setCancelable(true);
        builder.setMessage("마커에서 가장 가까운 측정지\n" + location + " 입니다. 위젯 설정하시겠습니까?");
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopWidgetLocationInMap();

                Intent i = new Intent(getApplicationContext(), WidgetSettingActivity.class);
                i.putExtra("LOCATION", location);
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
        Log.d("checkLocationPermission", "call " );
        LatLng userLocation = null;

        if (checkLocationPermission() == true) {

            LocationManager lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);
            Location myLocation = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
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
                if(l != null){
                    userLocation = new LatLng(l.latitude, l.longitude);
                    //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, DEFAULT_MYLOCATION_ZOOM_LEVEL), null);
                    mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(userLocation, DEFAULT_MYLOCATION_ZOOM_LEVEL) );
                }
            } else {
                showToast("현재 위치를 가져올 수 없습니다. \nGPS를 활성화 해주세요.\nGPS가 켜져있다면, 다시 실행해주세요.");
            }

        }


        if (userLocation == null) {
            userLocation = new LatLng(pref.getPrefInitMarkerLat(), pref.getPrefInitMarkerLng());
            /*mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo((int) pref.getPrefInitMarkerZoom());
            mMap.animateCamera(zoom);*/
            mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(userLocation, (int) pref.getPrefInitMarkerZoom()) );

        }

        return userLocation;

    }


    public void setPrefInitMarkerDefaultValue() {
        pref.setPrefInitMarker(pref.DEFAULT_LAT, pref.DEFAULT_LNG, pref.DEFAULT_ZOOM);
        pref.setIsFirstInitMarker(false);
    }


    public void checkIntentData(){
        boolean b = MyConst.intentVO.isAlarmMarker();
        if(b == true){
            setAlarmMarkerInMap();
        }
        MyConst.intentVO.setAlarmMarker(false);


        if(isSettingWidgetMarker){
            setWidgetMarkerInMap();
        }

        if(isEndWidgetMarker){
            isEndWidgetMarker = false;
            this.finish();
        }



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

        public MarkerVORenderer() {
            super(getApplicationContext(), mMap, mClusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(MarkerVO vo, MarkerOptions markerOptions) {
            //Log.d("singleItem", vo.getSidoName() + " " + vo.getCityName());

            String str;

            if (isDustType) {
                str = vo.getCityName() + " " + vo.getPm10Value();
            } else {
                str = vo.getCityName() + " " + vo.getPm25Value();
            }


            String val = isDustType == true ? vo.getPm10Value() : vo.getPm25Value();
            if (val != null && !"".equals(val)) {
                mIconGenerator.setColor(getPm10MarkerColor(getApplicationContext(), Integer.parseInt(val)));
                mIconGenerator.setTextAppearance(R.style.iconGenTextSingle);
            }

            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon(str)))
                    .anchor(mIconGenerator.getAnchorU(), mIconGenerator.getAnchorV());

        }

        @Override
        protected void onBeforeClusterRendered(Cluster<MarkerVO> cluster, MarkerOptions markerOptions) {

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

            mClusterIconGenerator.setColor(Utils.getPm10MarkerColor(getApplicationContext(), (int) Math.round(avg)));
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



    @Override
    public void onClusterInfoWindowClick(Cluster<MarkerVO> cluster) {
        // Does nothing, but you could go to a list of the users.
    }

    @Override
    public boolean onClusterItemClick(MarkerVO item) {
        Log.d("onClusterItemClick", item.getSidoName() + "," + item.getCityName());

        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(MyConst.DATE_TIME_DETAIL_INTENT_TAG, item.getDataTime());
        detailIntent.putExtra(MyConst.SIDO_DETAIL_INTENT_TAG, item.getSidoName());
        detailIntent.putExtra(MyConst.CITY_DETAIL_INTENT_TAG, item.getCityName());
        detailIntent.putExtra(MyConst.PM10_DETAIL_INTENT_TAG, item.getPm10Value());
        detailIntent.putExtra(MyConst.PM25_DETAIL_INTENT_TAG, item.getPm25Value());
        detailIntent.putExtra(MyConst.TODAY_VAL_DETAIL_INTENT_TAG, MyConst.PM10_FORECAST_SIDO_MAP.get(item.getSidoName()));
        detailIntent.putExtra(MyConst.TODAY_FORE_DETAIL_INTENT_TAG, MyConst.TODAY_FORECAST_VO.getInformCause());
        detailIntent.putExtra(MyConst.TOMW_FORE_DETAIL_INTENT_TAG, MyConst.TOMORROW_FORECAST_VO.getInformOverall());
        startActivity(detailIntent);

     /*   String str = item.getSidoName() + " " + item.getCityName() + " 상세정보\n";
        str += "미세먼지 : " + item.getPm10Value() + "(" + Utils.getPm10ValueStatus(item.getPm10Value()) + ")\n";
        str += "초미세먼지 : " + item.getPm25Value() + "(" + Utils.getPm25ValueStatus(item.getPm25Value()) + ")\n";
        str += "기준 : " + item.getDataTime();
        str += MyConst.TODAY_FORECAST_VO.getDataTime() + "기준 미세먼지 예보";
        str += item.getSidoName() + ":"+ MyConst.PM10_FORECAST_SIDO_MAP.get(item.getSidoName());
        str += "\n오늘 : " + MyConst.TODAY_FORECAST_VO.getInformCause();
        str += "내일 : " +MyConst.TOMORROW_FORECAST_VO.getInformOverall();
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
        */

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

    InterstitialAd interstitialAd;
    private void setFullAd(){
        interstitialAd = new InterstitialAd(this); //새 광고를 만듭니다.
        interstitialAd.setAdUnitId(getResources().getString(R.string.full_ad_unit_id)); //이전에 String에 저장해 두었던 광고 ID를 전면 광고에 설정합니다.
        AdRequest adRequest1 = new AdRequest.Builder().build(); //새 광고요청
        interstitialAd.loadAd(adRequest1); //요청한 광고를 load 합니다.
        interstitialAd.setAdListener(new AdListener() { //전면 광고의 상태를 확인하는 리스너 등록

            @Override
            public void onAdClosed() { //전면 광고가 열린 뒤에 닫혔을 때
                AdRequest adRequest1 = new AdRequest.Builder().build();  //새 광고요청
                interstitialAd.loadAd(adRequest1); //요청한 광고를 load 합니다.
            }
        });
    }


    public void displayAD() {

        if (interstitialAd.isLoaded()) { //광고가 로드 되었을 시
            interstitialAd.show(); //보여준다
        }
    }

}




//동 -> 15
//구 -> 12
//시 -> 10
//한반도 전체보기 6~7정도