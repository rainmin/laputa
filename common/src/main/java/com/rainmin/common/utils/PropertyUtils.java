package com.rainmin.common.utils;


import com.rainmin.common.base.BaseApplication;

import android.graphics.Color;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * <dl>  Class Description
 * <dd> 项目名称：HSTCFrame
 * <dd> 类名称：PropertyUtils
 * <dd> 类描述：获取settings.properties相关属性
 * <dd> 创建时间：2017年6月6日12:03:17
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author lujing
 * @version 1.0
 */
public class PropertyUtils implements Serializable {
    private Properties prop;
    private String defaultColorValue = "#6dacfb";
    private String propertiesName = "settings";

    private static class PropertyUtilHolder {

        static final PropertyUtils INSTANCE = new PropertyUtils();
    }

    private PropertyUtils() {
    }

    public static PropertyUtils getInstance() {

        return PropertyUtilHolder.INSTANCE;
    }

    /**
     * 获取指定properties文件中的指定key对应的值
     *
     * @param key            键
     * @param defaultVaule   默认值（如果没有对应的key或者出现异常时返回的默认值）
     * @param propertiesName properties文件名称
     * @return String 值
     */
    public String getValue(String key, String defaultVaule, String propertiesName) {
        if (prop == null) {
            prop = new Properties();
        }
        String value;
        InputStream in = BaseApplication.class.getResourceAsStream("/assets/" + propertiesName + ".properties");
        if (in == null) {
            Log.e("PropertyUtils", "配置文件不存在");
            prop = null;
            return defaultVaule;
        }
        // InputStream in = new BufferedInputStream (new
        // FileInputStream("sets.properties"));
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        try {
            prop.load(bf);
            value = prop.getProperty(key, defaultVaule);
            in.close();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
            return defaultVaule;
        }
        return value;
    }

    /**
     * 获取指定properties文件中的指定key对应的值
     *
     * @param key 键
     * @return String 值
     */
    public String getValue(String key) {
        if (prop == null) {
            prop = new Properties();
        }
        String value;
        InputStream in = BaseApplication.class.getResourceAsStream("/assets/" + propertiesName + ".properties");
        if (in == null) {
            Log.e("PropertyUtl", "配置文件不存在");
            prop = null;
            return "";
        }

        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        try {
            prop.load(bf);
            value = prop.getProperty(key);
            in.close();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return value;
    }

    /**
     * <b>方法描述： 获取配置文件中配置的主色调</b>
     * <dd>方法作用：
     * <dd>适用条件：
     * <dd>执行流程：
     * <dd>使用方法：
     * <dd>注意事项：
     * 2016-5-4上午11:50:28
     *
     * @return int 颜色值
     * @since Met 1.0
     */
    public int getMainColor() {
        String mainColor = getValue("mainColor", defaultColorValue, propertiesName);
        return Color.parseColor(mainColor);
    }

    /**
     * <b>方法描述： 获取文字主色调</b>
     * <dd>方法作用：
     * <dd>适用条件：
     * <dd>执行流程：
     * <dd>使用方法：
     * <dd>注意事项：
     * 2016-5-4上午11:51:22
     *
     * @return int 颜色值
     * @since Met 1.0
     */
    public int getMainTextColor() {
        String mainTextColor = getValue("mainTextColor", defaultColorValue, propertiesName);
        return Color.parseColor(mainTextColor);
    }

    /**
     * 获取配置文件中所有值
     *
     * @return Map 集合
     */
    public Map<String, String> getValues() {
        Map<String, String> propertiesMap;
        if (prop == null) {
            prop = new Properties();
        }
        propertiesMap = new HashMap<>();
        InputStream in = BaseApplication.class.getResourceAsStream("/assets/" + propertiesName + ".properties");
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        try {
            prop.load(bf);
            Iterator<Map.Entry<Object, Object>> it = prop.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                value = new String(value.toString().getBytes("utf-8"));
                propertiesMap.put(key.toString().trim(), value.toString().trim());
            }
            in.close();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
            propertiesMap = null;
        }
        return propertiesMap;
    }


    /**
     * 修改properties的值
     *
     * @param key            键
     * @param value          值
     * @param propertiesName 文件名
     */
    public void updateValue(String key, String value, String propertiesName) {
        Properties props = new Properties();
        InputStream is;
        OutputStream out = null;
        try {
            is = BaseApplication.class.getResourceAsStream("/assets/" + propertiesName + ".properties");
            props.load(is);
            URL url = BaseApplication.class.getResource("/assets/" + propertiesName + ".properties");
            String path = url.getPath();
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            out = new FileOutputStream(file);
            //在修改值之前关闭is
            is.close();
            props.setProperty(key, value);
            props.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
