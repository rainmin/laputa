package com.rainmin.laputa;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rainmin.common.utils.HrefUtils;
import com.rainmin.common.widget.LineItemDecoration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BaseQuickAdapter<String, BaseViewHolder> mAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvDashboard = findViewById(R.id.rv_dashboard);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        String[] array_function = getResources().getStringArray(R.array.array_dashboard);
        List<String> functions = Arrays.asList(array_function);
        rvDashboard.setLayoutManager(new GridLayoutManager(this, 3));
        rvDashboard.addItemDecoration(new LineItemDecoration());
        rvDashboard.setAdapter(mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_dashboard) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                TextView textView = helper.getView(R.id.tv_function);
                textView.setText(item);
                if (TextUtils.equals(item, getString(R.string.xunjian_hist))) {
                    textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_xunjian_hist, 0, 0);
                } else {
                    textView.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_launcher_round, 0, 0);
                }
            }
        });
        mAdapter.addData(functions);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String function = (String) adapter.getData().get(position);
                if (TextUtils.equals(function, getString(R.string.xunjian_hist))) {
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
                } else {
                    HrefUtils.getInstance().hrefActivity(MainActivity.this, LoginActivity.class);
                }
            }
        });
    }
}
