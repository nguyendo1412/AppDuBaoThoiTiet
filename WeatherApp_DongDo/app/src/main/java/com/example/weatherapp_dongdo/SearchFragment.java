package com.example.weatherapp_dongdo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    Weather weather = new Weather();
    EditText editText;
    TextView tentp, tenqg, nhietdo, doam, bautroi, tocdogio, apsuat, may, maxmin;
    ImageView icon;
    FloatingActionButton floatingActionButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getId(view);
        api_key("Saigon");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePhoneKeypad(v);
                if (String.valueOf(editText.getText()) != null)
                    api_key(String.valueOf(editText.getText()));
                
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setImage(icon,weather.icon);
                        tentp.setText(weather.tenTP);
                        tenqg.setText(weather.tenQG);
                        nhietdo.setText(weather.nhietDo+"°C");
                        bautroi.setText(weather.bauTroi);
                        doam.setText("Độ ẩm "+weather.doAm +"%");
                        tocdogio.setText(weather.tocDoGio+" m/s");
                        apsuat.setText(weather.apSuat+" mb");
                        may.setText(weather.may+" %");
                        maxmin.setText(weather.max+"/"+weather.min);
                    }
                },500);

            }
        });
    }

    public void removePhoneKeypad(View view) {
        InputMethodManager inputManager = (InputMethodManager) view
                .getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        IBinder binder = view.getWindowToken();
        inputManager.hideSoftInputFromWindow(binder,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void getId(View view)
    {
        editText = view.findViewById(R.id.search);
        floatingActionButton = view.findViewById(R.id.floating);
        tentp = view.findViewById(R.id.tentp);
        tenqg = view.findViewById(R.id.tenquocgia);
        nhietdo = view.findViewById(R.id.nhietdo);
        doam = view.findViewById(R.id.doam);
        bautroi = view.findViewById(R.id.bautroi);
        tocdogio = view.findViewById(R.id.tocdogio);
        apsuat = view.findViewById(R.id.apsuat);
        may = view.findViewById(R.id.may);
        maxmin = view.findViewById(R.id.maxmin);
        icon = view.findViewById(R.id.icon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    private void api_key(String City) {
        OkHttpClient client=new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q="+City+"&units=metric&appid=432ab892be65e0418d4228509c0bd1a2")
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

    private void setImage(ImageView imageView, String value){
        switch (value)
        {
            case "01d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                break;
            case "01n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                break;
            case "02d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                break;
            case "02n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                break;
            case "03d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d03d));
                break;
            case "03n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d03d));
                break;
            case "04d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                break;
            case "04n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                break;
            case "09d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                break;
            case "09n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                break;
            case "10d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                break;
            case "10n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                break;
            case "11d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                break;
            case "11n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                break;
            case "13d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                break;
            case "13n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                break;
            default:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.wheather));
        }
    }
}