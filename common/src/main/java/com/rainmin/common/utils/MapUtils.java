package com.rainmin.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

public class MapUtils {

    private static final String GAODE_MAP = "com.autonavi.minimap";
    private static final String BAIDU_MAP = "com.baidu.BaiduMap";
    private static final String TENXUN_MAP = "com.tencent.map";
    private static final String GOOGLE_MAP = "com.google.android.apps.maps";

    /**
     * 判断是否安装了某款应用
     * @param context context
     * @param packageName 应用包名
     * @return 已安装返回true, 否在返回false
     */
    private static boolean isAppInstalled(Context context, String packageName) {
        List<PackageInfo> packageInfoList = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfoList) {
            if (TextUtils.equals(packageInfo.packageName, packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 打开第三方地图APP开始导航
     * @param context context
     * @param lat 目的地维度
     * @param lon 目的地经度
     * @param dname 目的地名称
     */
    public static void startMapNavi(Context context, double lat, double lon, String dname) {
        if (isAppInstalled(context, GAODE_MAP)) {
            gotoGaodeNavi(context, lat, lon, dname);
        } else if (isAppInstalled(context, BAIDU_MAP)) {
            gotoBaiduNavi(context, lat, lon, dname);
        } else {
            Toast.makeText(context, "请安装高德地图或百度地图进行导航", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转到高德地图APP进行导航
     * @param context context
     * @param lat 目的地维度
     * @param lon 目的地经度
     * @param dname 目的地名称
     */
    private static void gotoGaodeNavi(Context context, double lat, double lon, String dname) {
        StringBuilder builder = new StringBuilder("androidamap://route/plan?sourceApplication=").append("amap")
                .append("&dlat=").append(lat)
                .append("&dlon=").append(lon)
                .append("&dev=").append(0)
                .append("&t").append(0);
        if (!TextUtils.isEmpty(dname)) {
            builder.append("dname=").append(dname);
        }

        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(builder.toString()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 跳转到百度地图APP进行导航
     * @param context context
     * @param lat 目的地维度
     * @param lon 目的地经度
     * @param dname 目的地名称，不能为空，否在会搜索不到结果
     */
    private static void gotoBaiduNavi(Context context, double lat, double lon, String dname) {
        double x = lon;
        double y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;

        StringBuilder builder = new StringBuilder("baidumap://map/direction?")
                .append("destination=name:").append(dname).append("|latlng:").append(lat).append(",").append(lon)
                .append("&coord_type=").append("gcj02")
                .append("&mode=").append("driving")
                .append("&src=").append("andr.rainmin.demo");

        Intent intent = new Intent();
        intent.setData(Uri.parse(builder.toString()));
        context.startActivity(intent);
    }
}
