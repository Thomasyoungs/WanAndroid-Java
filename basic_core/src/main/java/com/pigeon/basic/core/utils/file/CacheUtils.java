package com.pigeon.basic.core.utils.file;

import com.pigeon.basic.core.utils.file.FileUtils;

import com.pigeon.basic.core.utils.Utils;

import java.io.File;

/**
 * 缓存辅助类
 *
 * @author yangzhikuan
 * @date 18/4/23
 */
public class CacheUtils {

    /**
     * 获取系统默认缓存文件夹
     * 优先返回SD卡中的缓存文件夹
     */
    public static String getCacheDir() {
        File cacheFile = null;
        if (FileUtils.isSDCardAlive()) {
            cacheFile = Utils.getAppContext().getExternalCacheDir();
        }
        if (cacheFile == null) {
            cacheFile = Utils.getAppContext().getCacheDir();
        }
        return cacheFile.getAbsolutePath();
    }

    public static String getFilesDir() {
        File cacheFile = Utils.getAppContext().getFilesDir();
        return cacheFile.getAbsolutePath();
    }

    /**
     * 获取系统默认缓存文件夹内的缓存大小
     */
    public static String getTotalCacheSize() {
        long cacheSize = FileUtils.getSize(Utils.getAppContext().getCacheDir());
        if (FileUtils.isSDCardAlive()) {
            cacheSize += FileUtils.getSize(Utils.getAppContext().getExternalCacheDir());
        }
        return FileUtils.formatSize(cacheSize);
    }

    /**
     * 清除系统默认缓存文件夹内的缓存
     */
    public static void clearAllCache() {
        FileUtils.delete(Utils.getAppContext().getCacheDir());
        if (FileUtils.isSDCardAlive()) {
            FileUtils.delete(Utils.getAppContext().getExternalCacheDir());
        }
    }

}