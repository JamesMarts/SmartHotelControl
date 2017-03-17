package hotel.suozhang.com.menu.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.adapter.MenuAdapter;
import hotel.suozhang.com.menu.base.BaseActivity;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.MenuBean;
import hotel.suozhang.com.menu.http.HttpUrl;
import hotel.suozhang.com.menu.ui.RefreshLayout;
import hotel.suozhang.com.menu.util.L;

@ContentView(R.layout.activity_l_ista_ctivity)
public class ListActivity extends BaseActivity {
    @ViewInject(R.id.spl_menu)
    private RefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.lv_menu_list)
    private ListView listView;
    private MenuBean menuBean;
    private MenuAdapter menuAdapter;
    int id;
    private int mPage = 1;
    private int mSize = 10;

    @Override
    protected void initData() {
        id = this.getIntent().getIntExtra("classiftId", 0);
        getCache(id, mPage, mSize);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCache(id, mPage, mSize);
            }
        });
        swipeRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
                                                 @Override
                                                 public void onLoad() {
                                                     getCache(id, 2, 10);
                                                 }
                                             }
        );
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == menuBean.tngou.size()) {

                } else {
                    Intent intent = new Intent(ListActivity.this, FoodInfoActivity.class);
                    intent.putExtra("foodid", menuBean.tngou.get(position).id);
                    startActivity(intent);
                }

            }
        });
    }

    public void getCache(int id, int page, int rows) {
        RequestParams params = new RequestParams(HttpUrl.COOK_LIST);
        params.addHeader("apikey", HttpUrl.APIKEY);
        params.setCacheMaxAge(1000 * 600);
        params.addBodyParameter("id", id + "");
        params.addBodyParameter("page", page + "");
        params.addBodyParameter("rows", rows + "");
        x.http().get(params, new Callback.CacheCallback<MenuBean>() {
            @Override
            public void onSuccess(MenuBean result) {
                swipeRefreshLayout.setRefreshing(false);
                if (result.status) {
                    menuBean = result;
                    menuAdapter = new MenuAdapter(menuBean.tngou, ListActivity.this);
                    listView.setAdapter(menuAdapter);
                } else {
                    L.e("没有数据");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(CancelledException cex) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFinished() {
                swipeRefreshLayout.setRefreshing(false);
                if (menuBean != null) {
                   // T.showShort("缓存成功！");
                }
            }

            @Override
            public boolean onCache(MenuBean result) {
                menuBean = result;
                return false;
            }
        });
    }
}
