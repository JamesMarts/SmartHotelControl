package com.suozhang.smarthome.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.suozhang.smarthome.R;
import com.suozhang.smarthome.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_model_control)
public class ModelControlActivity extends BaseActivity {
    @ViewInject(R.id.tv_title_name) TextView tvTitleName;



    @Override
    protected void initData() {
        tvTitleName.setText("模式");
    }
}
