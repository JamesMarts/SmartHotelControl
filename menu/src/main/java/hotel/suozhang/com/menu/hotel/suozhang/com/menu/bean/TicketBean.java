package hotel.suozhang.com.menu.hotel.suozhang.com.menu.bean;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

import hotel.suozhang.com.menu.http.JsonResponseParser;

/**
 * Created by LIJUWEN on 2016/11/17.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class TicketBean  implements Serializable{

    public boolean ret;
    public DataBean data;

    public static class DataBean {
        public List<TrainListBean> trainList;
        public static class TrainListBean {
            public String trainType;
            public String trainNo;
            public String from;
            public String to;
            public String startTime;
            public String endTime;
            public String duration;
            public List<SeatInfosBean> seatInfos;

            public static class SeatInfosBean {
                public String seat;
                public String seatPrice;
                public int remainNum;
            }
        }
    }
}
