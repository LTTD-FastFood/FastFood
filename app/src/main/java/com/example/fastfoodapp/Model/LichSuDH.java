package com.example.fastfoodapp.Model;

import java.io.Serializable;

public class LichSuDH implements Serializable {

    private int MaHD;
    private String TenKH;
    private String SDT;
    private String DiaChi;
    private String NgayBan;
    private int DonGia;

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }

    public String getNgayBan() {
        return NgayBan;
    }

    public void setNgayBan(String ngayBan) {
        NgayBan = ngayBan;
    }

    public int getDonGia() {
        return DonGia;
    }

    public void setDonGia(int donGia) {
        DonGia = donGia;
    }
}