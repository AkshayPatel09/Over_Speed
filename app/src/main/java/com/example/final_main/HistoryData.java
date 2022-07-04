package com.example.final_main;

public class HistoryData {

    public String date,time,latitude,longitude,speedLimit,yourSpeed;
    public HistoryData(){}

    public void setDate(String date){
        this.date = date;
    }
    public void setTime(String time){
        this.time = time;
    }
    public void setLatitude(String latitude){
        this.latitude = latitude;
    }
    public void setLongitude(String longitude){
        this.longitude = longitude;
    }
    public void setSpeedLimit(String speedLimit){
        this.speedLimit = speedLimit;
    }
    public void setYourSpeed(String yourSpeed){
        this.yourSpeed = yourSpeed;
    }


    public String getYourSpeed(){
        return this.yourSpeed;
    }
    public String getSpeedLimit(){
        return this.speedLimit;
    }
    public String getLatitude(){
        return this.latitude;
    }
    public String getLongitude(){
        return this.longitude;
    }
    public String getTime(){
        return this.time;
    }
    public String getDate(){
        return this.date;
    }

}
