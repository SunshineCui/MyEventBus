package com.example.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            EventBus.getDefault().register(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    @Subscrible(threadMode = ThreadMode.MAIN)
    public void printLog(EventBean bean){
        Log.d("MainActivity",bean.toString());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unRegister(this);
    }
}
