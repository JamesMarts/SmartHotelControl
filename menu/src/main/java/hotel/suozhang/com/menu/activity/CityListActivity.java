package hotel.suozhang.com.menu.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.adapter.CityAdapter;
import hotel.suozhang.com.menu.base.BaseActivity;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.CityBean;
import hotel.suozhang.com.menu.http.HttpUrl;
import hotel.suozhang.com.menu.util.L;
import hotel.suozhang.com.menu.util.T;

@ContentView(R.layout.activity_city_list)
public class CityListActivity extends BaseActivity {
    private CityAdapter cityAdapter;
    private CityBean cityBean;
    @ViewInject(R.id.expandlist)
    private ExpandableListView expandableListView;

    @Override
    protected void initData() {
        getCity();
    }

    @Override
    protected void initEvent() {
        super.initEvent();


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent=new Intent(CityListActivity.this,HospitalListActivity.class);
                intent.putExtra("hid",cityBean.tngou.get(groupPosition).citys.get(childPosition).id);
                startActivity(intent);
                return false;
            }
        });
    }

    public void getCity() {
        RequestParams params = new RequestParams(HttpUrl.GET_ALL_CITY);
        params.addHeader("apikey", HttpUrl.APIKEY);
        params.addBodyParameter("type", "all");
        x.http().get(params, new Callback.CommonCallback<CityBean>() {


            @Override
            public void onSuccess(CityBean result) {
                L.e(result.toString());
                if (result.status) {
                    cityBean=result;
                    cityAdapter = new CityAdapter(cityBean.tngou, CityListActivity.this);
                    expandableListView.setAdapter(cityAdapter);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
