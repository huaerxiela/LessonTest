package com.hexl.lessontest.logic;

import com.hexl.lessontest.utils.LogUtils;

public class People extends Monkey{
    public static String hello = "world";
    public static boolean speak = true;
    public static boolean run = true;
    public int age;
    public String name;
    public int height = 180;

    public People() {
        LogUtils.info("People()");
    }

    public People(int age) {
        this.age = age;
        LogUtils.info("People(int age)");
    }

    public People(String name) {
        this.name = name;
        LogUtils.info("People(String name)");
    }

    public People(int age, String name) {
        this.age = age;
        this.name = name;
        LogUtils.info("People(int age, String name)");
    }

    @Override
    public boolean run(String type) {
        LogUtils.info("People run by " + type);
        return true;
    }

    public boolean run() {
        LogUtils.info("People run");
        return true;
    }

    public static void speak(){
        LogUtils.info("People speak");
    }

    public static void speak(String msg){
        LogUtils.info("People speak msg: " + msg);
    }

    public static void speak(String msg, int num){
        LogUtils.info(String.format("People speak msg: %s , num: %s", msg, num));
    }

    public static class Child{
        public boolean run() {
            LogUtils.info("Child run");
            return true;
        }
    }
}
