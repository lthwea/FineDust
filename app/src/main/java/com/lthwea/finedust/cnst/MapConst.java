package com.lthwea.finedust.cnst;

import com.google.android.gms.maps.model.LatLng;
import com.lthwea.finedust.vo.IntentVO;
import com.lthwea.finedust.vo.MarkerVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LeeTaeHun on 2017. 4. 3..
 */

public class MapConst {

    public static String CURRENT_DATA_DATE = "";
    public static int CURRENT_MARKER_NUMBER = 0;


    public static String ALARM_ID_FOR_UPDATE_TAG = "ALARM_ID_FOR_UPDATE";       // list -> activity 로 수정시 db id값을 넘겨주기 위함
    public static String ALARM_IS_UPDATE_TAG = "ALARM_IS_UPDATE";               // list -> activity 로 수정시 db id값을 넘겨주기 위함

    public static String ALARM_IS_CHANGE_TAG = "ALARM IS_CHANGE";               // alarm -> list 데이터 변경을 알려주어 listview update 처리 하기 위함


    public static String ALARM_IS_SET_LOCATION_TAG = "ALARM_IS_SET_LOCATION";   // set -> main 으로 업데이트 한다는것을 알려주기 위함
    public static String ALARM_LOCATION_TAG = "ALARM_LOCATION";   // main -> set  설정된 지역정보를 넘김


    // intent request code
  /*  public static int ALARM_TO_MAIN_REQ_CODE_INTENT = 0;
    public static int LIST_TO_ALARM_REQ_CODE_INTENT = 1;
    public static int MAIN_TO_LIST_REQ_CODE_INTENT = 2;*/


    public static int I_MAIN_TO_LIST_REQ_CODE =  0;
    public static int I_MAIN_TO_ALARM_REQ_CODE = 1;
    public static int I_LIST_TO_ALARM_REQ_CODE = 2;

    public static int I_LIST_TO_MAIN_RES_CODE =  3;
    public static int I_ALARM_TO_MAIN_RES_CODE = 4;
    public static int I_ALARM_TO_LIST_RES_CODE = 5;






    //public static int LIST_TO_MAIN_REQ_CODE_INTENT = 3;

    /*
            04-03 16:27:08.755 12091-12091/com.lthwea.finedust D/defaultInfo: 경상남도	35.4606,128.2132
            04-03 16:27:09.383 12091-12091/com.lthwea.finedust D/defaultInfo: 부산광역시	35.1795543,129.0756416
            04-03 16:27:09.561 12091-12091/com.lthwea.finedust D/defaultInfo: 대전광역시	36.3504119,127.3845475
            04-03 16:27:09.744 12091-12091/com.lthwea.finedust D/defaultInfo: 전라남도	34.8679,126.991
            04-03 16:27:10.309 12091-12091/com.lthwea.finedust D/defaultInfo: 경상북도	36.4919,128.8889
            04-03 16:27:10.483 12091-12091/com.lthwea.finedust D/defaultInfo: 제주특별자치도	33.4996213,126.5311884
            04-03 16:27:10.666 12091-12091/com.lthwea.finedust D/defaultInfo: 강원도	37.8228,128.1555
            04-03 16:27:11.040 12091-12091/com.lthwea.finedust D/defaultInfo: 전라북도	35.7175,127.153
            04-03 16:27:11.552 12091-12091/com.lthwea.finedust D/defaultInfo: 광주광역시	35.1595454,126.8526012
            04-03 16:27:12.117 12091-12091/com.lthwea.finedust D/defaultInfo: 대구광역시	35.8714354,128.601445
            04-03 16:27:12.508 12091-12091/com.lthwea.finedust D/defaultInfo: 충청남도	36.5184,126.8
            04-03 16:27:13.107 12091-12091/com.lthwea.finedust D/defaultInfo: 충청북도	36.8,127.7
            04-03 16:27:13.505 12091-12091/com.lthwea.finedust D/defaultInfo: 울산광역시	35.5383773,129.3113596
            04-03 16:27:13.706 12091-12091/com.lthwea.finedust D/defaultInfo: 경기도	37.4138,127.5183
            04-03 16:27:14.117 12091-12091/com.lthwea.finedust D/defaultInfo: 서울특별시	37.566535,126.9779692
            04-03 16:27:14.327 12091-12091/com.lthwea.finedust D/defaultInfo: 인천광역시	37.4562557,126.7052062
*/

