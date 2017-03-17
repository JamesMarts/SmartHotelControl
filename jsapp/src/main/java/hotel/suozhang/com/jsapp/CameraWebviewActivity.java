package hotel.suozhang.com.jsapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hotel.suozhang.com.jsapp.bean.DeviceInfo;
import hotel.suozhang.com.jsapp.ble.BleUtil;
import hotel.suozhang.com.jsapp.ble.BluetoothDaoImpl;
import hotel.suozhang.com.jsapp.util.DirectivesConvert;
import hotel.suozhang.com.jsapp.util.L;

import static hotel.suozhang.com.jsapp.R.id.wv1;

public class CameraWebviewActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private final static String TAG = "CameraWebviewActivity";
    private final static String UUID_KEY_DATA = "0000ffe1-0000-1000-8000-00805f9b34fb";
    boolean isScanALLBle;
    private boolean isMatched;//是否配对成功
    List<DeviceInfo> mDevices = new ArrayList<DeviceInfo>();
    private BluetoothAdapter mBluetoothAdapter;
    /**
     * 搜索BLE终端
     */
    private BluetoothDaoImpl mBluetoothDao;
    /**
     * 读写BLE终端
     */
    private boolean isScan = true;

    private boolean mScanning;
    private Button bt;
    private WebView webView;
    public String fileFullName;//照相后的照片的全整路径
    private boolean fromTakePhoto; //是否是从摄像界面返回的webview


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_webview);

        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(Math.random() * 1000 + 1 + ".jpg");
            }
        });

        webView = (WebView) findViewById(wv1);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "蓝牙服务获取失败请打开蓝牙后再试", Toast.LENGTH_LONG).show();
            //finish();
            return;
        }
        mBluetoothDao = BluetoothDaoImpl.getInstance(this);
        mBluetoothDao.setBleDaoCallback(bluetoothDaoCallback);


        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void clickOnAndroid() {
                Log.e("aaaa", "clickOnAndroid被调用了....");

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        fromTakePhoto = true;
                        //调用 启用摄像头的自定义方法
                        takePhoto("testimg" + Math.random() * 1000 + 1 + ".jpg");
                        Log.e(TAG, "========fileFullName: " + fileFullName);
                    }
                });
            }
        }, "callByJs");
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void clickOnAndroid() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getBlueTooth();
                    }
                });
            }
        }, "callBleByJs");
        webView.loadUrl("file:///android_asset/index.html");
    }

    /**
     * android 调用蓝牙
     */
    private void getBlueTooth() {
        if (!BleUtil.isSupportBle(this)) {
            return;
        }
        mDevices.clear();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 1);
            return;
        }
        if (isScan) {
            webView.loadUrl("javascript:showMsg('停止扫描')");
            mBluetoothDao.scan(true);
        } else {
            webView.loadUrl("javascript:showMsg('开始扫描')");
            mBluetoothDao.scan(false);
        }

    }

    /***
     * 蓝牙扫描回调
     */

    BluetoothDaoImpl.BluetoothDaoCallback bluetoothDaoCallback = new BluetoothDaoImpl.BluetoothDaoCallback() {
        @Override
        public void scanState(final boolean isScaning, boolean isTimeOut) {


            if (isScaning) {
                webView.loadUrl("javascript:showMsg('正在扫描...')");
                L.e(TAG, "正在扫描...");
            } else {
              //  webView.loadUrl("javascript:showMsg('扫描结束...')");
            }

        }

        @Override
        public void onDeviceDiscovered(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

            String bleName = device.getName();
            String record = DirectivesConvert.bytesToHexString(scanRecord);
            boolean isSucceed = mBluetoothDao.addDevice(mDevices, device, rssi);
             String   name="";
            for (DeviceInfo d : mDevices) {
                name += d.getDeviceName() +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ d.getDeviceMAC()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ d.rssi+"<br/>";
            }
            Log.e(TAG, "扫描到设备：" + mDevices.size() );
            webView.loadUrl("javascript:showMsg('扫描的蓝牙设备：<br/>" + name + "')");


        }

        @Override
        public void onConnectionStateChange(boolean isConnected) {
            if (isConnected) {
                //远程设备连接成功
            } else {
                mBluetoothDao.close();
                L.e("连接断开-关闭连接");
                // 断线重连处理,如果未安装成功，且重连次数<3次
                //  reconnection();
            }
        }

        @Override
        public void onServicesDiscovered(boolean isSucceed) {
            if (isSucceed) {
               /* Log.e(TAG, "已获取到服务--发送配对码");
                disPlayMessger("已获取到服务--发送数据");

                //TODO: 配对标志 --->未配对 设置为 false
                isMatched = false;
                bleDao.writeData(mData1);*/

                Log.e(TAG, "搜索服务成功--发送数据");
//                L.e("搜索服务成功--发送数据: " + mData);
//                String data = complement(mData);
//                bleDao.writeData(data);
                // disPlayMessger("发送数据：" + mData);

            } else {
                mBluetoothDao.close();
                L.e("搜索服务失败-关闭连接");
                //服务搜索失败，重连 重连次数<3次
                // reconnection();
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGattCharacteristic characteristic) {
            final byte[] txValue = characteristic.getValue();

            String resultCodeHex = DirectivesConvert.bytesToHexString(txValue);
            Log.e(TAG, "返回数据：转十六进制 " + resultCodeHex);
        }
    };

    /*
     * 调用摄像头的方法
	 */
    public void takePhoto(String filename) {
        System.out.println("----start to take photo2 ----");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_MEDIA_TITLE, "TakePhoto");

        //判断是否有SD卡
        String sdDir = null;
        boolean isSDcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSDcardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            sdDir = Environment.getRootDirectory().getAbsolutePath();
        }
        //确定相片保存路径
        String targetDir = sdDir + "/" + "webview_camera";
        File file = new File(targetDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        fileFullName = targetDir + "/" + filename;
        System.out.println("----taking photo fileFullName: " + fileFullName);
        //初始化并调用摄像头
        intent.putExtra(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileFullName)));
        startActivityForResult(intent, 1);
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     * 重写些方法，判断是否从摄像Activity返回的webview activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("----requestCode: " + requestCode + "; resultCode " + resultCode + "; fileFullName: " + fileFullName);
        if (fromTakePhoto && requestCode == 1 && resultCode == -1) {
            webView.loadUrl("javascript:wave2('" + fileFullName + "')");
        } else {
            webView.loadUrl("javascript:wave2('请拍打开摄像头拍照')");
        }
        fromTakePhoto = false;
        super.onActivityResult(requestCode, resultCode, data);
    }


}
