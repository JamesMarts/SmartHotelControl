package hotel.suozhang.com.menu.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import hotel.suozhang.com.menu.R;
import hotel.suozhang.com.menu.util.T;
import hotel.suozhang.com.menu.base.BaseActivity;
import hotel.suozhang.com.menu.ui.FloatingEditText;

@ContentView(R.layout.activity_ticket)
public class TicketActivity extends BaseActivity {

    @ViewInject(R.id.editText2)
    private FloatingEditText etFrom;
    @ViewInject(R.id.editText3)
    private EditText etTo;
    @ViewInject(R.id.editText4)
    private EditText etDate;


    @Override
    protected void initData() {

    }


    @Event(R.id.button2)
    private void omaa(View w) {
        String from = etFrom.getText().toString();
        String to = etTo.getText().toString();
        String date = etDate.getText().toString();
        if (TextUtils.isEmpty(from) && TextUtils.isEmpty(to) && TextUtils.isEmpty(date)) {
            T.showShort("参数不能为空！");
            return;
        }
        Intent intent = new Intent(this, TicketListActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("to", to);
        intent.putExtra("date", date);
        startActivity(intent);
    }

}
