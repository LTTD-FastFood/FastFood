package com.example.fastfoodapp.Object;

import java.io.Serializable;

public class MonAn implements Serializable {
    private String tenMon;
    private int hinhMon;
    private int calories;
    private Double sao;
    private String size;
    private int gia;
    private int Tong;
    private String moTa;
    private String ingredient;
    private int anhMot;
    private int anhHai;
    private int anhBa;
    private int anhBon;
    private int quantity;

    public MonAn(String tenMon, int hinhMon, int calories, Double sao, String size, int gia, int tong, String moTa, String ingredient, int anhMot, int anhHai, int anhBa, int anhBon, int quantity) {
        this.tenMon = tenMon;
        this.hinhMon = hinhMon;
        this.calories = calories;
        this.sao = sao;
        this.size = size;
        this.gia = gia;
        Tong = tong;
        this.moTa = moTa;
        this.ingredient = ingredient;
        this.anhMot = anhMot;
        this.anhHai = anhHai;
        this.anhBa = anhBa;
        this.anhBon = anhBon;
        this.quantity = quantity;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getHinhMon() {
        return hinhMon;
    }

    public void setHinhMon(int hinhMon) {
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getAnhMot() {
        return anhMot;
    }

    public void setAnhMot(int anhMot) {
        this.anhMot = anhMot;
    }

    public int getAnhHai() {
        return anhHai;
    }

    public void setAnhHai(int anhHai) {
        this.anhHai = anhHai;
    }

    public int getAnhBa() {
        return anhBa;
    }

    public void setAnhBa(int anhBa) {
        this.anhBa = anhBa;
    }

    public int getAnhBon() {
        return anhBon;
    }

    public void setAnhBon(int anhBon) {
        this.anhBon = anhBon;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}