package hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

import hotel.suozhang.com.menu.http.JsonResponseParser;

/**
 * Created by LIJUWEN on 2016/11/18.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class HospitalListBean implements Serializable {
    public boolean status;
    public int total;
    public List<TngouBean> tngou;
    public static class TngouBean {
        public String address;
        public int area;
        public int count;
        public String fax;
        public int fcount;
        public String gobus;
        public int id;
        public String img;
        public String level;
        public String mail;
        public String mtype;
        public String name;
        public String nature;
        public int rcount;
        public String tel;
        public String url;
        public double x;
        public double y;
        public String zipcode;


    }


    @Override
    public String toString() {
        return "HospitalListBean{" +
                "status=" + status +
                ", total=" + total +
                ", tngou=" + tngou +
                '}';
    }
}
