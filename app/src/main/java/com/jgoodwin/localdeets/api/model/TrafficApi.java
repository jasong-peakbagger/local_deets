package com.jgoodwin.localdeets.api.model;

import com.jgoodwin.localdeets.api.model.Traffic.Traffic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrafficApi {

    String METHOD = "Traffic/api/TravelTimes/TravelTimesREST.svc/GetTravelTimeAsJson?";
    @GET(METHOD)
    Call<Traffic> getTravelTime(@Query(value ="TravelTimeID", encoded = true) String id, @Query(value = "AccessCode", encoded = true) String accessCode);
}
