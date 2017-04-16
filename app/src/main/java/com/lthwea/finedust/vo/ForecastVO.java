package com.lthwea.finedust.vo;

/**
 * Created by LeeTaeHun on 2017. 4. 16..
 */

public class ForecastVO {

    private String	dataTime;
    private String	informData;
    private String	informCode;
    private String	informOverall;
    private String	informCause;
    private String	informGrade;
    private String	imageUrl1;
    private String	imageUrl2;
    private String	imageUrl3;
    private String	imageUrl4;
    private String	imageUrl5;
    private String	imageUrl6;

    public ForecastVO() {
    }

    public ForecastVO(String dataTime, String informData, String informCode, String informOverall, String informCause, String informGrade, String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4, String imageUrl5, String imageUrl6) {
        this.dataTime = dataTime;
        this.informData = informData;
        this.informCode = informCode;
        this.informOverall = informOverall;
        this.informCause = informCause;
        this.informGrade = informGrade;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
        this.imageUrl5 = imageUrl5;
        this.imageUrl6 = imageUrl6;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getInformData() {
        return informData;
    }

    public void setInformData(String informData) {
        this.informData = informData;
    }

    public String getInformCode() {
        return informCode;
    }

    public void setInformCode(String informCode) {
        this.informCode = informCode;
    }

    public String getInformOverall() {
        return informOverall;
    }

    public void setInformOverall(String informOverall) {
        this.informOverall = informOverall;
    }

    public String getInformCause() {
        return informCause;
    }

    public void setInformCause(String informCause) {
        this.informCause = informCause;
    }

    public String getInformGrade() {
        return informGrade;
    }

    public void setInformGrade(String informGrade) {
        this.informGrade = informGrade;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

    public String getImageUrl4() {
        return imageUrl4;
    }

    public void setImageUrl4(String imageUrl4) {
        this.imageUrl4 = imageUrl4;
    }

    public String getImageUrl5() {
        return imageUrl5;
    }

    public void setImageUrl5(String imageUrl5) {
        this.imageUrl5 = imageUrl5;
    }

    public String getImageUrl6() {
        return imageUrl6;
    }

    public void setImageUrl6(String imageUrl6) {
        this.imageUrl6 = imageUrl6;
    }

    @Override
    public String toString() {
        return "ForecastVO{" +
                "dataTime='" + dataTime + '\'' +
                ", informData='" + informData + '\'' +
                ", informCode='" + informCode + '\'' +
                ", informOverall='" + informOverall + '\'' +
                ", informCause='" + informCause + '\'' +
                ", informGrade='" + informGrade + '\'' +
                ", imageUrl1='" + imageUrl1 + '\'' +
                ", imageUrl2='" + imageUrl2 + '\'' +
                ", imageUrl3='" + imageUrl3 + '\'' +
                ", imageUrl4='" + imageUrl4 + '\'' +
                ", imageUrl5='" + imageUrl5 + '\'' +
                ", imageUrl6='" + imageUrl6 + '\'' +
                '}';
    }
}
