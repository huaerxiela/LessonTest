package com.hexl.lessontest.logic;

import com.hexl.lessontest.utils.LogUtils;

public class AnonymousDemo {
    public static void createClass() {

        // 匿名类实现一个接口
        IAnimal p1 = new IAnimal() {

            @Override
            public boolean run(String type) {
                LogUtils.info("AnonymousDemo run " + type);
                return false;
            }
        };
        p1.run("匿名类");
    }
}
