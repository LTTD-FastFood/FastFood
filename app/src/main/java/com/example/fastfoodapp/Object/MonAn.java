package com.example.fastfoodapp.Object;

import java.io.Serializable;

public class MonAn  implements Serializable {

    private String tenMon;
    private String hinhMon;
    private int calories;
    private Double sao;
    private int gia;
    private int Tong;
    private String moTa;
    private String nguyenlieu;
    private int soluong;

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getHinhMon() {
        return hinhMon;
    }

    public void setHinhMon(String hinhMon) {
        this.hinhMon = hinhMon;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Double getSao() {
        return sao;
    }

    public void setSao(Double sao) {
        this.sao = sao;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getTong() {
        return Tong;
    }

    public void setTong(int tong) {
        Tong = tong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getnguyenlieu() {
        return nguyenlieu;
    }

    public void setnguyenlieu(String nguyenlieu) {
        this.nguyenlieu = nguyenlieu;
    }

    public int getsoluong() {
        return soluong;
    }

    public void setsoluong(int soluong) {
        this.soluong = soluong;
    }
}