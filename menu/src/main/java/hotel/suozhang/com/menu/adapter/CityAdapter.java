package hotel.suozhang.com.menu.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean.CityBean;

/**
 * Created by LIJUWEN on 2016/11/18.
 */

public class CityAdapter extends BaseExpandableListAdapter {
    private List<CityBean.ProvinceBean> provinceBeen;
   private Context context;
        private LayoutInflater layoutInflater;

    public CityAdapter(List<CityBean.ProvinceBean> provinceBeen, Context context) {
        this.provinceBeen = provinceBeen;
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return provinceBeen.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return provinceBeen.get(groupPosition).citys.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return provinceBeen.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return provinceBeen.get(groupPosition).citys.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
         convertView=layoutInflater.inflate(R.layout.item_province,null);
        TextView textView= (TextView) convertView.findViewById(R.id.tv_hospital_province_name);
        textView.setText(provinceBeen.get(groupPosition).province);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        TextView textView=getTextView();
        textView.setText(provinceBeen.get(groupPosition).citys.get(childPosition).city);
        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private TextView getTextView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 110);
        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setTextSize(15f);
        textView.setPadding(66, 10, 0, 10);
        return textView;
    }
}
