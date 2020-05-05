package com.rainmin.module.main.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmin.module.main.R;
import com.rainmin.module.main.model.api.XunjianApis;
import com.rainmin.module.main.model.bean.Xunjian;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Route(path = "/main/main_activity")
public class MainActivity extends AppCompatActivity {

    BaseQuickAdapter<Xunjian, BaseViewHolder> mAdapter;
    XunjianApis mApis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);
        initNetEnv();
        initView();
        getData();
    }

    private void initNetEnv() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApis = retrofit.create(XunjianApis.class);
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.rv_xunjian);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter = new BaseQuickAdapter<Xunjian, BaseViewHolder>(R.layout.main_item_xunjian) {
            @Override
            protected void convert(BaseViewHolder helper, Xunjian item) {
                helper.setText(R.id.tv_date, "日期：" + item.getDate())
                        .setText(R.id.tv_line, "线路：" + item.getLine())
                        .setText(R.id.tv_updown, "上下行：" + "上行")
                        .setText(R.id.tv_train, "车号：" + item.getTrain())
                        .setText(R.id.tv_speed, "速度：" + item.getSpeed())
                        .setText(R.id.tv_kilometer, "公里标：" + item.getKilometer())
                        .setText(R.id.tv_station, "站名：" + item.getStation());

                ImageView imageView = helper.getView(R.id.iv_image);
                Glide.with(mContext).load(item.getFilename())
                        .placeholder(R.drawable.main_ic_guidao)
                        .error(R.drawable.main_ic_guidao)
                        .into(imageView);
            }
        });
    }

    private void getData() {
        List<Xunjian> list = new ArrayList<>(20);
        for (int i = 1; i < 20; i++) {
            Xunjian xunjian = new Xunjian();
            xunjian.setDate("2020-05-05");
            xunjian.setLine(2);
            xunjian.setUpdown(1);
            xunjian.setTrain(2);
            xunjian.setSpeed(35 + i);
            xunjian.setKilometer(16 + i);
            xunjian.setStation("哈雷" + i + "站");

            list.add(xunjian);
        }
        mAdapter.setNewData(list);
    }
}
