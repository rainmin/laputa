package com.rainmin.common.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Video.VideoColumns;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.HttpException;

public class DialogUtils {

    public static void showInfoDialog(Context context, String message) {
        showInfoDialog(context, message, "提示", "确定", null, "取消", null);
    }

    public static void showInfoDialog(Context context, String message,
                                      String titleStr, String positiveStr,
                                      DialogInterface.OnClickListener PositiveListener,
                                      String negativeStr, DialogInterface.OnClickListener NegativeListener) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
        localBuilder.setTitle(titleStr);
        localBuilder.setMessage(message);
        if (PositiveListener == null)
            PositiveListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            };
        localBuilder.setPositiveButton(positiveStr, PositiveListener);
        if (NegativeListener != null) {
            localBuilder.setNegativeButton(negativeStr, NegativeListener);
        }
        localBuilder.show();
    }

    public static void showInfoDialog(Context context, String titleStr, String positiveStr,
                                      DialogInterface.OnClickListener PositiveListener, String negativeStr,
                                      DialogInterface.OnClickListener NegativeListener, View view) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
        localBuilder.setTitle(titleStr);
        if (PositiveListener == null)
            PositiveListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            };
        localBuilder.setPositiveButton(positiveStr, PositiveListener);
        if (NegativeListener != null) {
            localBuilder.setNegativeButton(negativeStr, NegativeListener);
        }
        localBuilder.setView(view);
        localBuilder.show();
    }

    public static void showDateSelectDialog(final Context context, final TextView paramView) {
        Calendar calendar = Calendar.getInstance();
        String curDate = paramView.getText().toString();

        XDatePickDialog d = new XDatePickDialog(context,
                new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;

                        showTimeSelectDialog(context,paramView,year + "年" + monthOfYear
                                + "月" + dayOfMonth+"日");
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        d.show();
    }

    public static void showTimeSelectDialog(Context context, final TextView paramView,final String  datastr) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog d = new TimePickerDialog(context, new OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                paramView.setText(datastr+"  "+hourOfDay + "时" + minute+"分");
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        d.show();
    }

    public static ProgressDialog showProgressDialog(Context context, String content) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(content);
        progressDialog.show();
        return progressDialog;
    }
}
