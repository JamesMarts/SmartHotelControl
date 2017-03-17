package hotel.suozhang.com.menu.base;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

import org.xutils.x;

import hotel.suozhang.com.menu.BuildConfig;
import hotel.suozhang.com.menu.util.L;

/**
 * Created by LIJUWEN on 2016/11/15.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        L.setDebug(true);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
        ApiStoreSDK.init(this,"4714dccdf4ae87305663a773226bd37f");
    }
}
