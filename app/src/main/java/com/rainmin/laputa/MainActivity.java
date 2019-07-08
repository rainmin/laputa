package com.rainmin.laputa;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v ->{
            Toast.makeText(MainActivity.this, "you clicked button", Toast.LENGTH_SHORT).show();
            ARouter.getInstance().build("/main/main_activity").navigation(MainActivity.this, new NavigationCallback() {
                @Override
                public void onFound(Postcard postcard) {
                    Log.d("rainmin", "onFound");
                }

                @Override
                public void onLost(Postcard postcard) {
                    Log.d("rainmin", "onLost");
                }

                @Override
                public void onArrival(Postcard postcard) {
                    Log.d("rainmin", "onArrival");
                }

                @Override
                public void onInterrupt(Postcard postcard) {

                }
            });
        });
    }
}
