package com.hexl.lessontest;

import android.util.Log;

public class Test {
    private static final String TAG = "Test";

    static {
        Log.i(TAG, "print: static{}");
    }

    public static String print(String name){
        Log.i(TAG, "print: " + name);
        return name;
    }
}
