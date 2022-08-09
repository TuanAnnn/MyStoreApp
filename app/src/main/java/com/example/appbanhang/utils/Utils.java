package com.example.appbanhang.utils;

import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    //public static final String link ="tuananvt173.tk";
public static final String link ="192.168.0.107";
    public static final String BASE_URL="http://"+link+"/banhang/";
    public static final String BASE_FCM="https://fcm.googleapis.com/";

    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current= new User();
}
