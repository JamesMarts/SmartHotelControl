package hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

import hotel.suozhang.com.menu.http.JsonResponseParser;

/**
 * Created by LIJUWEN on 2016/11/16.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class FoodInfoBean implements Serializable {

    public int count;
    public String description;
    public int fcount;
    public String food;
    public int id;
    public String images;
    public String img;
    public String keywords;
    public String message;
    public String name;
    public int rcount;
    public boolean status;
    public String url;


    @Override
    public String toString() {
        return "FoodInfoBean{" +
                "count=" + count +
                ", description='" + description + '\'' +
                ", fcount=" + fcount +
                ", food='" + food + '\'' +
                ", id=" + id +
                ", images='" + images + '\'' +
                ", img='" + img + '\'' +
                ", keywords='" + keywords + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", rcount=" + rcount +
                ", status=" + status +
                ", url='" + url + '\'' +
                '}';
    }
}
