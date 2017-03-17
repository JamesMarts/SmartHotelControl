package hotel.suozhang.com.jsapp.bean;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;

/**
 * Created by Moodd on 2016/3/10.
 */
public class DeviceInfo {
    BluetoothDevice device;
    public int rssi;

    public DeviceInfo(BluetoothDevice device, int rssi) {
        this.device = device;
        this.rssi = rssi;
    }

    public String getDeviceName() {
        String name = device.getName();
        return TextUtils.isEmpty(name) ? "NO_NAME" : name;
    }

    public String getDeviceMAC() {

        return device.getAddress();
    }

}
