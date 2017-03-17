package hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

import hotel.suozhang.com.menu.http.JsonResponseParser;

/**
 * Created by LIJUWEN on 2016/11/15.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class MenuBean implements Serializable {
    public boolean status;
    public int total;
    public List<TngouBean> tngou;
    public static class TngouBean {
        public int count;
        public String description;
        public int fcount;
        public String food;
        public int id;
        public String images;
        public String img;
        public String keywords;
        public String name;
        public int rcount;
    }
}
