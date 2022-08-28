package com.hexl.lessontest;

import android.annotation.SuppressLint;
import android.view.View;

import com.hexl.lessontest.utils.ToastUtils;

public class MyListener implements View.OnClickListener{
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.simple_method:
                ToastUtils.showToast("simple_method");
                break;
            case R.id.overload_method:
                ToastUtils.showToast("overload_method");
                break;
            case R.id.init_method:
                ToastUtils.showToast("init_method");
                break;
            case R.id.search_instance:
                ToastUtils.showToast("search_instance");
                break;
            case R.id.prop:
                ToastUtils.showToast("prop");
                break;
            case R.id.inner_class:
                ToastUtils.showToast("inner_class");
                break;
            case R.id.anonymous_class:
                ToastUtils.showToast("anonymous_class");
                break;
            case R.id.all_method:
                ToastUtils.showToast("all_method");
                break;
            case R.id.dynamic_dex:
                ToastUtils.showToast("dynamic_dex");
                break;
            case R.id.array:
                ToastUtils.showToast("array");
                break;
            case R.id.type_cast:
                ToastUtils.showToast("type_cast");
                break;
            case R.id.interface_impl:
                ToastUtils.showToast("interface_impl");
                break;
            default:
                ToastUtils.showToast("111111");
                break;
        }
    }
}
