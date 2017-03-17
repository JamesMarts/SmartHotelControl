package hotel.suozhang.com.menu.activity;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hotel.suozhang.com.menu.adapter.ClassifyAdapter;
import hotel.suozhang.com.menu.http.HttpUrl;
import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.util.L;
import hotel.suozhang.com.menu.util.T;
import hotel.suozhang.com.menu.base.BaseActivity;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.ClassifyBean;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.spl_classify)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.lv_classify_list)
    private ListView listView;
    private ClassifyAdapter classifyAdapter;
   private ClassifyBean classifyBean;


    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,ListActivity.class);
                intent.putExtra("classiftId",classifyBean.tngou.get(position).id);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        RequestParams params = new RequestParams(HttpUrl.COOK_CLASS);
        params.addHeader("apikey", HttpUrl.APIKEY);
        params.addBodyParameter("id", 0 + "");
        x.http().get(params, new Callback.CommonCallback<ClassifyBean>() {
            @Override
            public void onSuccess(ClassifyBean result) {
                swipeRefreshLayout.setRefreshing(false);
                T.showLong("加载成功！");
                if (result.status) {
                    classifyBean=result;
                    classifyAdapter = new ClassifyAdapter(classifyBean.tngou, MainActivity.this);
                    listView.setAdapter(classifyAdapter);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                swipeRefreshLayout.setRefreshing(false);
                //Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    L.e(responseCode+responseMsg+errorResult);
                    // ...
                } else { // 其他错误
                    // ...
                    L.e(ex+"");
                }

                //txtShowResult.setText(ex + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFinished() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
