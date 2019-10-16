package com.jgoodwin.localdeets.api;

import com.jgoodwin.localdeets.api.model.Weather.WeatherRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    String METHOD = "data/2.5/weather?units=imperial";
    @GET(METHOD)
    Call<WeatherRoot> getTempForZip(@Query(value ="zip", encoded = true) String zip, @Query(value = "appid", encoded = true) String appid);
}
