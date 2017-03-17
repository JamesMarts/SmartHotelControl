package hotel.suozhang.com.menu.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import org.xutils.x;

/**
 * Created by LIJUWEN on 2016/11/15.
 */

public abstract  class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
//        getSupportActionBar().hide();//隐藏标题栏
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不弹出输入法键盘
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置屏幕方向为横屏
        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化视图
     */
    protected  void initView() {
    }


    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }
}
