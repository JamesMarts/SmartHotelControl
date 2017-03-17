package hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

import hotel.suozhang.com.menu.http.JsonResponseParser;

/**
 * Created by LIJUWEN on 2016/11/15.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class ClassifyBean implements Serializable {
    public boolean status;
    public List<TngouBean> tngou;
    public static class TngouBean {
        public int cookclass;
        public String description;
        public int id;
        public String keywords;
        public String name;
        public int seq;
        public String title;

        @Override
        public String toString() {
            return "TngouBean{" +
                    "cookclass=" + cookclass +
                    ", description='" + description + '\'' +
                    ", id=" + id +
                    ", keywords='" + keywords + '\'' +
                    ", name='" + name + '\'' +
                    ", seq=" + seq +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ClassifyBean{" +
                "status=" + status +
                ", tngou=" + tngou.toString() +
                '}';
    }
}
