package hotel.suozhang.com.jsapp.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import hotel.suozhang.com.jsapp.bean.DeviceInfo;
import hotel.suozhang.com.jsapp.util.DirectivesConvert;
import hotel.suozhang.com.jsapp.util.L;


/**
 * Created by Moodd on 2016/3/14.
 */
public class BluetoothDaoImpl implements IDeviceDao {
    /**
     * 返回数据的状态值-错误
     */
    public static final String STATE_ERROR = "4552524F52";//ERROR
    /**
     * 返回数据的状态值-未授权
     */
    public static final String STATE_UNAUTHOR = "554E415554484F5200";//UNAUTHOR
    /**
     * 返回数据的状态值-未安装
     */
    public static final String STATE_UNINSTALL = "554E494E5354414C4C00";//UNINSTALL

    private static final String TAG = BluetoothDaoImpl.class.getSimpleName();

    /**
     * 未安装
     * ----FFFFFFFFN对应的十六进制ACSII码
     */
    public static final String STATE_FFFFFFFFN = "46464646464646464E";//FFFFFFFFN
    /**
     * 可安装
     * ----FFFFFFFFI对应的十六进制ACSII码
     */
    public static final String STATE_FFFFFFFFI = "464646464646464649";//FFFFFFFFI
    /**
     * 已安装
     * ----FFFFFFFFY对应的十六进制ACSII码
     */
    public static final String STATE_FFFFFFFFY = "464646464646464659";//FFFFFFFFY


    //发送通知的UUID
    public UUID CCCD = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    //服务的UUID
    // public UUID UUID_SERVICE = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");//0xFEE7
    public UUID UUID_SERVICE = UUID.fromString("0000fee7-0000-1000-8000-00805f9b34fb");
    //写数据
    public UUID UUID_CHAR_WRITE = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    //notify方式 读取。
    public UUID UUID_CHAR_READ = UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb");

    /**
     * 扫描周期
     */
    private static long SCAN_TIME_OUT = 10 * 1000;

    private static final Object mLock = new Object();

    private Context mContext;

    private BluetoothAdapter mBluetoothAdapter;

    private BluetoothDaoCallback mBleDaoCallback;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    /**
     * 扫描状态，true :正在扫描
     */
    private boolean mScanning = false;

    private static BluetoothDaoImpl instance;

    public static BluetoothDaoImpl getInstance(Context context) {
        if (instance == null) {
            synchronized (mLock) {
                if (instance == null) {
                    instance = new BluetoothDaoImpl(context);
                }
            }
        }
        return instance;
    }

    private BluetoothDaoImpl() {
        new IllegalArgumentException("Not allowed to call this constructor! Please invoke getInstance()");
    }

    /**
     * 使用默认UUIDConfig初始化，如需改变，另行调用setUUIDConfig(UUIDConfig config)
     *
     * @param context
     */
    private BluetoothDaoImpl(Context context) {
        this.mContext = context.getApplicationContext();
        initialize();
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    /**
     * 获取回调
     *
     * @return
     */
    public BluetoothDaoCallback getBleDaoCallback() {
        return mBleDaoCallback;
    }

    public BluetoothDaoCallback clearBleDaoCallback() {
        return mBleDaoCallback = null;
    }

    /**
     * 设置统一回调
     *
     * @param bleDaoCallback
     */
    public BluetoothDaoImpl setBleDaoCallback(BluetoothDaoCallback bleDaoCallback) {
        this.mBleDaoCallback = bleDaoCallback;
        return this;
    }

    /**
     * 根据配置，设置对应UUID
     *
     * @param config UUID配置类
     */
    public BluetoothDaoImpl setUUIDConfig(UUIDConfig config) {
        if (config == null) return this;
        UUID_SERVICE = getUUID(config.UUID_SERVICE);
        UUID_CHAR_WRITE = getUUID(config.UUID_CHAR_WRITE);
        UUID_CHAR_READ = getUUID(config.UUID_CHAR_READ);
        CCCD = getUUID(config.CCCD);
        return this;
    }

    private UUID getUUID(String uuid) {

        return TextUtils.isEmpty(uuid) ? null : UUID.fromString(uuid);
    }

    /**
     * 初始化BluetoothAdapter
     *
     * @return 成功 true 失败 false
     */
    private boolean initialize() {
        if (mBluetoothAdapter == null)
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) return false;
        return true;
    }

