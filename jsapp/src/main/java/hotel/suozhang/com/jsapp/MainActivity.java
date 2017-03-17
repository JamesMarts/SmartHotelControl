package hotel.suozhang.com.jsapp;

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

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private final static String TAG = "CameraWebviewActivity";

    private Button bt;
    private WebView wv;
    public String fileFullName;//照相后的照片的全整路径
    private boolean fromTakePhoto; //是否是从摄像界面返回的webview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {

        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"----------------");
                takePhoto( Math.random()*1000+1 + ".jpg");
            }
        });
        final WebView webView = (WebView) findViewById(R.id.wv);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void clickOnAndroid() {
               Log.e("","clickOnAndroid被调用了....");

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("","clickOnAndroid被调用了1111111111111111111111....");
//                        webView.loadUrl("javascript:wave()");
                    }
                });
            }
        }, "callByJs");
        webView.loadUrl("file:///android_asset/index.html");


//        WebSettings setting = wv.getSettings();
//        setting.setJavaScriptEnabled(true);
//        wv.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//
//        });
//
//        wv.setWebChromeClient(new WebChromeClient(){
//            @Override//实现js中的alert弹窗在Activity中显示
//            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                Log.e(TAG, message);
//                result.confirm();
//                return true;
//            }
//        });




//        //webview增加javascript接口，监听html页面中的js点击事件
//        wv.addJavascriptInterface(new Object(){
//            public String clickOnAndroid() {//将被js调用
//                Log.e(TAG,"clickOnAndroid ----");
//                mHandler.post(new Runnable() {
//                    public void run() {
//
//                        fromTakePhoto  = true;
//                        //调用 启用摄像头的自定义方法
//                        takePhoto("testimg" + Math.random()*1000+1 + ".jpg");
//                        Log.e(TAG,"========fileFullName: " + fileFullName);
//                    }
//                });
//                return fileFullName;
//            }
//        }, "callByJs");
//        wv.loadUrl("file:///android_asset/index.html");
    }

    /*
     * 调用摄像头的方法
     */
    public void takePhoto(String filename) {
        Log.e(TAG,"----start to take photo2 ----");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_MEDIA_TITLE, "TakePhoto");

        //判断是否有SD卡
        String sdDir = null;
        boolean isSDcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(isSDcardExist) {
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
        Log.e(TAG,"----taking photo fileFullName: " + fileFullName);
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
        Log.e(TAG,"----requestCode: " + requestCode + "; resultCode " + resultCode + "; fileFullName: " + fileFullName);
        if (fromTakePhoto && requestCode ==1 && resultCode ==-1) {
            wv.loadUrl("javascript:wave2('" + fileFullName + "')");
        } else {
            wv.loadUrl("javascript:wave2('Please take your photo')");
        }
        fromTakePhoto = false;
        super.onActivityResult(requestCode, resultCode, data);
    }



}
