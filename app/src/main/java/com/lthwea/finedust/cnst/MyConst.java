package com.lthwea.finedust.cnst;

import com.google.android.gms.maps.model.LatLng;
import com.lthwea.finedust.vo.ForecastVO;
import com.lthwea.finedust.vo.IntentVO;
import com.lthwea.finedust.vo.MarkerVO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LeeTaeHun on 2017. 4. 3..
 */

public class MyConst {

    public static String CURRENT_DATA_DATE = "";
    public static int CURRENT_MARKER_NUMBER = 0;

    public static final String ID_ALARM_INTENT_TAG = "ID_ALARM_INTENT_TAG";
    public static final String SIDO_ALARM_INTENT_TAG = "SIDO_ALARM_INTENT_TAG";
    public static final String CITY_ALARM_INTENT_TAG = "CITY_ALARM_INTENT_TAG";
    public static final String DAYS_ALARM_INTENT_TAG = "DAYS_ALARM_INTENT_TAG";

    public static final String DATE_TIME_DETAIL_INTENT_TAG = "DATE_TIME_DETAIL_INTENT_TAG";
    public static final String SIDO_DETAIL_INTENT_TAG = "SIDO_DETAIL_INTENT_TAG";
    public static final String CITY_DETAIL_INTENT_TAG = "CITY_DETAIL_INTENT_TAG";
    public static final String PM10_DETAIL_INTENT_TAG = "PM10_DETAIL_INTENT_TAG";
    public static final String PM25_DETAIL_INTENT_TAG = "PM25_DETAIL_INTENT_TAG";
    public static final String TODAY_VAL_DETAIL_INTENT_TAG = "TODAY_VAL_DETAIL_INTENT_TAG";
    public static final String TODAY_FORE_DETAIL_INTENT_TAG = "TODAY_FORE_DETAIL_INTENT_TAG";
    public static final String TOMW_FORE_DETAIL_INTENT_TAG = "TOMW_FORE_DETAIL_INTENT_TAG";



    public static final String WIDGET_TO_MAIN_INTENT_ACTION = "WIDGET_TO_MAIN_INTENT_ACTION";



    public static int INTENT_DEFAULT_ID = 9999;
    public static boolean[] INTENT_DEFAULT_DAYS = new boolean[]{
            false,false,false,false,false,false,false
    };
    public static IntentVO intentVO = new IntentVO(false, null, false, INTENT_DEFAULT_ID, false, INTENT_DEFAULT_DAYS, false, null);


