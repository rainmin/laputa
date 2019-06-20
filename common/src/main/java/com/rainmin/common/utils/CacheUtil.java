package com.rainmin.common.utils;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;

/**
 * <dl>  Class Description
 * <dd> 项目名称：StudyDemo
 * <dd> 类名称：
 * <dd> 类描述：
 * <dd> 创建时间：2017/6/30
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author ljhl
 * @version 1.0
 * @see
 */

public class CacheUtil {

    //缓存10M
    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    /**
     * 获取缓存实例
     * @param context Application Context
     * @return Cache
     */
    public static Cache getCache(Context context) {

        //设置缓存路径
        final String baseDir = FileUtils.getCacheDirPath(context);
        final File cacheDir = new File(baseDir, "HttpResponseCache");
        return new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
