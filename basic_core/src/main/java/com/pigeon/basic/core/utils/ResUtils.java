package com.pigeon.basic.core.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ArrayRes;
import androidx.annotation.AttrRes;
import androidx.annotation.BoolRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 获取资源文件的工具类
 *
 * @author yangzhikuan
 * @date 2018/7/9-下午4:56
 */
public class ResUtils {

    public static Resources getResources() {
        return Utils.getAppContext().getResources();
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(Utils.getAppContext(), id);
    }

    public static String getString(@StringRes int id) {
        return getResources().getString(id);
    }

    @Deprecated
    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(Utils.getAppContext(), id);
    }

    public static int getColor(@NonNull Context context, @ColorRes int id) {
        return context.getResources().getColor(id);
    }

    public static int getColor(@NonNull View view, @ColorRes int id) {
        return view.getContext().getResources().getColor(id);
    }

    public static int getThemeColor(@NonNull View view, @AttrRes int id) {
        return getThemeColor(view.getContext(), id);
    }

    public static int getThemeColor(Context context, @AttrRes int id) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{id});
        int color = typedArray.getColor(0, Color.TRANSPARENT);
        typedArray.recycle();
        return color;
    }

    public static float getDimens(@DimenRes int id) {
        return getResources().getDimension(id);
    }

    public static String[] getStringArray(@ArrayRes int id) {
        return getResources().getStringArray(id);
    }

    public static boolean getBoolean(@BoolRes int id) {
        return getResources().getBoolean(id);
    }

    public static int getInteger(@IntegerRes int id) {
        return getResources().getInteger(id);
    }

    public static String getAssets(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = Utils.getAppContext().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
