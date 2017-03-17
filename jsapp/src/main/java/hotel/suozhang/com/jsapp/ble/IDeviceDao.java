package hotel.suozhang.com.jsapp.ble;

/**
 * 设备操作类，对设备进行操作的接口
 * Created by Moodd on 2016/3/14.
 */
public interface IDeviceDao {
    /**
     * 扫描设备
     *
     * @param enable
     */
    void scan(boolean enable);

    /**
     * 连接设备
     *
     * @param address
     * @return
     */
    boolean connect(String address);

    /**
     * 断开连接
     */
    void disconnect();

    /**
     * 关闭连接
     */
    void close();

    /**
     * 读取数据
     *
     * @return
     */
    Object readData();

    /**
     * 写入数据
     *
     * @param value 一般为byte[]
     */
    void writeData(Object value);

}
