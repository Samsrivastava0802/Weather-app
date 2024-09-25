package com.rsstudio.weather.data.network.model;

public class Weather2 {

    private Daily daily;
    private Hourly hourly;
    private String timezone;

    public Weather2(Daily daily, Hourly hourly, String timezone) {
        this.daily = daily;
        this.hourly = hourly;
        this.timezone = timezone;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
