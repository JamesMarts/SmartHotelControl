package hotel.suozhang.com.menu.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import hotel.suozhang.com.menu.R;


/**
 * Created by LIJUWEN on 2016/8/12.
 */
public class HintDialogCancle extends Dialog {

    private Context context;
    private String mesage;
    private String cacelButtonText;
    private MyClickListenerInterface listenerInterface;
    private LayoutInflater layoutInflater;

    public HintDialogCancle(Context context) {
        super(context);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = layoutInflater.inflate(R.layout.dialog_hint_cancle, null);
        setContentView(view);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
//        Button buttonCancle = (Button) view.findViewById(R.id.signon_button_cancle);
//        txtMessage.setText(mesage);
//        buttonCancle.setText(cacelButtonText);
        Window dialogWindow = getWindow();
       this.setCancelable(false);
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
        datePicker.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });
//        buttonCancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                L.e("dialogCanclea.dismiss();--------------->");
//
//                listenerInterface.doCancel();
//            }
//        });
    }

    public void setListener(MyClickListenerInterface listener) {
        this.listenerInterface = listener;
    }


    public interface MyClickListenerInterface {
        void doCancel();
    }
}
