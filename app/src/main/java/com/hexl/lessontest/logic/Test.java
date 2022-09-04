package com.hexl.lessontest.logic;


import com.hexl.lessontest.utils.LogUtils;

import java.util.Arrays;

enum Signal {
    GREEN, YELLOW, RED
}

public class Test {
    public static Signal signal = Signal.RED;

    public static void change() {
        LogUtils.info("before enum " + signal);
        LogUtils.info("before enum " + signal.getClass().getName());
        switch (signal) {
            case RED:
                signal = Signal.GREEN;
                break;
            case YELLOW:
                signal = Signal.RED;
                break;
            case GREEN:
                signal = Signal.YELLOW;
                break;
        }
        LogUtils.info("after enum " + signal);
        LogUtils.info("after enum " + signal.getClass().getName());

    }
    
    public static void printArray(int[] param){
        LogUtils.info("printArray(int[] param) = " + param);
        LogUtils.info("printArray(int[] param) = " + Arrays.toString(param));
    }
    public static void printArray(char[] param){
        LogUtils.info("printArray(char param) = " + param[0]);
        LogUtils.info("printArray(char param) = " + Character.toString(param[0]));
        LogUtils.info("printArray(char[] param) = " + param);
        LogUtils.info("printArray(char[] param) = " + Arrays.toString(param));
    }
    public static void printArray(byte[] param){
        LogUtils.info("printArray(byte[] param) = " + param);
        LogUtils.info("printArray(char[] param) = " + Arrays.toString(param));
    }
    public static void printArray(String[] param){
        LogUtils.info("printArray(String[] param) = " + param);
        LogUtils.info("printArray(char[] param) = " + Arrays.toString(param));
    }
    public int ֏(int x) {
        return x + 100;
    }
}
