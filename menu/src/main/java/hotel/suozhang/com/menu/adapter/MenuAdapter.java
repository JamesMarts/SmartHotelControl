package hotel.suozhang.com.menu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import hotel.suozhang.com.menu.http.HttpUrl;
import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.MenuBean;

/**
 * Created by LIJUWEN on 2016/11/15.
 */

public class MenuAdapter extends BaseAdapter {
    private List<MenuBean.TngouBean> tngouBeen;
    private LayoutInflater layoutInflater;
    private Context context;

    public MenuAdapter(List<MenuBean.TngouBean> tngouBeen, Context context) {
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
        convertView=layoutInflater.inflate(R.layout.item_menu,null);
        TextView txtxName= (TextView) convertView.findViewById(R.id.tv_menu_name);
        TextView txtxDes= (TextView) convertView.findViewById(R.id.tv_menu_des);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.iv_menu);
        txtxName.setText(tngouBeen.get(position).name);
        txtxDes.setText(tngouBeen.get(position).keywords);
        x.image().bind(imageView, HttpUrl.COOK_IMAGE+tngouBeen.get(position).img);
        return convertView;
    }



}
