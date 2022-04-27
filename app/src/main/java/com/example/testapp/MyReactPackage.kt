package com.example.testapp

import com.example.testapp.Text.MyTextManager
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

/**
 * @author jingqingqing
 * @date 2022/2/17
 */
class MyReactPackage : ReactPackage {
    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        val modules: MutableList<NativeModule> = ArrayList()
        //将我们创建的类添加进原生模块列表中
        modules.add(MyNativeModule(reactContext))
        return modules
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        val modules: MutableList<ViewManager<*, *>> = ArrayList()
        modules.add(MyTextManager(reactContext))
        return modules
    }
}