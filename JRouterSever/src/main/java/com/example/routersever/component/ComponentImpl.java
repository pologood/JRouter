package com.example.routersever.component;

import android.support.annotation.NonNull;

import com.example.routersever.component.IComponent.IComponent;
import com.example.routersever.controller.cache.CacheFactoryImpl;
import com.example.routersever.interfaces.IRouterApplication;
import com.example.routersever.util.ExceptionUtil;

import java.util.HashMap;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 10:35.
 * Function : 组件管理类
 */
class ComponentImpl implements IComponent {

    private HashMap<String, IRouterApplication> mComponentsWapper = new HashMap<>();

    private ComponentImpl() {
    }

    private static class Factory {
        private static final ComponentImpl mInstance = new ComponentImpl();
    }

    public static IComponent getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void RegisterComponent(@NonNull String componentName) {
        if (mComponentsWapper.containsKey(componentName)) {
            ExceptionUtil.Runtime("the " + componentName + " is Registered");
        }
        try {
            Class aClass = Class.forName(componentName);
            Object o=aClass.newInstance();
            //TODO 检查是否实现了IRouterApplication接口
//            if (o instanceof IRouterApplication){
//
//            }
            IRouterApplication iApplication = (IRouterApplication) o;
            iApplication.onCreate(CacheFactoryImpl.getFactory().getContext().get());
            mComponentsWapper.put(componentName, iApplication);
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        ExceptionUtil.IllegalArgument("please check the componentName");
    }

    @Override
    public void unRegisterComponent(@NonNull String componentName) {
        if (mComponentsWapper.containsKey(componentName)) {
            IRouterApplication iApplication = mComponentsWapper.get(componentName);
            iApplication.onDestory();
            mComponentsWapper.remove(componentName);
        } else {
            ExceptionUtil.Runtime("first you need to register " + componentName);
        }
    }
}
