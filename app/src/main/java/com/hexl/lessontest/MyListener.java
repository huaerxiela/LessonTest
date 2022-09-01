package com.hexl.lessontest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.hexl.lessontest.logic.AnonymousDemo;
import com.hexl.lessontest.logic.IAnimal;
import com.hexl.lessontest.logic.Monkey;
import com.hexl.lessontest.logic.People;
import com.hexl.lessontest.utils.LogUtils;
import com.hexl.lessontest.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import dalvik.system.DexClassLoader;
import dalvik.system.InMemoryDexClassLoader;

public class MyListener implements View.OnClickListener{
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.simple_method:
                People.speak();
                new People().run("no init");
                ToastUtils.showToast("simple_method");
                break;
            case R.id.overload_method:
                People.speak();
                People.speak("ooo");
                People.speak("xxx", 1);
                ToastUtils.showToast("overload_method");
                break;
            case R.id.init_method:
                new People();
                new People(18);
                new People("隔壁老花");
                new People(18, "隔壁老花");
                ToastUtils.showToast("init_method");
                break;
            case R.id.search_instance:
                new People();
                new People(13);
                new People("隔壁小花");
                new People(13, "隔壁小花");
                ToastUtils.showToast("search_instance");
                break;
            case R.id.prop:
                People.hello = "world".toUpperCase();
                People.speak = false;
                new People(13).age = 18;
                ToastUtils.showToast("prop");
                break;
            case R.id.inner_class:
                new People.Child().run();
                ToastUtils.showToast("inner_class");
                break;
            case R.id.anonymous_class:
                AnonymousDemo.createClass();
                ToastUtils.showToast("anonymous_class");
                break;
            case R.id.all_method:
                People.speak();
                People.speak("ooo");
                People.speak("xxx", 1);
                ToastUtils.showToast("all_method");
                break;
            case R.id.dynamic_dex:
                loadDex(ToastUtils.getContext(), new People(20, "隔壁老花"));
                ToastUtils.showToast("dynamic_dex");
                break;
            case R.id.array:
                ToastUtils.showToast("array");
                break;
            case R.id.type_cast:
                IAnimal xiaoHua = new People(15, "隔壁小花");
                LogUtils.info("xiaoHua.getClass() = " + xiaoHua.getClass());
                xiaoHua.run("xiaoHua");
//                xiaoHua.peach();
                ((People)xiaoHua).peach();
                ToastUtils.showToast("type_cast");
                break;
            case R.id.interface_impl:
                testInterface(new People());
                ToastUtils.showToast("interface_impl");
                break;
            default:
                ToastUtils.showToast("111111");
                break;
        }
    }

    public void testInterface(IAnimal animal){
        LogUtils.info("animal.getClass() = " + animal.getClass());
        animal.run("testInterface");
    }

    public void loadDex(Context context, Object object){
        File cacheFile = context.getApplicationContext().getCacheDir();
        String internalPath = cacheFile.getAbsolutePath() + File.separator + "gson_dex.dex";
        File desFile = new File(internalPath);
        try {
            if (!desFile.exists()) {
                desFile.createNewFile();
                copyFiles(context,"gson.dex", desFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DexClassLoader dexClassLoader = new DexClassLoader(internalPath, cacheFile.getAbsolutePath(), null, context.getClassLoader());

        try {
            Class<?> clazz = dexClassLoader.loadClass("com.google.gson.Gson");
            Method method = clazz.getMethod("toJson", Object.class);
            Object info = method.invoke(clazz.newInstance(), object);
            LogUtils.info("new Gson().toJson = " + info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyFiles(Context context, String fileName, File desFile) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = context.getApplicationContext().getAssets().open(fileName);
            out = new FileOutputStream(desFile.getAbsolutePath());
            byte[] bytes = new byte[1024];
            int i;
            while ((i = in.read(bytes)) != -1)
                out.write(bytes, 0, i);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
