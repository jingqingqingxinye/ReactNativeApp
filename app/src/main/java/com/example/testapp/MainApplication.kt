package com.example.testapp

import android.app.Application
import com.facebook.soloader.SoLoader

/**
 *
 * @author jingqingqing
 * @date 2022/2/17
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
    }
}