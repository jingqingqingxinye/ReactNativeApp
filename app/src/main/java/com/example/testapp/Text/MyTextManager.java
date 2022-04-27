package com.example.testapp.Text;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * @author jingqingqing
 * @date 2022/3/23
 */
public class MyTextManager extends SimpleViewManager<MyTextView> {
    // 注意这个名字，必须与 JS 端 require 的对应
    public static final String REACT_CLASS = "RCTMyText";
    private Context context;

    public MyTextManager(ReactApplicationContext context) {
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected MyTextView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new MyTextView(reactContext);
    }

    @Override
    public void onDropViewInstance(@NonNull MyTextView view) {
//        super.onDropViewInstance(view);
        view = null;
    }

    /**
     * 第二个参数来自JS端，改变这个参数，TextView上的圆将会变化
     */
    @ReactProp(name = "radius")
    public void setRadius(MyTextView textView, int radius) {
        textView.setRadius(radius);
    }

    @ReactProp(name = "text")
    public void setText(MyTextView textView, String text) {
        textView.setText(text);
    }

    @ReactProp(name = "gravity")
    public void setAlignType(MyTextView textView, String gravity) {
        // String type = gravity.split("\\|")[0];
        if("centerVertical".equals(gravity)) {
            textView.setGravity(Gravity.CENTER_VERTICAL);
        }
    }

    @ReactProp(name = "fontSize")
    public void setTextSize(MyTextView textView, int size) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }
}