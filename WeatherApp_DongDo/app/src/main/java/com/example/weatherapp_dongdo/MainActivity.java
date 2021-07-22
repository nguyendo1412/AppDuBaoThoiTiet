package com.example.weatherapp_dongdo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    CurrentLocation currentLocation = new CurrentLocation();
    Button button;
    ImageView imageView;
    Weather weather = new Weather();
    WellcomeActivity a = new WellcomeActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);

        Shake(imageView);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                api_key(a.lat, a.lon);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chuyển trang

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("weather", (Serializable) weather);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Shake(View view)
    {
        RotateAnimation anim = new RotateAnimation(-5, 5,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        //Set tốc độ anim
        anim.setDuration(450);
        view.setAnimation(anim);
    }

    private void Anim_xoay(View view)
    {
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        //Set tốc độ anim
        anim.setDuration(1000);
        view.setAnimation(anim);
    }

    private void api_key(String lat, String lon) {
        OkHttpClient client=new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=metric&appid=432ab892be65e0418d4228509c0bd1a2")
                .get()
                .build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Response response= client.newCall(request).execute();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {
                    String responseData= response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        weather.tenTP = jsonObject.getString("name");

                        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                        weather.nhietDo = jsonObjectMain.getString("temp");
                        weather.doAm = jsonObjectMain.getString("humidity");
                        weather.max = jsonObjectMain.getString("temp_max");
                        weather.min = jsonObjectMain.getString("temp_min");
                        weather.apSuat = jsonObjectMain.getString("pressure");

                        Double a = Double.valueOf(weather.nhietDo);
                        weather.nhietDo = String.valueOf(a.intValue());

                        Double b = Double.valueOf(weather.max);
                        weather.max = String.valueOf(b.intValue());

                        Double c = Double.valueOf(weather.min);
                        weather.min = String.valueOf(c.intValue());


                        JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                        weather.tocDoGio = jsonObjectWind.getString("speed");

                        JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                        weather.may = jsonObjectCloud.getString("all");

                        JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                        weather.tenQG = jsonObjectSys.getString("country");
                        String timemoc = jsonObjectSys.getString("sunrise");
                        String timelan = jsonObjectSys.getString("sunset");

                        Long l1 = Long.valueOf(timemoc);
                        Date date1 = new Date(l1 * 1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH"+":"+"mm");
                        weather.tgMoc = simpleDateFormat.format(date1);

                        Long l2 = Long.valueOf(timelan);
                        Date date2 = new Date(l2 * 1000L);
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH"+":"+"mm");
                        weather.tgLan = simpleDateFormat1.format(date2);

                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        weather.bauTroi = jsonObjectWeather.getString("main");
                        weather.icon = jsonObjectWeather.getString("icon");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}