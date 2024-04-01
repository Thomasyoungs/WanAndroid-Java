package com.pigeon.basic.core.utils;

import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * @author yangzhikuan
 * @date 2019/11/10
 *
 */
public class UriUtils {

    public static Uri getFileUri(File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(Utils.getAppContext(), Utils.getAppContext().getPackageName() + ".file.path.share", file);
        }
        return Uri.fromFile(file);
    }
}
