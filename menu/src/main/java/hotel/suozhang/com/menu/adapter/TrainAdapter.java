package hotel.suozhang.com.menu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.TicketBean;

/**
 * Created by LIJUWEN on 2016/11/17.
 */

public class TrainAdapter extends BaseAdapter {
    private List<TicketBean.DataBean.TrainListBean> tngouBeen;
    private LayoutInflater layoutInflater;
    private Context context;

    public TrainAdapter(List<TicketBean.DataBean.TrainListBean> tngouBeen, Context context) {
        this.tngouBeen = tngouBeen;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tngouBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return tngouBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_ticket, null);
        TextView bdgin = (TextView) convertView.findViewById(R.id.tv_train_begin_date);
        TextView end = (TextView) convertView.findViewById(R.id.tv_train_end_date);
        TextView from = (TextView) convertView.findViewById(R.id.tv_train_from_city);
        TextView to = (TextView) convertView.findViewById(R.id.tv_train_to_city);
        TextView no = (TextView) convertView.findViewById(R.id.tv_train_no);
        TextView duration = (TextView) convertView.findViewById(R.id.tv_train_duration);
        TextView price = (TextView) convertView.findViewById(R.id.tv_train_price);
        bdgin.setText(tngouBeen.get(position).startTime);
        end.setText(tngouBeen.get(position).endTime);
        from.setText(tngouBeen.get(position).from);
        to.setText(tngouBeen.get(position).to);
        no.setText(tngouBeen.get(position).trainNo);
        duration.setText(tngouBeen.get(position).duration);
        price.setText("￥" + tngouBeen.get(position).seatInfos.get(0).seatPrice);


        return convertView;
    }
}