    public static Map<String, MarkerVO> markerMap = new HashMap<String, MarkerVO>(){
        {

            put("충북단양군",new MarkerVO("충북","단양군",new LatLng( 36.9845473,128.3655186)) );
            put("충북옥천군",new MarkerVO("충북","옥천군",new LatLng(  36.3063646,127.5712809)) );
            put("충북제천시",new MarkerVO("충북","제천시",new LatLng(  37.1325821,128.1909481)) );
            put("충북진천군",new MarkerVO("충북","진천군",new LatLng(  36.8553798,127.4361117)) );
            put("충북청주시",new MarkerVO("충북","청주시",new LatLng(  36.6424341,127.4890319)) );
            put("충북충주시",new MarkerVO("충북","충주시",new LatLng(  36.9910113,127.9259497)) );
            put("부산강서구",new MarkerVO("부산","강서구",new LatLng(  35.2122157,128.9805666)) );
            put("부산금정구",new MarkerVO("부산","금정구",new LatLng(  35.2429921,129.0922773)) );
            put("부산기장군",new MarkerVO("부산","기장군",new LatLng(  35.2445494,129.2221542)) );
            put("부산남구",new MarkerVO("부산","남구",new LatLng(  35.1365596,129.084364)) );
            put("부산동구",new MarkerVO("부산","동구",new LatLng(  35.1390588,129.0569225)) );
            put("부산동래구",new MarkerVO("부산","동래구",new LatLng(  35.2048575,129.0836402)) );
            put("부산부산진구",new MarkerVO("부산","부산진구",new LatLng(  35.1630009,129.0531722)) );
            put("부산북구",new MarkerVO("부산","북구",new LatLng(  35.1971347,128.9903711)) );
            put("부산사상구",new MarkerVO("부산","사상구",new LatLng(  35.1526399,128.9908173)) );
            put("부산사하구",new MarkerVO("부산","사하구",new LatLng(  35.1044223,128.9747081)) );
            put("부산서구",new MarkerVO("부산","서구",new LatLng(  35.09773,129.0241207)) );
            put("부산수영구",new MarkerVO("부산","수영구",new LatLng(  35.145615,129.1130831)) );
            put("부산연제구",new MarkerVO("부산","연제구",new LatLng(  35.1761938,129.0797244)) );
            put("부산영도구",new MarkerVO("부산","영도구",new LatLng(  35.0912316,129.0681448)) );
            put("부산중구",new MarkerVO("부산","중구",new LatLng(  35.1061462,129.0323417)) );
            put("부산해운대구",new MarkerVO("부산","해운대구",new LatLng(  35.1631139,129.1635509)) );
            put("대전대덕구",new MarkerVO("대전","대덕구",new LatLng(  36.3467189,127.4155717)) );
            put("대전동구",new MarkerVO("대전","동구",new LatLng(  36.3269986,127.4326826)) );
            put("대전서구",new MarkerVO("대전","서구",new LatLng(  36.3545224,127.3836025)) );
            put("대전유성구",new MarkerVO("대전","유성구",new LatLng(  36.362216,127.3561329)) );
            put("대전중구",new MarkerVO("대전","중구",new LatLng(  36.3253046,127.4212387)) );
            put("인천강화군",new MarkerVO("인천","강화군",new LatLng(  37.7467263,126.4878731)) );
            put("인천계양구",new MarkerVO("인천","계양구",new LatLng(  37.5369504,126.7377435)) );
            put("인천남구",new MarkerVO("인천","남구",new LatLng(  37.4636808,126.6504771)) );
            put("인천남동구",new MarkerVO("인천","남동구",new LatLng(  37.4469893,126.7319416)) );
            put("인천동구",new MarkerVO("인천","동구",new LatLng(  37.4738184,126.6433385)) );
            put("인천부평구",new MarkerVO("인천","부평구",new LatLng(  37.5069818,126.7217739)) );
            put("인천서구",new MarkerVO("인천","서구",new LatLng(  37.5454212,126.6759723)) );
            put("인천연수구",new MarkerVO("인천","연수구",new LatLng(  37.4094099,126.6783088)) );
            put("인천중구",new MarkerVO("인천","중구",new LatLng(  37.4737341,126.6214796)) );
            put("충남당진군",new MarkerVO("충남","당진군",new LatLng(  36.8936109,126.6283278)) );
            put("충남서산시",new MarkerVO("충남","서산시",new LatLng(  36.7844993,126.4503169)) );
            put("충남아산시",new MarkerVO("충남","아산시",new LatLng(  36.789796,127.0018494)) );
            put("충남천안시",new MarkerVO("충남","천안시",new LatLng(  36.815129,127.1138939)) );
            put("광주광산구",new MarkerVO("광주","광산구",new LatLng(  35.1394638,126.7937183)) );
            put("광주남구",new MarkerVO("광주","남구",new LatLng(  35.1221068,126.9079579)) );
            put("광주동구",new MarkerVO("광주","동구",new LatLng(  35.1461042,126.9231228)) );
            put("광주북구",new MarkerVO("광주","북구",new LatLng(  35.1740167,126.9119842)) );
            put("광주서구",new MarkerVO("광주","서구",new LatLng(  35.1528248,126.8910977)) );
            put("울산남구",new MarkerVO("울산","남구",new LatLng(  35.543683,129.3298017)) );
            put("울산동구",new MarkerVO("울산","동구",new LatLng(  35.5048563,129.4166007)) );
            put("울산북구",new MarkerVO("울산","북구",new LatLng(  35.582734,129.3609087)) );
            put("울산울주군",new MarkerVO("울산","울주군",new LatLng(  35.54,129.2)) );
            put("울산중구",new MarkerVO("울산","중구",new LatLng(  35.5687911,129.3321348)) );
            put("전남광양시",new MarkerVO("전남","광양시",new LatLng(  34.9406968,127.6958882)) );
            put("전남목포시",new MarkerVO("전남","목포시",new LatLng(  34.8118351,126.3921664)) );
            put("전남순천시",new MarkerVO("전남","순천시",new LatLng(  34.950637,127.4872135)) );
            put("전남여수시",new MarkerVO("전남","여수시",new LatLng(  34.7603737,127.6622221)) );
            put("전남영암군",new MarkerVO("전남","영암군",new LatLng(  34.8001685,126.6967918)) );
            put("경남거제시",new MarkerVO("경남","거제시",new LatLng(  34.8806427,128.6210824)) );
            put("경남김해시",new MarkerVO("경남","김해시",new LatLng(  35.2285451,128.8893517)) );
            put("경남사천시",new MarkerVO("경남","사천시",new LatLng(  35.0037788,128.064185)) );
            put("경남양산시",new MarkerVO("경남","양산시",new LatLng(  35.3350072,129.0371689)) );
            put("경남진주시",new MarkerVO("경남","진주시",new LatLng(  35.1799817,128.1076213)) );
            put("경남창원시",new MarkerVO("경남","창원시",new LatLng(  35.2538433,128.6402609)) );
            put("경남하동군",new MarkerVO("경남","하동군",new LatLng(  35.0672108,127.7512687)) );
            put("제주서귀포시",new MarkerVO("제주","서귀포시",new LatLng(  33.2541205,126.560076)) );
            put("제주제주시",new MarkerVO("제주","제주시",new LatLng(  33.4996213,126.5311884)) );
            put("전북고창군",new MarkerVO("전북","고창군",new LatLng(  35.4358216,126.7020806)) );
            put("전북군산시",new MarkerVO("전북","군산시",new LatLng(  35.9676772,126.7366293)) );
            put("전북김제시",new MarkerVO("전북","김제시",new LatLng(  35.8036079,126.8808872)) );
            put("전북남원시",new MarkerVO("전북","남원시",new LatLng(  35.416357,127.3904877)) );
            put("전북부안군",new MarkerVO("전북","부안군",new LatLng(  35.7315661,126.7334651)) );
            put("전북익산시",new MarkerVO("전북","익산시",new LatLng(  35.9482858,126.9575991)) );
            put("전북전주시",new MarkerVO("전북","전주시",new LatLng(  35.8242238,127.1479532)) );
            put("전북정읍시",new MarkerVO("전북","정읍시",new LatLng(  35.5698855,126.8558955)) );
            put("경북경산시",new MarkerVO("경북","경산시",new LatLng(  35.8250555,128.7415441)) );
            put("경북경주시",new MarkerVO("경북","경주시",new LatLng(  35.8561719,129.2247477)) );
            put("경북구미시",new MarkerVO("경북","구미시",new LatLng(  36.119485,128.3445734)) );
            put("경북김천시",new MarkerVO("경북","김천시",new LatLng(  36.1398393,128.1135947)) );
            put("경북안동시",new MarkerVO("경북","안동시",new LatLng(  36.5683543,128.729357)) );
            put("경북영주시",new MarkerVO("경북","영주시",new LatLng(  36.8056858,128.6240551)) );
            put("경북포항시",new MarkerVO("경북","포항시",new LatLng(  36.0190178,129.3434808)) );
            put("강원강릉시",new MarkerVO("강원","강릉시",new LatLng(  37.751853,128.8760574)) );
            put("강원동해시",new MarkerVO("강원","동해시",new LatLng(  37.5247192,129.1142915)) );
            put("강원삼척시",new MarkerVO("강원","삼척시",new LatLng(  37.4498683,129.1652059)) );
            put("강원원주시",new MarkerVO("강원","원주시",new LatLng(  37.3422186,127.9201621)) );
            put("강원춘천시",new MarkerVO("강원","춘천시",new LatLng(  37.8813153,127.7299707)) );
            put("경기가평군",new MarkerVO("경기","가평군",new LatLng(  37.8315403,127.5098827)) );
            put("경기고양시",new MarkerVO("경기","고양시",new LatLng(  37.6583599,126.8320201)) );
            put("경기과천시",new MarkerVO("경기","과천시",new LatLng(  37.429246,126.9874451)) );
            put("경기광명시",new MarkerVO("경기","광명시",new LatLng(  37.4784878,126.8642888)) );
            put("경기광주시",new MarkerVO("경기","광주시",new LatLng(  37.4171413,127.2561413)) );
            put("경기구리시",new MarkerVO("경기","구리시",new LatLng(  37.5943124,127.1295646)) );
            put("경기군포시",new MarkerVO("경기","군포시",new LatLng(  37.3616703,126.9351741)) );
            put("경기김포시",new MarkerVO("경기","김포시",new LatLng(  37.6152464,126.7156325)) );
            put("경기남양주시",new MarkerVO("경기","남양주시",new LatLng(  37.6360028,127.2165279)) );
            put("경기동두천시",new MarkerVO("경기","동두천시",new LatLng(  37.9034112,127.0605075)) );
            put("경기부천시",new MarkerVO("경기","부천시",new LatLng(  37.5034138,126.7660309)) );
            put("경기성남시",new MarkerVO("경기","성남시",new LatLng(  37.4449168,127.1388684)) );
            put("경기수원시",new MarkerVO("경기","수원시",new LatLng(  37.2635727,127.0286009)) );
            put("경기시흥시",new MarkerVO("경기","시흥시",new LatLng(  37.3798877,126.8031025)) );
            put("경기안산시",new MarkerVO("경기","안산시",new LatLng(  37.3218778,126.8308848)) );
            put("경기안성시",new MarkerVO("경기","안성시",new LatLng(  37.0079695,127.2796786)) );
            put("경기안양시",new MarkerVO("경기","안양시",new LatLng(  37.3942527,126.9568209)) );
            put("경기양주시",new MarkerVO("경기","양주시",new LatLng(  37.7852875,127.0458453)) );
            put("경기양평군",new MarkerVO("경기","양평군",new LatLng(  37.4912195,127.4875607)) );
            put("경기여주군",new MarkerVO("경기","여주군",new LatLng(  37.2980237,127.6371628)) );
            put("경기연천군",new MarkerVO("경기","연천군",new LatLng(  38.0964438,127.0748335)) );
            put("경기오산시",new MarkerVO("경기","오산시",new LatLng(  37.1498096,127.0772212)) );
            put("경기용인시",new MarkerVO("경기","용인시",new LatLng(  37.2410864,127.1775537)) );
            put("경기의왕시",new MarkerVO("경기","의왕시",new LatLng(  37.344701,126.9683104)) );
            put("경기의정부시",new MarkerVO("경기","의정부시",new LatLng(  37.738098,127.0336819)) );
            put("경기이천시",new MarkerVO("경기","이천시",new LatLng(  37.2719952,127.4348221)) );
            put("경기파주시",new MarkerVO("경기","파주시",new LatLng(  37.7598688,126.7801781)) );
            put("경기평택시",new MarkerVO("경기","평택시",new LatLng(  36.9921075,127.1129451)) );
            put("경기포천시",new MarkerVO("경기","포천시",new LatLng(  37.8949148,127.2003551)) );
            put("경기하남시",new MarkerVO("경기","하남시",new LatLng(  37.5392646,127.2148919)) );
            put("경기화성시",new MarkerVO("경기","화성시",new LatLng(  37.1994932,126.8311887)) );
            put("대구남구",new MarkerVO("대구","남구",new LatLng(  35.8460224,128.5975291)) );
            put("대구달서구",new MarkerVO("대구","달서구",new LatLng(  35.8298561,128.5327576)) );
            put("대구달성군",new MarkerVO("대구","달성군",new LatLng(  35.7746388,128.4317137)) );
            put("대구동구",new MarkerVO("대구","동구",new LatLng(  35.8866012,128.6353024)) );
            put("대구북구",new MarkerVO("대구","북구",new LatLng(  35.8857114,128.5828073)) );
            put("대구서구",new MarkerVO("대구","서구",new LatLng(  35.8717686,128.559115)) );
            put("대구수성구",new MarkerVO("대구","수성구",new LatLng(  35.8582435,128.6306086)) );
            put("대구중구",new MarkerVO("대구","중구",new LatLng(  35.8693527,128.6061666)) );
            put("서울강남구",new MarkerVO("서울","강남구",new LatLng(  37.5172363,127.0473248)) );
            put("서울강동구",new MarkerVO("서울","강동구",new LatLng(  37.5301251,127.123762)) );
            put("서울강북구",new MarkerVO("서울","강북구",new LatLng(  37.6396099,127.0256575)) );
            put("서울강서구",new MarkerVO("서울","강서구",new LatLng(  37.5509786,126.8495382)) );
            put("서울관악구",new MarkerVO("서울","관악구",new LatLng(  37.4784063,126.9516133)) );
            put("서울광진구",new MarkerVO("서울","광진구",new LatLng(  37.5384843,127.0822938)) );
            put("서울구로구",new MarkerVO("서울","구로구",new LatLng(  37.4954031,126.887369)) );
            put("서울금천구",new MarkerVO("서울","금천구",new LatLng(  37.4518527,126.9020358)) );
            put("서울노원구",new MarkerVO("서울","노원구",new LatLng(  37.6541917,127.056793)) );
            put("서울도봉구",new MarkerVO("서울","도봉구",new LatLng(  37.6687738,127.0470706)) );
            put("서울동대문구",new MarkerVO("서울","동대문구",new LatLng(  37.5743682,127.0400189)) );
            put("서울동작구",new MarkerVO("서울","동작구",new LatLng(  37.512402,126.9392525)) );
            put("서울마포구",new MarkerVO("서울","마포구",new LatLng(  37.5637561,126.9084211)) );
            put("서울서대문구",new MarkerVO("서울","서대문구",new LatLng(  37.5791158,126.9367789)) );
            put("서울서초구",new MarkerVO("서울","서초구",new LatLng(  37.4837121,127.0324112)) );
            put("서울성동구",new MarkerVO("서울","성동구",new LatLng(  37.5633415,127.0371025)) );
            put("서울성북구",new MarkerVO("서울","성북구",new LatLng(  37.589116,127.0182146)) );
            put("서울송파구",new MarkerVO("서울","송파구",new LatLng(  37.5145437,127.1065971)) );
            put("서울양천구",new MarkerVO("서울","양천구",new LatLng(  37.5168721,126.8663985)) );
            put("서울영등포구",new MarkerVO("서울","영등포구",new LatLng(  37.5263715,126.8962283)) );
            put("서울용산구",new MarkerVO("서울","용산구",new LatLng(  37.5384272,126.9654442)) );
            put("서울은평구",new MarkerVO("서울","은평구",new LatLng(  37.6026957,126.9291119)) );
            put("서울종로구",new MarkerVO("서울","종로구",new LatLng(  37.5729503,126.9793579)) );
            put("서울중구",new MarkerVO("서울","중구",new LatLng(  37.5640907,126.9979403)) );
            put("서울중랑구",new MarkerVO("서울","중랑구",new LatLng(  37.6065602,127.0926519)) );
            put("세종세종시",new MarkerVO("세종","세종시",new LatLng(  36.6208498,127.2871603)) );

        }
    };



    public static Map<String, String> PM10_FORECAST_SIDO_MAP = new HashMap<String, String>();
    public static ForecastVO TODAY_FORECAST_VO = new ForecastVO();
    public static ForecastVO TOMORROW_FORECAST_VO = new ForecastVO();




}
