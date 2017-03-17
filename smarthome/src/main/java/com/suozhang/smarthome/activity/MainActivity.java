package com.suozhang.smarthome.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.suozhang.smarthome.R;
import com.suozhang.smarthome.adapter.MainMenuAdapter;
import com.suozhang.smarthome.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity{
    @ViewInject(R.id.gv_main_menu)
    private GridView gvMainMenu;
    private MainMenuAdapter menuAdapter;

    @Override
    protected void initData() {
        menuAdapter = new MainMenuAdapter(this);
        gvMainMenu.setAdapter(menuAdapter);
    }

}
