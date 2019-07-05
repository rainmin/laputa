package com.rainmin.common.utils;

import com.rainmin.common.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * <dl>  Class Description
 * <dd> 项目名称：AppFrame
 * <dd> 类名称：HrefUtils
 * <dd> 类描述：
 * <dd> 创建时间：2016-4-7上午10:49:33 2016
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author lujing
 * @version 1.0
 */
public class HrefUtils {


    private static class HrefUtilHolder {
        static final HrefUtils INSTANCE = new HrefUtils();
    }

    private HrefUtils() {
    }

    public static HrefUtils getInstance() {
        return HrefUtilHolder.INSTANCE;
    }

//    private Activity get

    /***
     * 跳转到网络设置页面
     * @param activity 实例对象
     */
    public void hrefInterSetting(Activity activity) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();
        // 跳转到系统的网络设置界面
        Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        wActivity.startActivity(intent);
    }

    /***
     * 调用拨号界面，不拨打电话
     * @param activity 实例对象
     * @param phone 电话号码
     */
    public void hrefCallPage(Activity activity, String phone) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        wActivity.startActivity(intent);
    }

    /***
     * 调用拨号，直接拨打电话
     * @param activity 实例对象
     * @param phone 电话号码
     */
    public void hrefCall(Activity activity, String phone) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(Intent.ACTION_CALL,
                Uri.parse("tel:" + phone));
        try {
            wActivity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", "没有或用户未同意电话权限");
        }
    }

    /**
     * 带Bundle参数启动activity并带activity回调
     *
     * @param activity    实例对象
     * @param targetcls   目标activity的class
     * @param requestCode 请求码
     * @param type        动画类型
     * @param bundle      Bundle参数，携带参数至第二个Activity
     * @param <T>         泛型
     */
    public <T> void hrefActivityForResult(Activity activity, Class<T> targetcls, int requestCode,
                                          int type, Bundle bundle) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetcls);
        intent.putExtras(bundle);
        wActivity.startActivityForResult(intent, requestCode);
        setAnimation(wActivity, type);
    }

    /**
     * 启动activity并带activity回调
     *
     * @param activity    实例对象
     * @param targetcls   目标activity的class
     * @param requestCode 请求码
     * @param type        动画类型
     * @param <T>         泛型
     */
    public <T> void hrefActivityForResult(Activity activity, Class<T> targetcls, int requestCode,
                                          int type) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetcls);
        wActivity.startActivityForResult(intent, requestCode);
        setAnimation(wActivity, type);
    }

    /**
     * 启动activity并带activity回调(无动画)
     *
     * @param activity    实例对象
     * @param targetcls   目标activity的class
     * @param requestCode 请求码
     * @param <T>         泛型
     */
    public <T> void hrefActivityForResult(Activity activity, Class<T> targetcls, int requestCode) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetcls);
        wActivity.startActivityForResult(intent, requestCode);
    }

    /**
     * 带可序列化Object参数启动activity并带activity回调
     *
     * @param activity    实例
     * @param targetClass 目标activity的class
     * @param requestCode 请求码
     * @param bundleKey   Bundle对象中参数的键
     * @param bundleValue Bundle对象中参数的值
     * @param type        动画类型
     * @param <T>         泛型
     */
    public <T> void hrefActivityForResult(Activity activity, Class<T> targetClass, int requestCode,
                                          String bundleKey, Object bundleValue, int type) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable(bundleKey, (Serializable) bundleValue);
        intent.putExtras(bundle);
        wActivity.startActivityForResult(intent, requestCode);
        setAnimation(wActivity, type);
    }

    /**
     * 带可序列化Object参数启动activity并带activity回调
     *
     * @param activity    实例
     * @param targetClass 目标activity的class
     * @param requestCode 请求码
     * @param bundleKey   Bundle对象中参数的键
     * @param bundleValue Bundle对象中参数的值
     * @param <T>         泛型
     */
    public <T> void hrefActivityForResult(Activity activity, Class<T> targetClass, int requestCode,
                                          String bundleKey, Object bundleValue) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable(bundleKey, (Serializable) bundleValue);
        intent.putExtras(bundle);
        wActivity.startActivityForResult(intent, requestCode);
    }

    /**
     * Bundle参数启动activity并带返回回调
     *
     * @param activity    实例
     * @param targetClass 目标activity的class
     * @param requestCode 请求码
     * @param bundle      Bundle对象
     * @param <T>         泛型
     */
    public <T> void hrefActivityForResult(Activity activity, Class<T> targetClass, int requestCode,
                                          Bundle bundle) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetClass);
        intent.putExtras(bundle);
        wActivity.startActivityForResult(intent, requestCode);
    }

    /**
     * 带Bundle跳转Activity
     *
     * @param activity  实例
     * @param targetcls 目标Activity
     * @param type      动画类型
     * @param bundle    携带参数的Bundle对象
     */
    public void hrefActivity(Activity activity, Class targetcls,
                             int type, Bundle bundle) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetcls);
        intent.putExtras(bundle);
        wActivity.startActivity(intent);
        setAnimation(wActivity, type);
    }

    /**
     * 带Bundle跳转Activity(不带动画)
     *
     * @param activity  实例
     * @param targetcls 目标Activity
     * @param bundle    携带参数的Bundle对象
     */
    public void hrefActivity(Activity activity, Class targetcls, Bundle bundle) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetcls);
        intent.putExtras(bundle);
        wActivity.startActivity(intent);
    }

    /**
     * 带可序列化Object参数跳转Activity（带动画）
     *
     * @param activity    上下文环境变量
     * @param targetClass 目标页面
     * @param bundleKey   Bundle中参数键
     * @param bundleValue Bundle中参数值
     * @param type        动画类型
     */
    public void hrefActivity(Activity activity, Class targetClass,
                             String bundleKey, Object bundleValue, int type) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable(bundleKey, (Serializable) bundleValue);
        intent.putExtras(bundle);
        wActivity.startActivity(intent);
        setAnimation(wActivity, type);
    }

    /**
     * 带可序列化Object参数跳转Activity（不带动画）
     *
     * @param activity    上下文环境变量
     * @param targetClass 目标页面
     * @param bundleKey   Bundle中参数键
     * @param bundleValue Bundle中参数值
     */
    public void hrefActivity(Activity activity, Class targetClass,
                             String bundleKey, Object bundleValue) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable(bundleKey, (Serializable) bundleValue);
        intent.putExtras(bundle);
        wActivity.startActivity(intent);
    }

    /**
     * 不带参数Activity跳转
     *
     * @param activity  实例
     * @param targetCls 目标Activity的Class
     * @param type      动画类型
     */
    public void hrefActivity(Activity activity, Class targetCls,
                             int type) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetCls);
        wActivity.startActivity(intent);
        setAnimation(wActivity, type);
    }

    /**
     * 不带参数Activity无动画跳转
     *
     * @param activity  实例
     * @param targetCls 目标Activity的Class
     */
    public void hrefActivity(Activity activity, Class targetCls) {
        //弱引用
        WeakReference<Activity> mActivity = new WeakReference<>(activity);
        Activity wActivity = mActivity.get();

        Intent intent = new Intent(wActivity, targetCls);
        wActivity.startActivity(intent);
    }

    /**
     * 不带参数Activity无动画跳转
     *
     * @param context   实例
     * @param targetCls 目标Activity的Class
     */
    public void hrefActivity(Context context, Class targetCls) {
        //弱引用
        WeakReference<Context> weakReference = new WeakReference<>(context);
        Context context1 = weakReference.get();

        Intent intent = new Intent(context1, targetCls);
        context1.startActivity(intent);
    }

    /**
     * activity动画
     *
     * @param activity 实例
     * @param type     动画类型，0：右进右出；1：无动画；2：淡入淡出；3：
     */
    public static void setAnimation(Activity activity, int type) {
        switch (type) {
            case 0:
                activity.overridePendingTransition(R.anim.common_push_left_in,
                        R.anim.common_push_left_out);
                break;
            case 1:
                activity.overridePendingTransition(0, 0);
                break;
            case 2:
                activity.overridePendingTransition(R.anim.common_z_zoomin_in,
                        R.anim.common_z_zoomout_out);
                break;
            case 3:
                activity.overridePendingTransition(R.anim.common_zoom_in_login,
                        R.anim.common_zoom_out_splash);
                break;
            default:
                break;
        }
    }
}
