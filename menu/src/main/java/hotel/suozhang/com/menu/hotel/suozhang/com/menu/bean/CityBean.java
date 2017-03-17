package hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

import hotel.suozhang.com.menu.http.JsonResponseParser;

/**
 * Created by LIJUWEN on 2016/11/18.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class CityBean implements Serializable {


    public boolean status;
    public List<ProvinceBean> tngou;

    public static class ProvinceBean {
        public String city;
        public int id;
        public int level;
        public String province;
        public String region;
        public int seq;
        public double x;
        public double y;
        public List<CitysBean> citys;

        public static class CitysBean {
            public String city;
            public int id;
            public int level;
            public String province;
            public int seq;
            public double x;
            public double y;

            @Override
            public String toString() {
                return "CitysBean{" +
                        "city='" + city + '\'' +
                        ", id=" + id +
                        ", level=" + level +
                        ", province='" + province + '\'' +
                        ", seq=" + seq +
                        ", x=" + x +
                        ", y=" + y +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "status=" + status +
                ", tngou=" + tngou .toString()+
                '}';
    }
}
