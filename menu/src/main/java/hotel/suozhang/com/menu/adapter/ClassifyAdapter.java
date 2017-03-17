package hotel.suozhang.com.menu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.ClassifyBean;

/**
 * Created by LIJUWEN on 2016/11/15.
 */

public class ClassifyAdapter extends BaseAdapter {
    private List<ClassifyBean.TngouBean> tngouBeen;
    private LayoutInflater layoutInflater;
    private Context context;

    public ClassifyAdapter(List<ClassifyBean.TngouBean> tngouBeen, Context context) {
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
        convertView=layoutInflater.inflate(R.layout.item_classify,null);
        TextView txtxName= (TextView) convertView.findViewById(R.id.tv_classify_name);
        txtxName.setText(tngouBeen.get(position).name);
        return convertView;
    }



}
