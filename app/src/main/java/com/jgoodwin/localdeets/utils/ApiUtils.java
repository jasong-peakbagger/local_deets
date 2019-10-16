package com.jgoodwin.localdeets.utils;

import com.jgoodwin.localdeets.api.RetrofitClient;
import com.jgoodwin.localdeets.api.WeatherApi;
import com.jgoodwin.localdeets.api.model.TrafficApi;

public class ApiUtils {

    public static final String WEATHER_BASE_URL = "http://api.openweathermap.org/";
    public static final String TRAFFIC_BASE_URL = "https://www.wsdot.com/";

    public static WeatherApi getWeather() {
        return RetrofitClient.getClient(WEATHER_BASE_URL).create(WeatherApi.class);
    }

    public static TrafficApi getTravelTime() {
        return RetrofitClient.getClient(TRAFFIC_BASE_URL).create(TrafficApi.class);
    }
}