    /**
     * 获取扫描超时时间
     *
     * @return
     */
    public static long getScanTimeOut() {
        return SCAN_TIME_OUT;
    }

    /**
     * 设置扫描超时时间
     *
     * @param timeoutMillis
     */
    public static synchronized void setScanTimeOut(long timeoutMillis) {
        SCAN_TIME_OUT = timeoutMillis;
    }

    /**
     * 扫描设备
     *
     * @param enable true:开始扫描 false: 停止扫描
     */
    @Override
    public void scan(boolean enable) {
        this.scan(enable, SCAN_TIME_OUT);
    }

    /**
     * 扫描设备，指定时间后自动停止扫描
     *
     * @param enable        true:开始扫描 false: 停止扫描
     * @param timeoutMillis 自动扫描持续时间,及扫描超时时间
     */

    public void scan(boolean enable, long timeoutMillis) {
        this.scan(enable, timeoutMillis, false);
    }

    /**
     * 扫描设备，一直扫描，需手动停止
     *
     * @param enable true:开始扫描 false: 停止扫描
     */

    public void periodScan(boolean enable) {
        this.scan(enable, 0, true);
    }

    /**
     * 扫描设备，指定时间后自动停止扫描
     *
     * @param enable        true:开始扫描 false: 停止扫描
     * @param timeoutMillis 自动扫描持续时间,及扫描超时时间
     */
    private synchronized void scan(boolean enable, long timeoutMillis, boolean isPeriodScan) {
        if (enable) {
            if (isPeriodScan) {
                mScanning = true;
                mHandler.removeCallbacks(stopScanTask);



                mBluetoothAdapter.startLeScan(mLeScanCallback);
            } else {
                timeoutMillis = timeoutMillis <= 0 ? 0 : timeoutMillis;
                mHandler.removeCallbacks(stopScanTask);
                mHandler.postDelayed(stopScanTask, timeoutMillis);

                mScanning = true;
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            }
        } else {
            mScanning = false;
            mHandler.removeCallbacks(stopScanTask);
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        if (mBleDaoCallback != null) mBleDaoCallback.scanState(mScanning, false);//扫描状态回调
    }

    /**
     * 停止扫描任务
     */
    private Runnable stopScanTask = new Runnable() {
        @Override
        public void run() {
            mScanning = false;

            if (mBluetoothAdapter != null) mBluetoothAdapter.stopLeScan(mLeScanCallback);
            if (mBleDaoCallback != null) mBleDaoCallback.scanState(mScanning, true);//扫描状态回调
        }
    };

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            //扫描到设备，回调接口
            if (mBleDaoCallback != null) mBleDaoCallback.onDeviceDiscovered(device, rssi, scanRecord);
        }
    };
    /**
     * 扫描并连接设备的回调实现
     */
    private PeriodMacScanCallback scanAndConnectCallback = new PeriodMacScanCallback() {
        @Override
        public void onScanTimeout() {
            if (mBleDaoCallback != null) mBleDaoCallback.scanState(false, true);//扫描状态回调
        }

        @Override
        public void onDeviceFound(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //连接设备
                    boolean isc = connect(device.getAddress());
                    if (!isc) {
                        if (mBleDaoCallback != null) mBleDaoCallback.onConnectionStateChange(false);
                    }
                }
            });
        }
    };

    /**
     * 扫描并连接,在主线程中调用
     *
     * @param mac     设备MAC地址
     * @param timeOut 扫描超时时间
     */
    public void scanAndConnect(final String mac, long timeOut) {
        stopScan(scanAndConnectCallback);
        scanAndConnectCallback.setScanParameter(mac, timeOut, this);
        scanAndConnectCallback.setHasFound();
        startScan(scanAndConnectCallback);
    }

    public void startScan(PeriodMacScanCallback callback) {
        L.e("PeriodMacScanCallback---impl--startScan", callback.toString());
        callback.notifyScanStarted();
        mBluetoothAdapter.startLeScan(callback);
    }

    public void stopScan(PeriodMacScanCallback callback) {
        L.e("PeriodMacScanCallback---impl--stopScan", callback.toString());
        callback.removeCallbacks();
        mBluetoothAdapter.stopLeScan(callback);
    }

    public void stopConnectScan() {
        if (scanAndConnectCallback != null) stopScan(scanAndConnectCallback);
    }


    /**
     * 重连
     */
    public synchronized boolean reconnection() {
        if (mBluetoothDeviceAddress == null) {
            L.e(TAG, "上次连接的MAC地址为空 ，重连失败 mBluetoothDeviceAddress ");
            return false;
        }
        if (mBluetoothGatt == null) {
            L.e(TAG, "mBluetoothGatt 为空，重连失败 ");
            return false;
        }

        L.i(TAG, "reconnection() 关闭连接 mBluetoothGatt.close()");
        // this.refreshDeviceCache();
        mBluetoothGatt.close();

        return connect(mBluetoothDeviceAddress);

    }

    /**
     * 重连-关闭当前连接，重新连接
     */
    public synchronized boolean reconnection(String address) {
        close();
        return connect(address);
    }

    @Override
    public synchronized boolean connect(String address) {

        if (mBluetoothAdapter == null || address == null) {
            L.e(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            L.e(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        //建立连接
        mBluetoothGatt = device.connectGatt(mContext, false, mGattCallback);

        if (mBluetoothGatt == null) {
            L.e(TAG, "connectGatt return null .  Unable to connect.");
            return false;
        }
        //刷新缓存
        //refreshDeviceCache();
        L.i(TAG, "Trying to create a new connection. MAC address= " + address);
        mBluetoothDeviceAddress = address;
        return true;
    }


    public synchronized boolean connect(BluetoothDevice device) {
        if (device == null) {
            return false;
        }
        String address = device.getAddress();
        if (mBluetoothAdapter == null || address == null) {
            L.e(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        //建立连接
        mBluetoothGatt = device.connectGatt(mContext, false, mGattCallback);
        if (mBluetoothGatt == null) {
            L.e(TAG, "connectGatt return null .  Unable to connect.");
            return false;
        }
        //刷新缓存
        //refreshDeviceCache();
        L.i(TAG, "Trying to create a new connection. MAC address= " + address);
        mBluetoothDeviceAddress = address;
        return true;
    }

    /**
     * 断开连接
     */
    @Override
    public synchronized void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            L.e(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * 关闭连接
     */
    @Override
    public synchronized void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        L.i(TAG, "关闭连接 close()");
        mBluetoothDeviceAddress = null;
        this.refreshDeviceCache();
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * 刷新缓存
     *
     * @return
     */
    public synchronized boolean refreshDeviceCache() {
        try {
            Method e = BluetoothGatt.class.getMethod("refresh", new Class[0]);
            if (e != null) {
                boolean success = ((Boolean) e.invoke(mBluetoothGatt, new Object[0])).booleanValue();
                L.i(TAG, "Refreshing result: " + success);
                return success;
            }
        } catch (Exception var3) {
            L.e(TAG, "An exception occured while refreshing device " + var3);
        }

        return false;
    }

    public BluetoothGatt getBluetoothGatt() {
        return this.mBluetoothGatt;
    }

    @Override
    public Object readData() {
        return null;
    }

    /**
     * 写入数据，当参数为byte[]时，直接发送；
     * 为String 十六进制字符串时，对命令进行分组发送
     *
     * @param value 一般为byte[]
     */
    @Override
    public synchronized void writeData(final Object value) {

        if (value == null) return;

        if (value instanceof byte[]) sendMessage((byte[]) value);
        if (value instanceof String) {
            try {

                String[] message = DirectivesConvert.getSubStr((String) value);

                byte[] value1 = message[0] == null ? null : DirectivesConvert.HexString2Bytes(message[0]);
                byte[] value2 = message[1] == null ? null : DirectivesConvert.HexString2Bytes(message[1]);
                sendMessage(value1, value2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息
     *
     * @param values
     */
    private synchronized boolean sendMessage(byte[]... values) {
        boolean status = false;
        if (values == null || mBluetoothGatt == null) return false;
        BluetoothGattService rxService = mBluetoothGatt.getService(UUID_SERVICE);
        if (rxService == null) {
            L.e(TAG, "Rx service not found!");
            return false;
        }
        final BluetoothGattCharacteristic rxChar = rxService.getCharacteristic(UUID_CHAR_WRITE);
        if (rxChar == null) {
            L.e(TAG, "Rx charateristic not found!");
            return false;
        }
        for (int i = 0; i < values.length; i++) {
            final byte[] value = values[i];
            if (value == null) continue;
            // writeCharacteristic(rxChar, value);
            if (i == 0) {
                status = writeCharacteristic(rxChar, value);
            } else if (i > 0) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        writeCharacteristic(rxChar, value);
                    }
                }, i * 300);
            }
        }

        return status;
    }

    private boolean writeCharacteristic(BluetoothGattCharacteristic rxChar, byte[] value) {
        if (mBluetoothGatt == null || rxChar == null || value == null) return false;
        rxChar.setValue(value);
        boolean status = mBluetoothGatt.writeCharacteristic(rxChar);
        String txChar = DirectivesConvert.bytesToHexString(value);
        L.i(TAG, "write txChar = " + txChar + "  status=" + status);
        return status;
    }


    /**
     * 开启通知
     *
     * @return
     */
    private synchronized boolean startNotification() {

        if (mBluetoothGatt == null) {
            L.e(TAG, "开启通知失败 mBluetoothGatt = null");
            return false;
        }

        BluetoothGattService rxService = mBluetoothGatt.getService(UUID_SERVICE);
        if (rxService == null) {
            L.e(TAG, "开启通知失败Rx service not found!");
            return false;
        }
        BluetoothGattCharacteristic txChar = rxService.getCharacteristic(UUID_CHAR_READ);
        if (txChar == null) {
            L.e(TAG, "开启通知失败Tx charateristic not found!");
            return false;
        }

        boolean isNotification = mBluetoothGatt.setCharacteristicNotification(txChar, true);

        BluetoothGattDescriptor descriptor = txChar.getDescriptor(CCCD);

        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);//BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
        boolean isWrite = mBluetoothGatt.writeDescriptor(descriptor);

        L.i(TAG, "开启通知 writeDescriptor = " + isWrite + " setCharacteristicNotification = " + isNotification);

        return isWrite && isNotification;
    }

    public boolean readCharacteristic() {
        if (mBluetoothGatt == null) {
            L.e(TAG, " mBluetoothGatt = null");
            return false;
        }

        BluetoothGattService rxService = mBluetoothGatt.getService(UUID_SERVICE);
        if (rxService == null) {
            L.e(TAG, "Rx service not found!");
            return false;
        }
        BluetoothGattCharacteristic txChar = rxService.getCharacteristic(UUID_CHAR_READ);
        if (txChar == null) {
            L.e(TAG, "charateristic not found!");
            return false;
        }
        return mBluetoothGatt.readCharacteristic(txChar);
    }


    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(final BluetoothGatt gatt, final int status, final int newState) {
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    L.e(TAG, "onConnectionStateChange -------status= " + status + " newState = " + newState);

                    if (status != BluetoothGatt.GATT_SUCCESS) {
                        if (mBleDaoCallback != null) mBleDaoCallback.onConnectionStateChange(false);
                        return;
                    }

                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        if (mBleDaoCallback != null) mBleDaoCallback.onConnectionStateChange(true);

                        if (mBluetoothGatt != null) {
                            boolean isDis = mBluetoothGatt.discoverServices();//启动搜索服务
                            L.i(TAG, "连接成功，开始搜索服务 isDis = " + isDis);
                        } else {
                            L.i(TAG, "连接成功，不搜索服务 mBluetoothGatt = null");
                        }

                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        L.i(TAG, "连接断开 onConnectionStateChange newState=STATE_DISCONNECTED");
                        if (mBleDaoCallback != null) mBleDaoCallback.onConnectionStateChange(false);

                    } else {
                        L.i(TAG, "连接断开 onConnectionStateChange");
                        if (mBleDaoCallback != null) mBleDaoCallback.onConnectionStateChange(false);
                    }
                }
            });

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, final int status) {
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        L.i(TAG, "搜索服务成功:  " + status);

                        boolean isNotification = startNotification();//开启通知
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                L.i(TAG, "搜索到服务后回调:  " + status);
                                if (mBleDaoCallback != null) mBleDaoCallback.onServicesDiscovered(true);//搜索到服务后回调 ：成功
                            }
                        },200);//200
                    } else {
                        L.e(TAG, "搜索服务失败: " + status);
                        if (mBleDaoCallback != null) mBleDaoCallback.onServicesDiscovered(false);//搜索到服务后回调 ：失败
                    }
                }
            });

        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, final int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                readDataCallback(characteristic);
            }

        }

        @Override
        public void onCharacteristicChanged(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {

            readDataCallback(characteristic);
        }
    };

    private void readDataCallback(final BluetoothGattCharacteristic characteristic) {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (UUID_CHAR_READ.equals(characteristic.getUuid())) {
                    if (mBleDaoCallback != null) mBleDaoCallback.onCharacteristicRead(characteristic);//读取到数据后回调
                }
            }
        });

    }

    /**
     * // 检查当前手机是否支持ble 蓝牙
     *
     * @return true:支持 false: 不支持
     */
    public boolean isSupportBle() {
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    /**
     * 清除所有设备
     *
     * @param devices
     */
    public static void clearDevices(List<DeviceInfo> devices) {
        if (devices != null && devices.size() > 0) devices.clear();
    }


    /**
     * 添加设备
     *
     * @param devices 设备集合，存放所有扫描到的设备
     * @param device  扫描到的设备
     * @param rssi    信号强度
     */
    public static boolean addDevice(List<DeviceInfo> devices, BluetoothDevice device, int rssi) {
        boolean isSucceed = false;
        if (devices == null) return isSucceed;

        boolean isEx = false;

        for (DeviceInfo deviceInfo : devices) {

            if (deviceInfo.getDeviceMAC().equals(device.getAddress())) {
                isEx = true;
                break;
            }
        }
        if (!isEx) {
            L.e(TAG, "添加设备Devices :  " + device.getAddress() + "  " + rssi);
            devices.add(new DeviceInfo(device, rssi));
            isSucceed = true;
        }
        return isSucceed;
    }

    private void runOnMainThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    /**
     * UUID配置类，用于保存各UUID信息
     */
    public class UUIDConfig {
        private String UUID_SERVICE;
        private String UUID_CHAR_WRITE;
        private String UUID_CHAR_READ;
        private String CCCD;
    }

    public interface BluetoothDaoCallback {
        /**
         * 扫描状态变化时回调
         *
         * @param isScaning 是否正在扫描
         * @param isTimeOut 是否超时，停止扫描时回调
         */
        void scanState(boolean isScaning, boolean isTimeOut);

        /**
         * 扫描，发现设备时回调，传递参数为：扫描到的设备列表，
         * 每发现一个设备，此接口回调一次，如需通知ListView 更新数据，
         * 请在此回调接口中调用，Adapter.notifyDataSetChanged()方法
         *
         * @param device
         * @param rssi
         * @param scanRecord
         */
        void onDeviceDiscovered(BluetoothDevice device, final int rssi, byte[] scanRecord);

        /**
         * 连接状态发送变化时回调
         *
         * @param isConnected 参数：是否连接成功
         */
        void onConnectionStateChange(boolean isConnected);

        /**
         * 连接成功后，搜素到指定服务时回调，请在此回调中处理数据发送,
         *
         * @param isSucceed 参数：搜索服务是否成功
         */
        void onServicesDiscovered(boolean isSucceed);

        /**
         * 当接受到设备返回的数据时回调，只有当发送通知时指定的characteristicUUID与返回characteristic的UUID对应时才会回调
         *
         * @param characteristic
         */
        void onCharacteristicRead(BluetoothGattCharacteristic characteristic);

    }
}
