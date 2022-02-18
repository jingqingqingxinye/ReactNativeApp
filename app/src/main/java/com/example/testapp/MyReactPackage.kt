package com.example.testapp

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.NativeModule
import com.example.testapp.MyNativeModule
import com.facebook.react.uimanager.ViewManager
import java.util.ArrayList

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
        //返回值需要修改
        return emptyList()
    }
}