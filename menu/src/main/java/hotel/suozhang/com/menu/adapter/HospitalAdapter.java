package hotel.suozhang.com.menu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.HospitalListBean;
import hotel.suozhang.com.menu.http.HttpUrl;

/**
 * Created by LIJUWEN on 2016/11/18.
 */

public class HospitalAdapter extends BaseAdapter {

    private Context context;
    private HospitalListBean listBean;
    private LayoutInflater inflater;

    public HospitalAdapter(Context context, HospitalListBean listBean) {
        this.context = context;
        this.listBean = listBean;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listBean.tngou.size();
    }

    @Override
    public Object getItem(int position) {
        return listBean.tngou.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_hospital, null);
        ImageView view = (ImageView) convertView.findViewById(R.id.iv_hospital_img);
        TextView name= (TextView) convertView.findViewById(R.id.tv_hospital_name);
        TextView address= (TextView) convertView.findViewById(R.id.tv_hospital_address);
        TextView type= (TextView) convertView.findViewById(R.id.tv_hospital_type);
        TextView level= (TextView) convertView.findViewById(R.id.tv_hospital_level);
        x.image().bind(view, HttpUrl.COOK_IMAGE+listBean.tngou.get(position).img);
        name.setText(listBean.tngou.get(position).name);
        address.setText(listBean.tngou.get(position).address);
        type.setText(listBean.tngou.get(position).mtype);
        if (!TextUtils.isEmpty(listBean.tngou.get(position).level)){
            level.setText(listBean.tngou.get(position).level);
        }else {
            level.setVisibility(View.GONE);
        }
        return convertView;
    }


}
