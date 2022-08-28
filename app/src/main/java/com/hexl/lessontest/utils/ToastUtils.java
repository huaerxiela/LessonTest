package com.hexl.lessontest.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ToastUtils {
    @SuppressLint("StaticFieldLeak")
    public static Context context;

    public static Context getContext(){
        if (context == null){
            try {
                @SuppressLint("PrivateApi") Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
                @SuppressLint("DiscouragedPrivateApi") Method currentApplicationMethod = ActivityThread.getDeclaredMethod("currentApplication");
                Application currentApplication = (Application) currentApplicationMethod.invoke(null);
                assert currentApplication != null;
                context = currentApplication.getApplicationContext();
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return context;
    }
    public static void showToast(String msg){
        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show());
    }
}
