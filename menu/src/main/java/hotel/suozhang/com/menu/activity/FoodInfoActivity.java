package hotel.suozhang.com.menu.activity;

import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import hotel.suozhang.com.menu.http.HttpUrl;
import hotel.suozhang.com.menu.util.L;
import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.base.BaseActivity;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.FoodInfoBean;

@ContentView(R.layout.activity_food_info)
public class FoodInfoActivity extends BaseActivity {
    private int foodid;
    @ViewInject(R.id.tv_info_name)
    private TextView tvName;
    @ViewInject(R.id.tv_info_des)
    private TextView tvDes;
    @ViewInject(R.id.tv_info_keywords)
    private TextView tvKey;
    @ViewInject(R.id.iv_food_img)
    private ImageView ivFood;

    @Override
    protected void initData() {
        foodid = this.getIntent().getIntExtra("foodid", 0);
        getData(foodid);

    }

    private void getData(int id) {
        RequestParams params = new RequestParams(HttpUrl.FOOD_INFO);
        params.addHeader("apikey", HttpUrl.APIKEY);
        params.addBodyParameter("id", id + "");

        x.http().get(params, new Callback.CommonCallback<FoodInfoBean>() {
            @Override
            public void onSuccess(FoodInfoBean result) {

                if (result.status) {
                    init(result);
                } else {
                    L.e("没有数据");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                //Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    // ...
                } else { // 其他错误
                    // ...
                }
                L.e(ex + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void init(FoodInfoBean infoBean) {
        tvName.setText(infoBean.name);
        tvDes.setText("\t\t"+Html.fromHtml(infoBean.description));
        tvKey.setText(infoBean.keywords);
        x.image().bind(ivFood, HttpUrl.COOK_IMAGE + infoBean.img);
    }
}
