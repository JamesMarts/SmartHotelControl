package hotel.suozhang.com.menu.activity;

import android.content.Intent;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import hotel.suozhang.com.menu.base.BaseActivity;
import hotel.suozhang.com.menu.R;

@ContentView(R.layout.activity_main2)
public class Main2Activity extends BaseActivity {



    @Override
    protected void initData() {

    }
    @Event(R.id.btn_main_food)
    private void toFood(View v){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Event(R.id.btn_main_ticket)
    private void toTicket(View v){
        Intent intent=new Intent(this, TicketActivity.class);
        startActivity(intent);
    }
    @Event(R.id.btn_main_hospital)
    private void toHospital(View v){
        Intent intent=new Intent(this, CityListActivity.class);
        startActivity(intent);
    }

}
