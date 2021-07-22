package com.example.weatherapp_dongdo;

import android.widget.ImageView;

import java.io.Serializable;

public class Weather implements Serializable {
    String tenTP;
    String tenQG;
    String nhietDo;
    String max;
    String min;
    String doAm;
    String tocDoGio;
    String bauTroi;
    String apSuat;
    String may;
    String tgMoc;
    String tgLan;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    String icon;

    public String getTenTP() {
        return tenTP;
    }

    public void setTenTP(String tenTP) {
        this.tenTP = tenTP;
    }

    public String getTenQG() {
        return tenQG;
    }

    public void setTenQG(String tenQG) {
        this.tenQG = tenQG;
    }

    public String getNhietDo() {
        return nhietDo;
    }

    public void setNhietDo(String nhietDo) {
        this.nhietDo = nhietDo;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getDoAm() {
        return doAm;
    }

    public void setDoAm(String doAm) {
        this.doAm = doAm;
    }

    public String getTocDoGio() {
        return tocDoGio;
    }

    public void setTocDoGio(String tocDoGio) {
        this.tocDoGio = tocDoGio;
    }

    public String getBauTroi() {
        return bauTroi;
    }

    public void setBauTroi(String bauTroi) {
        this.bauTroi = bauTroi;
    }

    public String getApSuat() {
        return apSuat;
    }

    public void setApSuat(String apSuat) {
        this.apSuat = apSuat;
    }

    public String getMay() {
        return may;
    }

    public void setMay(String may) {
        this.may = may;
    }

    public String getTgMoc() {
        return tgMoc;
    }

    public void setTgMoc(String tgMoc) {
        this.tgMoc = tgMoc;
    }

    public String getTgLan() {
        return tgLan;
    }

    public void setTgLan(String tgLan) {
        this.tgLan = tgLan;
    }

    public Weather(String tenTP, String tenQG, String nhietDo, String max, String min, String doAm, String tocDoGio, String bauTroi, String apSuat, String may, String tgMoc, String tgLan, String icon) {
        this.tenTP = tenTP;
        this.tenQG = tenQG;
        this.nhietDo = nhietDo;
        this.max = max;
        this.min = min;
        this.doAm = doAm;
        this.tocDoGio = tocDoGio;
        this.bauTroi = bauTroi;
        this.apSuat = apSuat;
        this.may = may;
        this.tgMoc = tgMoc;
        this.tgLan = tgLan;
        this.icon = icon;
    }


    public Weather ()
    {
    }




}
