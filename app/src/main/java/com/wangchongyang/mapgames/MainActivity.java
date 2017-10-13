package com.wangchongyang.mapgames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.wangchongyang.mapgames.map.MapActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startMapActivity(View view) {
        startActivity(new Intent(this,MapActivity.class));
    }
}
