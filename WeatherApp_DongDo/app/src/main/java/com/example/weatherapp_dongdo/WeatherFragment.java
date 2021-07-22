package com.example.weatherapp_dongdo;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.WeakHashMap;

import kotlin.Unit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    Weather weather = new Weather();
    TextView tentp, tenqg, nhietdo, doam, bautroi, binhminh, hoanghon, tocdogio, apsuat, may, maxmin;
    ImageView icon;
    ImageButton button;
    public static String ten;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    private void Shake(View view)
    {
        RotateAnimation anim = new RotateAnimation(-3, 3,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        //Set tốc độ anim
        anim.setDuration(850);
        view.setAnimation(anim);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getId(view);

        Intent intent = getActivity().getIntent();
        weather = (Weather) intent.getSerializableExtra("weather");
        setdata();

        ten = weather.tenTP;
        Shake(icon);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Weather_NextActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getId(View view)
    {
        button = view.findViewById(R.id.next);
        tentp = view.findViewById(R.id.tentp);
        tenqg = view.findViewById(R.id.tenquocgia);
        nhietdo = view.findViewById(R.id.nhietdo);
        doam = view.findViewById(R.id.doam);
        bautroi = view.findViewById(R.id.bautroi);
        binhminh = view.findViewById(R.id.tgbinhminh);
        hoanghon = view.findViewById(R.id.tghoanghon);
        tocdogio = view.findViewById(R.id.tocdogio);
        apsuat = view.findViewById(R.id.apsuat);
        may = view.findViewById(R.id.may);
        maxmin = view.findViewById(R.id.maxmin);
        icon = view.findViewById(R.id.icon);
    }

    private void setdata()
    {
        setImage(icon,weather.icon);
        tentp.setText(weather.tenTP);
        tenqg.setText(weather.tenQG);
        nhietdo.setText(weather.nhietDo+"°C");
        bautroi.setText(weather.bauTroi);
        doam.setText("Độ ẩm "+weather.doAm +"%");
        hoanghon.setText(weather.tgLan);
        binhminh.setText(weather.tgMoc);
        tocdogio.setText(weather.tocDoGio+" m/s");
        apsuat.setText(weather.apSuat+" mb");
        may.setText(weather.may+" %");
        maxmin.setText(weather.max+"/"+weather.min);
    }

    public void setImage(ImageView imageView, String value){
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