package com.example.testapp

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import android.widget.Toast
import android.text.TextUtils
import com.facebook.react.bridge.Callback
import com.facebook.react.uimanager.IllegalViewOperationException
import java.lang.Exception

class MyNativeModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {
    private val mContext: Context

    init {
        mContext = reactContext
    }

    override fun getName(): String {
        // 返回的这个名字是必须的，在rn代码中需要这个名字来调用该类的方法。
        return "MyNativeModule"
    }

    // 函数不能有返回值，因为被调用的原生代码是异步的，原生代码执行结束之后只能通过回调函数或者发送信息给rn那边。
    @ReactMethod
    fun rnCallNative(msg: String?) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 创建给js调用的方法 将网络请求的结果以回调的方式传递给js
     *
     * @param url
     * @param callback
     */
    @ReactMethod
    fun getResult(url: String?, callback: Callback) {
        Thread {
            try {
                // 模拟网络请求数据的操作
                val result = "我是请求结果"
                callback.invoke(true, result)
            } catch (e: Exception) {
            }
        }.start()
    }

    @ReactMethod
    fun tryCallBack(name: String?, psw: String?,
        errorCallback: Callback, successCallback: Callback) {
        try {
            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(psw)) {
                // 失败时回调
                errorCallback.invoke("user or psw  is empty")
            }
            // 成功时回调
            successCallback.invoke("add user success")
        } catch (e: IllegalViewOperationException) {
            // 失败时回调
            errorCallback.invoke(e.message)
        }
    }

    /**
     * 回调给android端的数据
     *
     * @param callback
     */
    @ReactMethod
    fun renderAndroidData(callback: Callback) {
        callback.invoke("android data")
    }
}