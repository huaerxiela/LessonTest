package com.hexl.lessontest.logic;


import com.hexl.lessontest.utils.LogUtils;

public class Monkey implements IAnimal{
    @Override
    public boolean run(String type) {
        LogUtils.info("Monkey run by " + type);
        return true;
    }

    public boolean peach() {
        LogUtils.info("Monkey peach");
        return true;
    }


}
