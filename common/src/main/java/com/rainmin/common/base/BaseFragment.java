package com.rainmin.common.base;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 默认当前时间的时间选择器
     *
     * @param context         当前activity上下文对象
     * @param dateSetListener 选择回调监听
     */
    public void showDatePicker(Context context,
                               DatePickerDialog.OnDateSetListener dateSetListener) {
        DatePickerDialog datePickerDialog;

        Calendar calendar = Calendar.getInstance(Locale.CHINA);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(context, dateSetListener,
                year, month, day);
        datePickerDialog.show();
    }

    /**
     * <b>方法描述： 显示选择日期的对话框</b>
     * <dd>方法作用：
     * <dd>适用条件：
     * <dd>执行流程：
     * <dd>使用方法：
     * <dd>注意事项：
     * 2016-4-14下午2:03:47
     *
     * @param date 字符串的日期格式（yyyy-MM-dd） null默认显示当前日期
     * @since Met 1.0
     */
    public void showDatePicker(Context context, String date, String minDate,
                               DatePickerDialog.OnDateSetListener dateSetListener) {
        DatePickerDialog datePickerDialog;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (!TextUtils.isEmpty(date)) {
            String[] dates = date.split("-");
            if (dates.length > 2) {
                year = Integer.valueOf(dates[0]);
                month = Integer.valueOf(dates[1]) - 1;
                day = Integer.valueOf(dates[2]);
                calendar.set(year, month, day);
            }
        }

        datePickerDialog = new DatePickerDialog(context, dateSetListener,
                year, month, day);
        if (!TextUtils.isEmpty(minDate)) {

            Calendar calendarMin = Calendar.getInstance(Locale.CHINA);
            String[] dates = date.split("-");
            if (dates.length > 2) {
                calendarMin.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1, Integer.valueOf(dates[2]));
            }
            datePickerDialog.getDatePicker().setMinDate(calendarMin.getTimeInMillis());
        }
        datePickerDialog.show();
    }
}
