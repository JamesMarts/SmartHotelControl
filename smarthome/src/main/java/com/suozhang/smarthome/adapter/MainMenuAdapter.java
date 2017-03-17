package com.suozhang.smarthome.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.suozhang.smarthome.R;
import com.suozhang.smarthome.activity.AirConditionControlActivity;
import com.suozhang.smarthome.activity.LigthControlActivity;
import com.suozhang.smarthome.activity.ModelControlActivity;
import com.suozhang.smarthome.activity.OtherControlActivity;
import com.suozhang.smarthome.activity.ServiceControlActivity;
import com.suozhang.smarthome.activity.TVControlActivity;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijuwen on 2017/1/6.
 */

public class MainMenuAdapter extends BaseAdapter {
    private List<MainMenu> mDatas = getMenu();
    private LayoutInflater mInflater;
    private Context mContext;

    public MainMenuAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView =mInflater.inflate(R.layout.item_main_menu,null);
        ImageView ivImage= (ImageView) convertView.findViewById(R.id.iv_menu_icon);
        TextView tvName= (TextView) convertView.findViewById(R.id.tv_menu_name);
        ivImage.setImageResource(mDatas.get(position).imgRes);
        tvName.setText(mDatas.get(position).name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,mDatas.get(position).resultClass);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    public class MainMenu {
        Class<?> resultClass;
        String name;
        int imgRes;

        public MainMenu(Class<?> resultClass, String name, int imgRes) {
            this.resultClass = resultClass;
            this.name = name;
            this.imgRes = imgRes;
        }
    }


    public List<MainMenu> getMenu() {
        List<MainMenu> mainMenus = new ArrayList<MainMenu>();
        mainMenus.add(new MainMenu(TVControlActivity.class, "电视", R.drawable.main_menu_tv));
        mainMenus.add(new MainMenu(AirConditionControlActivity.class, "空调", R.drawable.main_menu_air_condition));
        mainMenus.add(new MainMenu(LigthControlActivity.class, "灯光", R.drawable.main_menu_light));
        mainMenus.add(new MainMenu(ServiceControlActivity.class, "服务", R.drawable.main_menu_service));
        mainMenus.add(new MainMenu(ModelControlActivity.class, "模式", R.drawable.main_menu_model));
        mainMenus.add(new MainMenu(OtherControlActivity.class, "其他", R.drawable.main_menu_other));
        return mainMenus;
    }
}
