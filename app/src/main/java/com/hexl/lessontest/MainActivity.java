package com.hexl.lessontest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.hexl.lessontest.databinding.ActivityMainBinding;
import com.hexl.lessontest.utils.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("lessontest");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        MyListener listener = new MyListener();
        binding.simpleMethod.setOnClickListener(listener);
        binding.overloadMethod.setOnClickListener(listener);
        binding.initMethod.setOnClickListener(listener);
        binding.searchInstance.setOnClickListener(listener);
        binding.prop.setOnClickListener(listener);
        binding.innerClass.setOnClickListener(listener);
        binding.anonymousClass.setOnClickListener(listener);
        binding.allMethod.setOnClickListener(listener);
        binding.dynamicDex.setOnClickListener(listener);
        binding.array.setOnClickListener(listener);
        binding.typeCast.setOnClickListener(listener);
        binding.interfaceImpl.setOnClickListener(listener);


    }

    /**
     * A native method that is implemented by the 'myapplication' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


    public void testStaticForLoadClass(){
        try {
            LogUtils.info("onCreate: before loadClass");
            Class<?> test = getClassLoader().loadClass("com.hexl.lessontest.Test");
            LogUtils.info("onCreate: after loadClass");
            Constructor<?> constructor = test.getDeclaredConstructor();
            LogUtils.info("onCreate: after getDeclaredConstructor");
            constructor.newInstance();
            LogUtils.info("onCreate: after newInstance");
            Method print = test.getDeclaredMethod("print", String.class);
            LogUtils.info("onCreate: after getDeclaredMethod");
            Object result = print.invoke(null, "aaaaa");
            LogUtils.info("onCreate: after invoke " + result);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}