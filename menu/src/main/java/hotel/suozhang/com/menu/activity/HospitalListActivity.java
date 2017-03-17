package hotel.suozhang.com.menu.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.adapter.HospitalAdapter;
import hotel.suozhang.com.menu.base.BaseActivity;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.HospitalListBean;
import hotel.suozhang.com.menu.http.HttpUrl;
import hotel.suozhang.com.menu.util.L;
import hotel.suozhang.com.menu.util.T;

@ContentView(R.layout.activity_hospital_list)
public class HospitalListActivity extends BaseActivity {
    @ViewInject(R.id.lv_hospatial)
    ListView lvHospital;
    private int hid;
    private HospitalListBean hospitalListBean;
    private HospitalAdapter hospitalAdapter;

    @Override
    protected void initData() {
        hid = this.getIntent().getIntExtra("hid", 0);
        getHositals(hid);
    }

    public void getHositals(int hid) {
        RequestParams params = new RequestParams(HttpUrl.GET_HOSPITAL);
        params.addHeader("apikey", HttpUrl.APIKEY);
        params.addBodyParameter("id", hid + "");
        params.addBodyParameter("page", 1 + "");
        params.addBodyParameter("rows", 1000 + "");
        x.http().get(params, new Callback.CommonCallback<HospitalListBean>() {


            @Override
            public void onSuccess(HospitalListBean result) {
                L.e(result.toString());
                if (result.status) {
                    hospitalListBean = result;
                    hospitalAdapter = new HospitalAdapter(HospitalListActivity.this, hospitalListBean);
                    lvHospital.setAdapter(hospitalAdapter);
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

    @Override
    protected void initEvent() {
        lvHospital.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HospitalListActivity.this, HosptialInfoActivity.class);
                intent.putExtra("hosid", hospitalListBean.tngou.get(position).id);
                startActivity(intent);
            }
        });
    }
}
