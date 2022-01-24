package com.shenyutao.mylibrary;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


import me.weishu.reflection.Reflection;

/**
 * @author Shzy
 */
public class IgnoreUiUpdateCheck {
    private static volatile IgnoreUiUpdateCheck mInstance = null;
    public int replace;

    private Object viewRootImpl;
    private Method getViewRootImplMethod;
    private Field mThreadField;
    private Field modifiersField;
    private Object replaceFieldModifiers;
    private Object originalMThreadObject;


    private IgnoreUiUpdateCheck() {
    }

    public static void freeReflection(Context base){
        Reflection.unseal(base);
    }

    public static IgnoreUiUpdateCheck getInstance() {
        if (mInstance == null) {
            synchronized (IgnoreUiUpdateCheck.class){
                if(mInstance == null){
                    mInstance = new IgnoreUiUpdateCheck();
                    mInstance.init();
                }
            }
        }
        return mInstance;
    }

    private void init() {
        try{
            // ViewRootImpl类
            Class<?> viewRootImplClass = Class.forName("android.view.ViewRootImpl");
            // ViewRootImpl的mThread属性
            mThreadField = viewRootImplClass.getDeclaredField("mThread");
            mThreadField.setAccessible(true);

            // getViewRootImpl方法
            getViewRootImplMethod = View.class.getDeclaredMethod("getViewRootImpl");
            getViewRootImplMethod.setAccessible(true);

            // modifiersField
            modifiersField = Field.class.getDeclaredField("accessFlags");
            modifiersField.setAccessible(true);
            Field replaceField = IgnoreUiUpdateCheck.class.getDeclaredField("replace");
            replaceFieldModifiers = modifiersField.get(replaceField);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void changedViewRootImplThread(Activity activity,Thread currentThread) {
        try {
            // 获取activity的decorView
            View decorView = activity.getWindow().getDecorView();
            // 获取到ViewRootImpl实例
            viewRootImpl = getViewRootImplMethod.invoke(decorView);
            // 将mThread字段的final属性去掉
            modifiersField.set(mThreadField, replaceFieldModifiers);
            // 记录源Thread
            originalMThreadObject = mThreadField.get(viewRootImpl);
            // 替换成当前线程
            mThreadField.set(viewRootImpl, currentThread);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void restoreViewRootImplOriginThread(){
        try {
            mThreadField.set(viewRootImpl, originalMThreadObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
