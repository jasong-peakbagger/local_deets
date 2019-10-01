package com.jgoodwin.localdeets.utils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GetDriveTimesData {

    //https://www.wsdot.com/traffic/api/TravelTimes/TravelTimesREST.svc/Help
    String url = "http://www.wsdot.com/Traffic/api/TravelTimes/TravelTimesREST.svc/GetTravelTimeAsJson?AccessCode=0802c193-2f12-4eec-bfcd-32a3ce9e6a26&TravelTimeID=2";

    private final OkHttpClient client = new OkHttpClient();

    public String FetchTimes (String travelTimeId) throws Exception {
        String jsonBlob = null;

        run(url);

        return jsonBlob;
    }

    public void run(String url) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            System.out.println(response.body().string());
        }
    }
}
