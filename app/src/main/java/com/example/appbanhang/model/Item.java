package com.example.appbanhang.model;

public class Item {
    int idsp;
    String tensp;
    int soluong;
    long gia;
    String hinhanh;

    public Item(int idsp, String tensp, int soluong, long gia, String hinhanh) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.soluong = soluong;
        this.gia = gia;
        this.hinhanh = hinhanh;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}