package com.rainmin.common.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.IOException;

import static android.content.Context.TELEPHONY_SERVICE;

public class AppUtils {

    /**
     * 获取app的名称
     */
    public static String getAppName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本名称
     * @return
     */
    private String getVersionName(Context context) {
        String versionName = "v1.0";
        try {
            versionName = "v" + context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 清除应用SharedPreference
     * @param context
     */
    public static void clearSharedPreference(Context context) {
        try {
            FileUtils.deleteFiles(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备码
     * @param context 上下文对象
     * @return 设备码
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceID(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
        /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            return TelephonyMgr.getMeid();
        }else {


        }*/
    }
}
