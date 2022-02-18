package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.react.ReactRootView
import com.facebook.react.ReactInstanceManager
import android.os.Bundle
import com.facebook.soloader.SoLoader
import com.facebook.react.shell.MainReactPackage
import com.example.testapp.MyReactPackage
import com.facebook.react.common.LifecycleState

/**
 * @author jingqingqing
 * @date 2022/2/15
 */
class MyReactActivity : AppCompatActivity(), DefaultHardwareBackBtnHandler {
    private var mReactRootView: ReactRootView? = null
    private var mReactInstanceManager: ReactInstanceManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SoLoader.init(this, false)
        mReactRootView = ReactRootView(this)
        mReactInstanceManager = ReactInstanceManager.builder()
            .setApplication(application)
            .setCurrentActivity(this)
            .setBundleAssetName("index.android.bundle")
            .setJSMainModulePath("index.android")
            .addPackage(MainReactPackage())
            .addPackage(MyReactPackage()) // 将自定义的Package加进来
            .setUseDeveloperSupport(BuildConfig.DEBUG)
            .setInitialLifecycleState(LifecycleState.RESUMED)
            .build()
        // 注意这里的MyReactNativeApp 必须对应"index.android.js"中的
        // "AppRegistry.registerComponent()"的第一个参数
        mReactRootView!!.startReactApplication(mReactInstanceManager, "RNComponent", null)
        setContentView(mReactRootView)
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }
}

// 或者采用这种方式
//public class MyReactActivity extends ReactActivity {
//
//    @Override
//    protected String getMainComponentName() {
//        return "RNComponent";
//    }
//
//}