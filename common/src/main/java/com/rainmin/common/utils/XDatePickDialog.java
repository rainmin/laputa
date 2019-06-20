package com.rainmin.common.utils;

import android.app.DatePickerDialog;
import android.content.Context;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class XDatePickDialog extends DatePickerDialog {
    public XDatePickDialog(Context context, OnDateSetListener callBack,
                           int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }

    public XDatePickDialog(Context context, int theme,
                           OnDateSetListener callBack, int year, int monthOfYear,
                           int dayOfMonth) {
        super(context, theme, callBack, year, monthOfYear, dayOfMonth);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        // super.onStop();//注释掉
    }
}