    public static int INTENT_DEFAULT_ID = 9999;
    public static boolean[] INTENT_DEFAULT_DAYS = new boolean[]{
            false,false,false,false,false,false,false
    };
    public static IntentVO intentVO = new IntentVO(false, null, false, INTENT_DEFAULT_ID, false, INTENT_DEFAULT_DAYS, false);


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

/*

    public static List<MarkerVO> markerList = new ArrayList<MarkerVO>(){
        {
            add( new MarkerVO("서울","도봉구",new LatLng(37.6658609,127.0317674)) );
            add( new MarkerVO("서울","은평구",new LatLng(37.6176125,126.9227004)) );
            add( new MarkerVO("서울","동대문구",new LatLng(37.5838012,127.0507003)) );
            add( new MarkerVO("서울","동작구",new LatLng(37.4965037,126.9443073)) );
            add( new MarkerVO("서울","금천구",new LatLng(37.4600969,126.9001546)) );
            add( new MarkerVO("서울","구로구",new LatLng(37.4954856,126.858121)) );
            add( new MarkerVO("서울","종로구",new LatLng(37.5990998,126.9861493)) );
            add( new MarkerVO("서울","강북구",new LatLng(37.6469954,127.0147158)) );
            add( new MarkerVO("서울","중랑구",new LatLng(37.5953795,127.0939669)) );
            add( new MarkerVO("서울","강남구",new LatLng(37.4959854,127.0664091)) );
            add( new MarkerVO("서울","강서구",new LatLng(37.5657617,126.8226561)) );
            add( new MarkerVO("서울","중구",new LatLng(37.5579452,126.9941904)) );
            add( new MarkerVO("서울","강동구",new LatLng(37.5492077,127.1464824)) );
            add( new MarkerVO("서울","광진구",new LatLng(37.5481445,127.0857528)) );
            add( new MarkerVO("서울","마포구",new LatLng(37.5622906,126.9087803)) );
            add( new MarkerVO("서울","서초구",new LatLng(37.4769528,127.0378103)) );
            add( new MarkerVO("서울","성북구",new LatLng(37.606991,127.0232185)) );
            add( new MarkerVO("서울","노원구",new LatLng(37.655264,127.0771201)) );
            add( new MarkerVO("서울","송파구",new LatLng(37.5048534,127.1144822)) );
            add( new MarkerVO("서울","서대문구",new LatLng(37.5820369,126.9356665)) );
            add( new MarkerVO("서울","양천구",new LatLng(37.5270616,126.8561534)) );
            add( new MarkerVO("서울","영등포구",new LatLng(37.520641,126.9139242)) );
            add( new MarkerVO("서울","관악구",new LatLng(37.4653993,126.9438071)) );
            add( new MarkerVO("서울","성동구",new LatLng(37.5506753,127.0409622)) );
            add( new MarkerVO("서울","용산구",new LatLng(37.5311008,126.9810742)) );
            add( new MarkerVO("부산","중구",new LatLng(35.1061462,129.0323417)) );
            add( new MarkerVO("부산","서구",new LatLng(35.09773,129.0241207)) );
            add( new MarkerVO("부산","동구",new LatLng(35.1390588,129.0569225)) );
            add( new MarkerVO("부산","영도구",new LatLng(35.0912316,129.0681448)) );
            add( new MarkerVO("부산","부산진구",new LatLng(35.1630009,129.0531722)) );
            add( new MarkerVO("부산","동래구",new LatLng(35.2048575,129.0836402)) );
            add( new MarkerVO("부산","남구",new LatLng(35.1365596,129.084364)) );
            add( new MarkerVO("부산","북구",new LatLng(35.1971347,128.9903711)) );
            add( new MarkerVO("부산","해운대구",new LatLng(35.1631139,129.1635509)) );
            add( new MarkerVO("부산","사하구",new LatLng(35.1044223,128.9747081)) );
            add( new MarkerVO("부산","금정구",new LatLng(35.2429921,129.0922773)) );
            add( new MarkerVO("부산","강서구",new LatLng(35.2122157,128.9805666)) );
            add( new MarkerVO("부산","연제구",new LatLng(35.1761938,129.0797244)) );
            add( new MarkerVO("부산","수영구",new LatLng(35.145615,129.1130831)) );
            add( new MarkerVO("부산","사상구",new LatLng(35.1526399,128.9908173)) );
            add( new MarkerVO("부산","기장군",new LatLng(35.2445494,129.2221542)) );
            add( new MarkerVO("대구","중구",new LatLng(35.8693527,128.6061666)) );
            add( new MarkerVO("대구","동구",new LatLng(35.8866012,128.6353024)) );
            add( new MarkerVO("대구","서구",new LatLng(35.8717686,128.559115)) );
            add( new MarkerVO("대구","남구",new LatLng(35.8460224,128.5975291)) );
            add( new MarkerVO("대구","북구",new LatLng(35.8857114,128.5828073)) );
            add( new MarkerVO("대구","수성구",new LatLng(35.8582435,128.6306086)) );
            add( new MarkerVO("대구","달서구",new LatLng(35.8298561,128.5327576)) );
            add( new MarkerVO("대구","달성군",new LatLng(35.7746388,128.4317137)) );
            add( new MarkerVO("인천","중구",new LatLng(37.4737341,126.6214796)) );
            add( new MarkerVO("인천","동구",new LatLng(37.4738184,126.6433385)) );
            add( new MarkerVO("인천","남구",new LatLng(37.4636808,126.6504771)) );
            add( new MarkerVO("인천","연수구",new LatLng(37.4094099,126.6783088)) );
            add( new MarkerVO("인천","남동구",new LatLng(37.4469893,126.7319416)) );
            add( new MarkerVO("인천","부평구",new LatLng(37.5069818,126.7217739)) );
            add( new MarkerVO("인천","계양구",new LatLng(37.5369504,126.7377435)) );
            add( new MarkerVO("인천","서구",new LatLng(37.5454212,126.6759723)) );
            add( new MarkerVO("인천","강화군",new LatLng(37.7467263,126.4878731)) );
            add( new MarkerVO("인천","옹진군",new LatLng(37.213889,126.178333)) );
            add( new MarkerVO("광주","동구",new LatLng(35.1461042,126.9231228)) );
            add( new MarkerVO("광주","서구",new LatLng(35.1528248,126.8910977)) );
            add( new MarkerVO("광주","남구",new LatLng(35.1221068,126.9079579)) );
            add( new MarkerVO("광주","북구",new LatLng(35.1740167,126.9119842)) );
            add( new MarkerVO("광주","광산구",new LatLng(35.1394638,126.7937183)) );
            add( new MarkerVO("대전","동구",new LatLng(36.3269986,127.4326826)) );
            add( new MarkerVO("대전","중구",new LatLng(36.3253046,127.4212387)) );
            add( new MarkerVO("대전","서구",new LatLng(36.3545224,127.3836025)) );
            add( new MarkerVO("대전","유성구",new LatLng(36.362216,127.3561329)) );
            add( new MarkerVO("대전","대덕구",new LatLng(36.3467189,127.4155717)) );
            add( new MarkerVO("울산","중구",new LatLng(35.5687911,129.3321348)) );
            add( new MarkerVO("울산","남구",new LatLng(35.543683,129.3298017)) );
            add( new MarkerVO("울산","동구",new LatLng(35.5048563,129.4166007)) );
            add( new MarkerVO("울산","북구",new LatLng(35.582734,129.3609087)) );
            add( new MarkerVO("울산","울주군",new LatLng(35.54,129.2)) );
            add( new MarkerVO("경기","수원시",new LatLng(37.2635727,127.0286009)) );
            add( new MarkerVO("경기","장안구",new LatLng(37.3038177,127.0099185)) );
            add( new MarkerVO("경기","권선구",new LatLng(37.2572117,126.9722014)) );
            add( new MarkerVO("경기","팔달구",new LatLng(37.2860983,127.0349995)) );
            add( new MarkerVO("경기","영통구",new LatLng(37.2594897,127.0466381)) );
            add( new MarkerVO("경기","성남시",new LatLng(37.4449168,127.1388684)) );
            add( new MarkerVO("경기","수정구",new LatLng(37.4502462,127.1455402)) );
            add( new MarkerVO("경기","중원구",new LatLng(37.4304435,127.1372635)) );
            add( new MarkerVO("경기","분당구",new LatLng(37.382699,127.1189057)) );
            add( new MarkerVO("경기","의정부시",new LatLng(37.738098,127.0336819)) );
            add( new MarkerVO("경기","안양시",new LatLng(37.3942527,126.9568209)) );
            add( new MarkerVO("경기","만안구",new LatLng(37.3864722,126.9323127)) );
            add( new MarkerVO("경기","동안구",new LatLng(37.3925838,126.9514497)) );
            add( new MarkerVO("경기","부천시",new LatLng(37.5034138,126.7660309)) );
            add( new MarkerVO("경기","원미구",new LatLng(37.4965952,126.7870738)) );
            add( new MarkerVO("경기","소사구",new LatLng(37.4802924,126.800017)) );
            add( new MarkerVO("경기","오정구",new LatLng(37.5283145,126.796155)) );
            add( new MarkerVO("경기","광명시",new LatLng(37.4784878,126.8642888)) );
            add( new MarkerVO("경기","평택시",new LatLng(36.9921075,127.1129451)) );
            add( new MarkerVO("경기","동두천시",new LatLng(37.9034112,127.0605075)) );
            add( new MarkerVO("경기","안산시",new LatLng(37.3218778,126.8308848)) );
            add( new MarkerVO("경기","상록구",new LatLng(37.3012311,126.8450736)) );
            add( new MarkerVO("경기","단원구",new LatLng(37.3193953,126.8123263)) );
            add( new MarkerVO("경기","고양시",new LatLng(37.6583599,126.8320201)) );
            add( new MarkerVO("경기","덕양구",new LatLng(37.6372458,126.8322044)) );
            add( new MarkerVO("경기","일산동구",new LatLng(37.6585751,126.7749035)) );
            add( new MarkerVO("경기","일산서구",new LatLng(37.6754336,126.7505994)) );
            add( new MarkerVO("경기","과천시",new LatLng(37.429246,126.9874451)) );
            add( new MarkerVO("경기","구리시",new LatLng(37.5943124,127.1295646)) );
            add( new MarkerVO("경기","남양주시",new LatLng(37.6360028,127.2165279)) );
            add( new MarkerVO("경기","오산시",new LatLng(37.1498096,127.0772212)) );
            add( new MarkerVO("경기","시흥시",new LatLng(37.3798877,126.8031025)) );
            add( new MarkerVO("경기","군포시",new LatLng(37.3616703,126.9351741)) );
            add( new MarkerVO("경기","의왕시",new LatLng(37.344701,126.9683104)) );
            add( new MarkerVO("경기","하남시",new LatLng(37.5392646,127.2148919)) );
            add( new MarkerVO("경기","용인시",new LatLng(37.2410864,127.1775537)) );
            add( new MarkerVO("경기","처인구",new LatLng(37.2344406,127.2013458)) );
            add( new MarkerVO("경기","기흥구",new LatLng(37.2801554,127.114659)) );
            add( new MarkerVO("경기","수지구",new LatLng(37.32215,127.0974374)) );
            add( new MarkerVO("경기","파주시",new LatLng(37.7598688,126.7801781)) );
            add( new MarkerVO("경기","이천시",new LatLng(37.2719952,127.4348221)) );
            add( new MarkerVO("경기","안성시",new LatLng(37.0079695,127.2796786)) );
            add( new MarkerVO("경기","김포시",new LatLng(37.6152464,126.7156325)) );
            add( new MarkerVO("경기","화성시",new LatLng(37.1994932,126.8311887)) );
            add( new MarkerVO("경기","광주시",new LatLng(37.4171413,127.2561413)) );
            add( new MarkerVO("경기","양주시",new LatLng(37.7852875,127.0458453)) );
            add( new MarkerVO("경기","포천시",new LatLng(37.8949148,127.2003551)) );
            add( new MarkerVO("경기","여주군",new LatLng(37.2980237,127.6371628)) );
            add( new MarkerVO("경기","연천군",new LatLng(38.0964438,127.0748335)) );
            add( new MarkerVO("경기","가평군",new LatLng(37.8315403,127.5098827)) );
            add( new MarkerVO("경기","양평군",new LatLng(37.4912195,127.4875607)) );
            add( new MarkerVO("강원","춘천시",new LatLng(37.8813153,127.7299707)) );
            add( new MarkerVO("강원","원주시",new LatLng(37.3422186,127.9201621)) );
            add( new MarkerVO("강원","강릉시",new LatLng(37.751853,128.8760574)) );
            add( new MarkerVO("강원","동해시",new LatLng(37.5247192,129.1142915)) );
            add( new MarkerVO("강원","태백시",new LatLng(37.1640654,128.9855649)) );
            add( new MarkerVO("강원","속초시",new LatLng(38.2070148,128.5918488)) );
            add( new MarkerVO("강원","삼척시",new LatLng(37.4498683,129.1652059)) );
            add( new MarkerVO("강원","홍천군",new LatLng(37.6969518,127.8886827)) );
            add( new MarkerVO("강원","횡성군",new LatLng(37.4917566,127.9849295)) );
            add( new MarkerVO("강원","영월군",new LatLng(37.183637,128.4617535)) );
            add( new MarkerVO("강원","평창군",new LatLng(37.370474,128.3899769)) );
            add( new MarkerVO("강원","정선군",new LatLng(37.3807549,128.6609505)) );
            add( new MarkerVO("강원","철원군",new LatLng(38.146609,127.3132256)) );
            add( new MarkerVO("강원","화천군",new LatLng(38.1056484,127.7080492)) );
            add( new MarkerVO("강원","양구군",new LatLng(38.1100701,127.9898825)) );
            add( new MarkerVO("강원","인제군",new LatLng(38.0694675,128.1706991)) );
            add( new MarkerVO("강원","고성군",new LatLng(38.3801292,128.4674385)) );
            add( new MarkerVO("강원","양양군",new LatLng(38.0753925,128.6188503)) );
            add( new MarkerVO("충북","청주시",new LatLng(36.6424341,127.4890319)) );
            add( new MarkerVO("충북","상당구",new LatLng(36.6510445,127.4870295)) );
            add( new MarkerVO("충북","흥덕구",new LatLng(36.6370179,127.4696705)) );
            add( new MarkerVO("충북","충주시",new LatLng(36.9910113,127.9259497)) );
            add( new MarkerVO("충북","제천시",new LatLng(37.1325821,128.1909481)) );
            add( new MarkerVO("충북","청원군",new LatLng(36.5573058,127.5321341)) );
            add( new MarkerVO("충북","보은군",new LatLng(36.4894573,127.7294827)) );
            add( new MarkerVO("충북","옥천군",new LatLng(36.3063646,127.5712809)) );
            add( new MarkerVO("충북","영동군",new LatLng(36.1750231,127.7834302)) );
            add( new MarkerVO("충북","증평군",new LatLng(36.7855019,127.5816556)) );
            add( new MarkerVO("충북","진천군",new LatLng(36.8553798,127.4361117)) );
            add( new MarkerVO("충북","괴산군",new LatLng(36.815669,127.7865791)) );
            add( new MarkerVO("충북","음성군",new LatLng(36.9396792,127.6905018)) );
            add( new MarkerVO("충북","단양군",new LatLng(36.9845473,128.3655186)) );
            add( new MarkerVO("충남","천안시",new LatLng(36.815129,127.1138939)) );
            add( new MarkerVO("충남","동남구",new LatLng(36.806991,127.150335)) );
            add( new MarkerVO("충남","서북구",new LatLng(36.8785965,127.143142)) );
            add( new MarkerVO("충남","공주시",new LatLng(36.4465346,127.1191534)) );
            add( new MarkerVO("충남","보령시",new LatLng(36.3331629,126.6129441)) );
            add( new MarkerVO("충남","아산시",new LatLng(36.789796,127.0018494)) );
            add( new MarkerVO("충남","서산시",new LatLng(36.7844993,126.4503169)) );
            add( new MarkerVO("충남","논산시",new LatLng(36.1870656,127.0987453)) );
            add( new MarkerVO("충남","계룡시",new LatLng(36.2745577,127.2485896)) );
            add( new MarkerVO("충남","금산군",new LatLng(36.1086928,127.4880712)) );
            add( new MarkerVO("충남","연기군",new LatLng(36.592881,127.2923268)) );
            add( new MarkerVO("충남","부여군",new LatLng(36.2754406,126.910178)) );
            add( new MarkerVO("충남","서천군",new LatLng(36.0803312,126.6913277)) );
            add( new MarkerVO("충남","청양군",new LatLng(36.4588326,126.8022116)) );
            add( new MarkerVO("충남","홍성군",new LatLng(36.60123,126.6607764)) );
            add( new MarkerVO("충남","예산군",new LatLng(36.6826123,126.8483951)) );
            add( new MarkerVO("충남","태안군",new LatLng(36.7456421,126.2980528)) );
            add( new MarkerVO("충남","당진군",new LatLng(36.8936109,126.6283278)) );
            add( new MarkerVO("전북","전주시",new LatLng(35.8242238,127.1479532)) );
            add( new MarkerVO("전북","완산구",new LatLng(35.812111,127.1199201)) );
            add( new MarkerVO("전북","덕진구",new LatLng(35.8293872,127.1343073)) );
            add( new MarkerVO("전북","군산시",new LatLng(35.9676772,126.7366293)) );
            add( new MarkerVO("전북","익산시",new LatLng(35.9482858,126.9575991)) );
            add( new MarkerVO("전북","정읍시",new LatLng(35.5698855,126.8558955)) );
            add( new MarkerVO("전북","남원시",new LatLng(35.416357,127.3904877)) );
            add( new MarkerVO("전북","김제시",new LatLng(35.8036079,126.8808872)) );
            add( new MarkerVO("전북","완주군",new LatLng(35.891275,127.253895)) );
            add( new MarkerVO("전북","진안군",new LatLng(35.7917297,127.4248356)) );
            add( new MarkerVO("전북","무주군",new LatLng(36.0068191,127.6607805)) );
            add( new MarkerVO("전북","장수군",new LatLng(35.6472767,127.5211363)) );
            add( new MarkerVO("전북","임실군",new LatLng(35.6110549,127.2826599)) );
            add( new MarkerVO("전북","순창군",new LatLng(35.3744136,127.1375968)) );
            add( new MarkerVO("전북","고창군",new LatLng(35.4358216,126.7020806)) );
            add( new MarkerVO("전북","부안군",new LatLng(35.7315661,126.7334651)) );
            add( new MarkerVO("전남","목포시",new LatLng(34.8118351,126.3921664)) );
            add( new MarkerVO("전남","여수시",new LatLng(34.7603737,127.6622221)) );
            add( new MarkerVO("전남","순천시",new LatLng(34.950637,127.4872135)) );
            add( new MarkerVO("전남","나주시",new LatLng(35.0160601,126.7107572)) );
            add( new MarkerVO("전남","광양시",new LatLng(34.9406968,127.6958882)) );
            add( new MarkerVO("전남","담양군",new LatLng(35.3211394,126.9881673)) );
            add( new MarkerVO("전남","곡성군",new LatLng(35.2819553,127.2919175)) );
            add( new MarkerVO("전남","구례군",new LatLng(35.2024947,127.4626534)) );
            add( new MarkerVO("전남","고흥군",new LatLng(34.6112219,127.284978)) );
            add( new MarkerVO("전남","보성군",new LatLng(34.7714563,127.0798944)) );
            add( new MarkerVO("전남","화순군",new LatLng(35.0645029,126.9864799)) );
            add( new MarkerVO("전남","장흥군",new LatLng(34.6816856,126.9069278)) );
            add( new MarkerVO("전남","강진군",new LatLng(34.6420774,126.767261)) );
            add( new MarkerVO("전남","해남군",new LatLng(34.5732516,126.5989274)) );
            add( new MarkerVO("전남","영암군",new LatLng(34.8001685,126.6967918)) );
            add( new MarkerVO("전남","무안군",new LatLng(34.9904542,126.4816856)) );
            add( new MarkerVO("전남","함평군",new LatLng(35.0659399,126.5165524)) );
            add( new MarkerVO("전남","영광군",new LatLng(35.2771719,126.5119874)) );
            add( new MarkerVO("전남","장성군",new LatLng(35.3018333,126.7848541)) );
            add( new MarkerVO("전남","완도군",new LatLng(34.3110596,126.7550541)) );
            add( new MarkerVO("전남","진도군",new LatLng(34.4868712,126.2634853)) );
            add( new MarkerVO("전남","신안군",new LatLng(34.827332,126.101074)) );
            add( new MarkerVO("경북","포항시",new LatLng(36.0190178,129.3434808)) );
            add( new MarkerVO("경북","남구",new LatLng(36.0086283,129.3592306)) );
            add( new MarkerVO("경북","북구",new LatLng(36.0418465,129.3656451)) );
            add( new MarkerVO("경북","경주시",new LatLng(35.8561719,129.2247477)) );
            add( new MarkerVO("경북","김천시",new LatLng(36.1398393,128.1135947)) );
            add( new MarkerVO("경북","안동시",new LatLng(36.5683543,128.729357)) );
            add( new MarkerVO("경북","구미시",new LatLng(36.119485,128.3445734)) );
            add( new MarkerVO("경북","영주시",new LatLng(36.8056858,128.6240551)) );
            add( new MarkerVO("경북","영천시",new LatLng(35.9732915,128.9385493)) );
            add( new MarkerVO("경북","상주시",new LatLng(36.4109466,128.1590828)) );
            add( new MarkerVO("경북","문경시",new LatLng(36.586148,128.1867972)) );
            add( new MarkerVO("경북","경산시",new LatLng(35.8250555,128.7415441)) );
            add( new MarkerVO("경북","군위군",new LatLng(36.2428355,128.5727702)) );
            add( new MarkerVO("경북","의성군",new LatLng(36.3526576,128.6970053)) );
            add( new MarkerVO("경북","청송군",new LatLng(36.4359045,129.0571077)) );
            add( new MarkerVO("경북","영양군",new LatLng(36.6666558,129.1124007)) );
            add( new MarkerVO("경북","영덕군",new LatLng(36.4150799,129.3659681)) );
            add( new MarkerVO("경북","청도군",new LatLng(35.6472706,128.7339107)) );
            add( new MarkerVO("경북","고령군",new LatLng(35.7261415,128.262953)) );
            add( new MarkerVO("경북","성주군",new LatLng(35.9190079,128.2829738)) );
            add( new MarkerVO("경북","칠곡군",new LatLng(35.9953059,128.4015434)) );
            add( new MarkerVO("경북","예천군",new LatLng(36.6577004,128.4528808)) );
            add( new MarkerVO("경북","봉화군",new LatLng(36.8930933,128.7323752)) );
            add( new MarkerVO("경북","울진군",new LatLng(36.9930661,129.4004195)) );
            add( new MarkerVO("경북","울릉군",new LatLng(37.4844171,130.9058002)) );
            add( new MarkerVO("경남","창원시",new LatLng(35.2538433,128.6402609)) );
            add( new MarkerVO("경남","의창구",new LatLng(35.2538433,128.6402609)) );
            add( new MarkerVO("경남","성산구",new LatLng(35.1984659,128.7027178)) );
            add( new MarkerVO("경남","마산합포구",new LatLng(35.1969107,128.5678853)) );
            add( new MarkerVO("경남","마산회원구",new LatLng(35.2206349,128.5795296)) );
            add( new MarkerVO("경남","진해구",new LatLng(35.1330251,128.7100381)) );
            add( new MarkerVO("경남","진주시",new LatLng(35.1799817,128.1076213)) );
            add( new MarkerVO("경남","통영시",new LatLng(34.8544227,128.433182)) );
            add( new MarkerVO("경남","사천시",new LatLng(35.0037788,128.064185)) );
            add( new MarkerVO("경남","김해시",new LatLng(35.2285451,128.8893517)) );
            add( new MarkerVO("경남","밀양시",new LatLng(35.5037598,128.7464415)) );
            add( new MarkerVO("경남","거제시",new LatLng(34.8806427,128.6210824)) );
            add( new MarkerVO("경남","양산시",new LatLng(35.3350072,129.0371689)) );
            add( new MarkerVO("경남","의령군",new LatLng(35.3221896,128.261658)) );
            add( new MarkerVO("경남","함안군",new LatLng(35.2725591,128.4064797)) );
            add( new MarkerVO("경남","창녕군",new LatLng(35.5445563,128.4922143)) );
            add( new MarkerVO("경남","고성군",new LatLng(34.973149,128.3222456)) );
            add( new MarkerVO("경남","남해군",new LatLng(34.8376721,127.8924234)) );
            add( new MarkerVO("경남","하동군",new LatLng(35.0672108,127.7512687)) );
            add( new MarkerVO("경남","산청군",new LatLng(35.4155885,127.8734981)) );
            add( new MarkerVO("경남","함양군",new LatLng(35.5204614,127.7251763)) );
            add( new MarkerVO("경남","거창군",new LatLng(35.6867229,127.9095155)) );
            add( new MarkerVO("경남","합천군",new LatLng(35.5665758,128.1657995)) );
            add( new MarkerVO("제주","제주시",new LatLng(33.4996213,126.5311884)) );
            add( new MarkerVO("제주","서귀포시",new LatLng(33.2541205,126.560076)) );
            add( new MarkerVO("세종","세종시",new LatLng(36.6208498,127.2871603)) );

        }
    };




    public static List testList = new ArrayList(){
        {

            add("충북 단양군");
            add("충북 옥천군");
            add("충북 제천시");
            add("충북 진천군");
            add("충북 청주시");
            add("충북 충주시");
            add("부산 강서구");
            add("부산 금정구");
            add("부산 기장군");
            add("부산 남구");
            add("부산 동구");
            add("부산 동래구");
            add("부산 부산진구");
            add("부산 북구");
            add("부산 사상구");
            add("부산 사하구");
            add("부산 서구");
            add("부산 수영구");
            add("부산 연제구");
            add("부산 영도구");
            add("부산 중구");
            add("부산 해운대구");
            add("대전 대덕구");
            add("대전 동구");
            add("대전 서구");
            add("대전 유성구");
            add("대전 중구");
            add("인천 강화군");
            add("인천 계양구");
            add("인천 남구");
            add("인천 남동구");
            add("인천 동구");
            add("인천 부평구");
            add("인천 서구");
            add("인천 연수구");
            add("인천 중구");
            add("충남 당진군");
            add("충남 서산시");
            add("충남 아산시");
            add("충남 천안시");
            add("광주 광산구");
            add("광주 남구");
            add("광주 동구");
            add("광주 북구");
            add("광주 서구");
            add("울산 남구");
            add("울산 동구");
            add("울산 북구");
            add("울산 울주군");
            add("울산 중구");
            add("전남 광양시");
            add("전남 목포시");
            add("전남 순천시");
            add("전남 여수시");
            add("전남 영암군");
            add("경남 거제시");
            add("경남 김해시");
            add("경남 사천시");
            add("경남 양산시");
            add("경남 진주시");
            add("경남 창원시");
            add("경남 하동군");
            add("제주 서귀포시");
            add("제주 제주시");
            add("전북 고창군");
            add("전북 군산시");
            add("전북 김제시");
            add("전북 남원시");
            add("전북 부안군");
            add("전북 익산시");
            add("전북 전주시");
            add("전북 정읍시");
            add("경북 경산시");
            add("경북 경주시");
            add("경북 구미시");
            add("경북 김천시");
            add("경북 안동시");
            add("경북 영주시");
            add("경북 포항시");
            add("강원 강릉시");
            add("강원 동해시");
            add("강원 삼척시");
            add("강원 원주시");
            add("강원 춘천시");
            add("경기 가평군");
            add("경기 고양시");
            add("경기 과천시");
            add("경기 광명시");
            add("경기 광주시");
            add("경기 구리시");
            add("경기 군포시");
            add("경기 김포시");
            add("경기 남양주시");
            add("경기 동두천시");
            add("경기 부천시");
            add("경기 성남시");
            add("경기 수원시");
            add("경기 시흥시");
            add("경기 안산시");
            add("경기 안성시");
            add("경기 안양시");
            add("경기 양주시");
            add("경기 양평군");
            add("경기 여주군");
            add("경기 연천군");
            add("경기 오산시");
            add("경기 용인시");
            add("경기 의왕시");
            add("경기 의정부시");
            add("경기 이천시");
            add("경기 파주시");
            add("경기 평택시");
            add("경기 포천시");
            add("경기 하남시");
            add("경기 화성시");
            add("대구 남구");
            add("대구 달서구");
            add("대구 달성군");
            add("대구 동구");
            add("대구 북구");
            add("대구 서구");
            add("대구 수성구");
            add("대구 중구");
            add("서울 강남구");
            add("서울 강동구");
            add("서울 강북구");
            add("서울 강서구");
            add("서울 관악구");
            add("서울 광진구");
            add("서울 구로구");
            add("서울 금천구");
            add("서울 노원구");
            add("서울 도봉구");
            add("서울 동대문구");
            add("서울 동작구");
            add("서울 마포구");
            add("서울 서대문구");
            add("서울 서초구");
            add("서울 성동구");
            add("서울 성북구");
            add("서울 송파구");
            add("서울 양천구");
            add("서울 영등포구");
            add("서울 용산구");
            add("서울 은평구");
            add("서울 종로구");
            add("서울 중구");
            add("서울 중랑구");
            add("세종 세종시");

        }
    };
*/


}
