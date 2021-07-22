package com.example.weatherapp_dongdo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Weather_NextActivity extends AppCompatActivity {

    WeatherAdapter weatherAdapter;
    ArrayList<Weather> arrayList;
    ImageButton button;
    ListView listView;
    TextView tentp;
    WellcomeActivity a = new WellcomeActivity();
    WeatherFragment b = new WeatherFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather__next);

        getID();
        get8DayData(a.lat, a.lon);

        tentp.setText(b.ten);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                weatherAdapter = new WeatherAdapter(Weather_NextActivity.this, arrayList);
                listView.setAdapter(weatherAdapter);
            }
        },500);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void getID()
    {
        tentp = findViewById(R.id.tentp);
        button = findViewById(R.id.back);
        listView =(ListView) findViewById(R.id.listview);
        arrayList = new ArrayList<Weather>();

    }

    public void get8DayData(String lat, String lon)
    {
        OkHttpClient client=new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&appid=432ab892be65e0418d4228509c0bd1a2&units=metric")
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
                        JSONArray jsonArray = jsonObject.getJSONArray("daily");

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            Weather weather1 = new Weather();


                            JSONObject jsonObjectI = jsonArray.getJSONObject(i);
                            JSONObject jsonObjectMain = jsonObjectI.getJSONObject("temp");
                            weather1.max = jsonObjectMain.getString("max");
                            weather1.min = jsonObjectMain.getString("min");

                            Double b = Double.valueOf(weather1.max);
                            weather1.max = String.valueOf(b.intValue());

                            Double c = Double.valueOf(weather1.min);
                            weather1.min = String.valueOf(c.intValue());

                            JSONArray jsonArray1 = jsonObjectI.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArray1.getJSONObject(0);
                            weather1.icon = jsonObjectWeather.getString("icon");

                            String day = jsonObjectI.getString("dt");
                            Long l1 = Long.valueOf(day);
                            Date date1 = new Date(l1 * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE ");
                            weather1.tenQG = simpleDateFormat.format(date1);
                            weather1.bauTroi = simpleDateFormat1.format(date1);

                            arrayList.add(weather1);
                        }

                        //weatherAdapter.notifyDataSetChanged();
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