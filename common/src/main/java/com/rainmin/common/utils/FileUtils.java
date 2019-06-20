package com.rainmin.common.utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import androidx.core.content.FileProvider;

/**
 * 文件操作类
 *
 * @author licong
 */
public class FileUtils {

    private static final String SDPATH = Environment.getExternalStorageDirectory() + "/";

    /**
     * 在SD卡上创建目录
     */
    public static File createSdCardDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdirs();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    public static File getAttachmentDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalFilesDir("attachments");
        } else {
            return context.getFilesDir();
        }
    }

    /**
     * 获取uri的绝对路径
     *
     * @param context 上下文对象
     * @param uri     地址
     * @return 绝对地址
     */
    public static String getAbsolutePath(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 将流写入本地SD
     */
    public static String writeBytesToDisk(final byte[] bytes, final String parentPath, final String fileName) {

        File dir = new File(parentPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir + "/" + fileName);
        if (file.exists()) {
            return dir + "/" + fileName;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dir + "/" + fileName);
            fos.write(bytes);
            fos.flush();
            return dir + "/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String writeStreamToDisk(final InputStream is, final String parentPath, final String fileName) {
        File dir = new File(parentPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir + "/" + fileName);
        if (file.exists()) {
            return dir + "/" + fileName;
        }
        byte[] buf = new byte[1024];
        int len;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dir + "/" + fileName);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return dir + "/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打开文件
     *
     * @param context  Activity上下文
     * @param filePath 文件路径
     */
    public static void openFile(Context context, String filePath) {
        if (context == null || TextUtils.isEmpty(filePath)) return;
        File file = new File(filePath);
        String extension = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();
        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            uri = FileProvider.getUriForFile(context, "longruan.provider", file);
        } else {
            uri = Uri.fromFile(file);
        }

        //给三方应用授权
        List<ResolveInfo> resInfoList = context.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        if (TextUtils.isEmpty(mime)) {
            intent.setData(uri);
        } else {
            intent.setDataAndType(uri, mime);
        }
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "没有找到能打开此文件类型的应用程序", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * copy a file and rename it
     *
     * @param srcPath  the path of source file
     * @param destDir  the directory of dest file
     * @param fileName the name of dest file
     * @return return the path of new file if successfully,otherwise return null
     */
    public static String copyFile(String srcPath, String destDir, String fileName) throws IllegalArgumentException {
        String result = null;
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(destDir)) {
            throw new IllegalArgumentException("srcPath or destDir is empty");
        }

        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File srcFile = new File(srcPath);
        if (TextUtils.isEmpty(fileName)) {
            fileName = srcFile.getName();
        }
        File destFile = new File(dir, File.separator + fileName);
        if (destFile.exists()) {
            destFile.delete(); // delete file
        }
        try {
            destFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileChannel srcChannel = null;
        FileChannel dstChannel = null;
        try {
            srcChannel = new FileInputStream(srcFile).getChannel();
            dstChannel = new FileOutputStream(destFile).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), dstChannel);
            result = destFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (srcChannel != null)
                    srcChannel.close();
                if (dstChannel != null)
                    dstChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * move a file to the specified directory and rename it
     *
     * @param srcPath  the path of source file
     * @param destDir  the directory of dest file
     * @param fileName the name of dest file
     * @return return the path of new file if move successfully,otherwise return null
     */
    public static String moveFile(String srcPath, String destDir, String fileName) {
        String result = null;
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(destDir)) {
            throw new IllegalArgumentException("srcPath or destDir is empty");
        }

        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File srcFile = new File(srcPath);
        if (TextUtils.isEmpty(fileName)) {
            fileName = srcFile.getName();
        }
        File destFile = new File(dir, File.separator + fileName);
        if (destFile.exists()) {
            destFile.delete(); // delete file
        }
        try {
            if (srcFile.renameTo(destFile)) {
                result = destFile.getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void deleteFiles(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IOException("not a readable directory: " + dir);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteFiles(file);
            }
            if (!file.delete()) {
                throw new IOException("failed to delete file: " + file);
            }
        }
    }

    /**
     * 获取SD卡的地址
     *
     * @return 若未取到则返回空字符串
     */
    @SuppressLint("NewApi")
    public static String getSDDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * 返回日志存储路径
     */
    public static String getLogPath(Context context) {
        File file = new File(getSDDir() + File.separatorChar + "longruan"
                + File.separatorChar + AppUtils.getAppName(context) + File.separatorChar + "log");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /***
     * 得到缓存文件夹的路径
     */
    @SuppressLint("NewApi")
    public static String getCacheDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getApplicationContext().getExternalCacheDir()
                    .getPath();
        } else {
            return context.getApplicationContext().getCacheDir().getPath();
        }
    }
}
