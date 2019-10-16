package com.jgoodwin.localdeets.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jgoodwin.localdeets.BuildConfig;
import com.jgoodwin.localdeets.R;
import com.jgoodwin.localdeets.api.WeatherApi;
import com.jgoodwin.localdeets.api.model.Traffic.Traffic;
import com.jgoodwin.localdeets.api.model.TrafficApi;
import com.jgoodwin.localdeets.api.model.Weather.WeatherRoot;
import com.jgoodwin.localdeets.utils.ApiUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String trafficApiKey = BuildConfig.TrafficApiKey;
    String weatherApiKey = BuildConfig.WeatherApiKey;
    String seattleZipcode = "98109";
    String everettToSeattleId = "1";

    private String TAG = MainActivity.class.getSimpleName();

    private TextView mDriveTimesTextViewResult;
    private TextView mWeatherTextViewResult;
    private ImageView mWeatherIconImageView;
    private WeatherApi mWeatherApi;
    private TrafficApi mTrafficApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDriveTimesTextViewResult = findViewById(R.id.drive_times_text);
        mWeatherTextViewResult = findViewById(R.id.weather_text);
        mWeatherIconImageView = findViewById(R.id.weather_icon);

        // Do Traffic & Weather requests
        try {
            doTrafficRequest(everettToSeattleId,trafficApiKey);
            doWeatherRequest(seattleZipcode, weatherApiKey);
        } catch (Exception e) {

        }

        TextView driveTimesTextView = findViewById(R.id.drive_times_text);
        driveTimesTextView.setOnClickListener(mTouchyListener);
    }

    // TODO: Make this actually do something later.
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


    void doWeatherRequest(String zip, String key) {
        mWeatherApi = ApiUtils.getWeather();
        Call<WeatherRoot> call = mWeatherApi.getTempForZip(zip,key);

        call.enqueue(new Callback<WeatherRoot>() {

            @Override
            public void onResponse(Call<WeatherRoot> call, final Response<WeatherRoot> response) {

                //Convert response to rounded decimal
                DecimalFormat df = new DecimalFormat("#,###");
                df.setRoundingMode(RoundingMode.HALF_UP);
                final String tempAsRoundedDecimal = df.format(response.body().getMain().getTemp());

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update Weather textview from response.
                        mWeatherTextViewResult.setText("Seattle temp " + tempAsRoundedDecimal);
                    }
                });
            }

            @Override
            public void onFailure(Call<WeatherRoot> call, Throwable t) {
                call.cancel();
                Log.d(TAG, "Call FAILED");
            }
        });
    }

    void doTrafficRequest(String travelTimeID, String accessCode) {
        mTrafficApi = ApiUtils.getTravelTime();
        Call<Traffic> call = mTrafficApi.getTravelTime(travelTimeID, accessCode);
        call.enqueue(new Callback<Traffic>() {
            @Override
            public void onResponse(Call<Traffic> call, final Response<Traffic> response) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update Traffic textview from response.
                        mDriveTimesTextViewResult.setText("Everett to Seattle  " + response.body().getCurrentTime() + " mns");
                    }
                });
            }

            @Override
            public void onFailure(Call<Traffic> call, Throwable t) {

            }
        });
    }
}
