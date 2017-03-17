package hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

import hotel.suozhang.com.menu.http.JsonResponseParser;

/**
 * Created by LIJUWEN on 2016/11/23.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class HospitalBean implements Serializable {

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
    public String message;
    public String mtype;
    public String name;
    public String nature;
    public int rcount;
    public boolean status;
    public String tel;
    public String url;
    public double x;
    public double y;
    public String zipcode;


    @Override
    public String toString() {
        return "HospitalBean{" +
                "address='" + address + '\'' +
                ", area=" + area +
                ", count=" + count +
                ", fax='" + fax + '\'' +
                ", fcount=" + fcount +
                ", gobus='" + gobus + '\'' +
                ", id=" + id +
                ", img='" + img + '\'' +
                ", level='" + level + '\'' +
                ", mail='" + mail + '\'' +
                ", message='" + message + '\'' +
                ", mtype='" + mtype + '\'' +
                ", name='" + name + '\'' +
                ", nature='" + nature + '\'' +
                ", rcount=" + rcount +
                ", status=" + status +
                ", tel='" + tel + '\'' +
                ", url='" + url + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
