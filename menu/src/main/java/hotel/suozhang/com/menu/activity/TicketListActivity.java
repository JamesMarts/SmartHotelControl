package hotel.suozhang.com.menu.activity;

import android.widget.ListView;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import hotel.suozhang.com.menu.http.HttpUrl;
import hotel.suozhang.com.menu.util.L;
import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.adapter.TrainAdapter;
import hotel.suozhang.com.menu.base.BaseActivity;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.TicketBean;

@ContentView(R.layout.activity_ticket_list)
public class TicketListActivity extends BaseActivity {
    @ViewInject(R.id.lv_ticket)
    private ListView listView;
    private TrainAdapter trainAdapter;
    private String from = "";
    private String to = "";
    private String date = "";
    private List<TicketBean.DataBean.TrainListBean> tngouBeen;
    @Override
    protected void initData() {


        from = this.getIntent().getStringExtra("from");
        to = this.getIntent().getStringExtra("to");
        date = this.getIntent().getStringExtra("date");
        getData(from, to, date);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }


    private void getData(String from, String to, String date) {

//        from = URLEncoder.encode(from, "utf-8");
//        to = URLEncoder.encode(to, "utf-8");
        RequestParams params = new RequestParams(HttpUrl.TICKET_INFO);
        params.addHeader("apikey", HttpUrl.APIKEY);
        params.addBodyParameter("version", 1.0 + "");
        params.addBodyParameter("from", from);
        params.addBodyParameter("to", to);
        params.addBodyParameter("date", date);

        x.http().get(params, new Callback.CommonCallback<TicketBean>() {
            @Override
            public void onSuccess(TicketBean result) {
                if (result.ret) {
                    trainAdapter=new TrainAdapter(result.data.trainList,TicketListActivity.this);
                    listView.setAdapter(trainAdapter);
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


}
