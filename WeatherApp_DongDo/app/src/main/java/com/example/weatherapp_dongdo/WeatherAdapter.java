package com.example.weatherapp_dongdo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter {
    Context context;
    ArrayList<Weather> arrayList;

    public WeatherAdapter(Context context, ArrayList<Weather> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.layout_item, null);

        Weather weather = arrayList.get(position);

        TextView thu = convertView.findViewById(R.id.thu);
        TextView ngay = convertView.findViewById(R.id.ngay);
        TextView max = convertView.findViewById(R.id.max);
        TextView min = convertView.findViewById(R.id.min);
        ImageView icon = convertView.findViewById(R.id.icon);

        thu.setText(weather.bauTroi);
        ngay.setText(weather.tenQG);
        max.setText(weather.max +"°C");
        min.setText(weather.min+"°C");
        String a = weather.icon;
        icon.setImageResource(setImage(weather.icon));
        return convertView;
    }

    public int setImage(String value){
        switch (value)
        {
            case "01d": return R.drawable.d01d;
            case "01n": return R.drawable.d01d;
            case "02d": return R.drawable.d02d;
            case "02n": return R.drawable.d02d;
            case "03d": return R.drawable.d03d;
            case "03n": return R.drawable.d03d;
            case "04d": return R.drawable.d04d;
            case "04n": return R.drawable.d04d;
            case "09d": return R.drawable.d09d;
            case "09n": return R.drawable.d09d;
            case "10d": return R.drawable.d10d;
            case "10n": return R.drawable.d10d;
            case "11d": return R.drawable.d11d;
            case "11n": return R.drawable.d11d;
            case "13d": return R.drawable.d13d;
            case "13n": return R.drawable.d13d;
            default:
                return R.drawable.wheather;
        }
    }

    public String setDay(String value)
    {
        if (value.equalsIgnoreCase("Monday"))
            return "Thứ hai";
        else if (value.toString().equals("Tuesday"))
            return "Thứ ba";
        else if (value.toString().equals("Wednesday"))
            return "Thứ tư";
        else if (value.toString().equals("Thursday"))
            return "Thứ năm";
        else if (value.toString().equals("Friday"))
            return "Thứ sáu";
        else if (value.toString().equals("Saturday"))
            return "Thứ bảy";
        else
            return "Chủ nhật";
    }
}
