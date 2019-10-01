package com.jgoodwin.localdeets.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jgoodwin.localdeets.BuildConfig;
import com.jgoodwin.localdeets.R;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    String trafficApiKey = BuildConfig.TrafficApiKey;
    String weatherApiKey = BuildConfig.WeatherApiKey;

    private String TAG = MainActivity.class.getSimpleName();
    private final OkHttpClient client = new OkHttpClient();
    
    String weatherEverettUrl = "http://api.openweathermap.org/data/2.5/weather?zip=98208,us&units=imperial&appid=";

    private TextView mDriveTimesTextViewResult;
    private TextView mWeatherTextViewResult;
    private ImageView mWeatherIconImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDriveTimesTextViewResult = findViewById(R.id.drive_times_text);
        mWeatherTextViewResult = findViewById(R.id.weather_text);
        mWeatherIconImageView = findViewById(R.id.weather_icon);

        try {
            StringBuilder trafficEverSeattleurl = new StringBuilder("http://www.wsdot.com/Traffic/api/TravelTimes/TravelTimesREST.svc/GetTravelTimeAsJson?TravelTimeID=1&AccessCode=");
            trafficEverSeattleurl.append(trafficApiKey);
            doDriveTimesRequest(trafficEverSeattleurl.toString());

            StringBuilder weatherSeattleUrl = new StringBuilder("http://api.openweathermap.org/data/2.5/weather?zip=98109,us&units=imperial&appid=");
            weatherSeattleUrl.append(weatherApiKey);
            doWeatherRequest(weatherSeattleUrl.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView driveTimesTextView = findViewById(R.id.drive_times_text);
        driveTimesTextView.setOnClickListener(mTouchyListener);
    }


    private View.OnClickListener mTouchyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = getApplicationContext();
            CharSequence text = "I GOT TOUCHED!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    };



    void doDriveTimesRequest(String url) {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {

                        //can show toast on failure
                        Log.d(TAG, "Drive times call failed");
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) {
                                throw new IOException("Unexpected code " + response);
                            } else {
                                final String responseAsText = responseBody.string();
                                Log.d(TAG,responseAsText);

                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Update Weather textview from response.
                                        mDriveTimesTextViewResult.setText("Everett to Seattle  " + parse(responseAsText) + " mns");
                                    }
                                });
                            }

                            Headers responseHeaders = response.headers();
                            for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                            }
                        }
                    }

                    public String parse(String jsonLine) {
                        JsonElement jelement = new JsonParser().parse(jsonLine);
                        JsonObject  jobject = jelement.getAsJsonObject();
                        String result = jobject.get("CurrentTime").getAsString();
                        Log.d(TAG, result);
                        return result;
                    }
                });
    }

    void doWeatherRequest(String url) {

        Log.d(TAG, url);

        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {

                        //can show toast on failure
                        Log.d(TAG, "Weather call failed");
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) {
                                throw new IOException("Unexpected code " + response);
                            } else {
                                final String responseAsText = responseBody.string();
                                Log.d(TAG,responseAsText);

                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //change text in text view on mainactivity
                                        mWeatherTextViewResult.setText("Seattle temp " + parse(responseAsText));
                                    }
                                });
                            }

                            Headers responseHeaders = response.headers();
                            for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                            }
                        }
                    }

                    public String parse(String jsonLine) {
                        // Given api response, convert to jsonobject and parse out temperature.
                        JsonElement jelement = new JsonParser().parse(jsonLine);
                        JsonObject  jobject = jelement.getAsJsonObject();
                        jobject = jobject.getAsJsonObject("main");
                        BigDecimal result = jobject.get("temp").getAsBigDecimal();

                        // Finally, convert Big D to a rounded-up string
                        DecimalFormat df = new DecimalFormat("#,###");
                        df.setRoundingMode(RoundingMode.HALF_UP);
                        String rounded = df.format(result);

                        return rounded;
                    }
                });
    }
}
