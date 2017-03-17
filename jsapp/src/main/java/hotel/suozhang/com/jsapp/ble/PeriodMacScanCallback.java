package hotel.suozhang.com.jsapp.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.atomic.AtomicBoolean;

import hotel.suozhang.com.jsapp.util.L;


public abstract class PeriodMacScanCallback implements BluetoothAdapter.LeScanCallback {
    private String mac;
    private long timeoutMillis;
    private Handler handler = new Handler(Looper.getMainLooper());

    private BluetoothDaoImpl bluetoothAdapter;

    private AtomicBoolean hasFound = new AtomicBoolean(false);

    public PeriodMacScanCallback() {
    }

    public PeriodMacScanCallback(String mac, long timeoutMillis, BluetoothDaoImpl bluetoothAdapter) {
        this.mac = mac;
        this.timeoutMillis = timeoutMillis;
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public void setScanParameter(String mac, long timeoutMillis, BluetoothDaoImpl bluetoothAdapter) {
        this.mac = mac;
        this.timeoutMillis = timeoutMillis;
        this.bluetoothAdapter = bluetoothAdapter;
    }

    private Runnable stopTask = new Runnable() {
        @Override
        public void run() {
            if (bluetoothAdapter == null) return;
            bluetoothAdapter.stopScan(PeriodMacScanCallback.this);
            L.e("PeriodMacScanCallback----stopTask", PeriodMacScanCallback.this.toString());
            PeriodMacScanCallback.this.onScanTimeout();
        }
    };

    public void notifyScanStarted() {
        removeCallbacks();
        handler.postDelayed(stopTask, this.timeoutMillis);
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        L.e("PeriodMacScanCallback----onLeScan", device.getAddress());
        if (bluetoothAdapter == null) return;
        if (!this.hasFound.get() && this.mac.equalsIgnoreCase(device.getAddress())) {
            this.hasFound.set(true);
            this.bluetoothAdapter.stopScan(this);
            L.e("PeriodMacScanCallback", this.toString());
            this.onDeviceFound(device, rssi, scanRecord);
        }
    }

    public void removeCallbacks() {
        this.handler.removeCallbacks(stopTask);
    }

    public void setHasFound() {
        hasFound.set(false);
    }

    public abstract void onScanTimeout();

    public abstract void onDeviceFound(BluetoothDevice var1, int var2, byte[] var3);
}