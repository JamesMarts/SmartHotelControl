package hotel.suozhang.com.menu.activity;

import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.base.BaseActivity;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.HospitalBean;
import hotel.suozhang.com.menu.http.HttpUrl;
import hotel.suozhang.com.menu.util.L;
import hotel.suozhang.com.menu.util.T;

@ContentView(R.layout.activity_hosptial_info)
public class HosptialInfoActivity extends BaseActivity {
    private int hospitalId;
    @ViewInject(R.id.tv_hosptial_info_name)
   private TextView tvName;
    @ViewInject(R.id.iv_hosptial_info)
    private ImageView ivImage;
    @Override
    protected void initData() {
        hospitalId = this.getIntent().getIntExtra("hosid", 0);
        getData(hospitalId);
    }

    private void getData(int id) {
        RequestParams params = new RequestParams(HttpUrl.GET_HOSPITAL_DETAIL);
        params.addHeader("apikey", HttpUrl.APIKEY);
        params.addBodyParameter("id", id + "");
        x.http().get(params, new Callback.CommonCallback<HospitalBean>() {
            @Override
            public void onSuccess(HospitalBean result) {
              if (result.status){
                  tvName.setText(result.name);
                  x.image().bind(ivImage,HttpUrl.COOK_IMAGE+result.img);
              }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                L.e(ex + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                T.showShort("加载成功！");
            }
        });
    }



}
