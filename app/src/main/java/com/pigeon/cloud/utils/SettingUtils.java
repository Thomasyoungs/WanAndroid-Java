package com.pigeon.cloud.utils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import com.pigeon.basic.core.utils.SPUtils;
import com.pigeon.cloud.module.mine.model.HostEntity;
import com.pigeon.cloud.utils.web.HostInterceptUtils;

/**
 * @author yangzhikuan
 * @date 2019/5/18
 *
 */
public class SettingUtils {

    private static final String SP_NAME = "setting";
    private static final String KEY_SYSTEM_THEME = "KEY_SYSTEM_THEME";
    private static final String KEY_DARK_THEME = "KEY_DARK_THEME";
    private static final String KEY_SHOW_READ_LATER = "KEY_SHOW_READ_LATER";
    private static final String KEY_SHOW_READ_LATER_NOTIFICATION = "KEY_SHOW_READ_LATER_NOTIFICATION";
    private static final String KEY_SHOW_READ_RECORD = "KEY_SHOW_READ_RECORD";
    private static final String KEY_SHOW_TOP = "KEY_SHOW_TOP";
    private static final String KEY_SHOW_BANNER = "KEY_SHOW_BANNER";
    private static final String KEY_HIDE_ABOUT_ME = "KEY_HIDE_ABOUT_ME";
    private static final String KEY_HIDE_OPEN = "KEY_HIDE_OPEN";
    private static final String KEY_WEB_SWIPE_BACK_EDGE = "KEY_WEB_SWIPE_BACK_EDGE";
    private static final String KEY_RV_ANIM = "KEY_RV_ANIM";
    private static final String KEY_URL_INTERCEPT_TYPE = "KEY_URL_INTERCEPT_TYPE";
    private static final String KEY_HOST_WHITE = "KEY_HOST_WHITE";
    private static final String KEY_HOST_BLACK = "KEY_HOST_BLACK";
    private static final String KEY_SEARCH_HISTORY_MAX_COUNT = "KEY_SEARCH_HISTORY_MAX_COUNT";
    private static final String KEY_UPDATE_IGNORE_DURATION = "KEY_UPDATE_IGNORE_DURATION";

    private final SPUtils mSPUtils = SPUtils.newInstance(SP_NAME);

    private boolean mSystemTheme = true;
    private boolean mDarkTheme = false;
    private boolean mShowReadLaterNotification = true;
    private boolean mShowTop = true;
    private boolean mShowBanner = true;
    private int mUrlInterceptType = HostInterceptUtils.TYPE_NOTHING;
    private final List<HostEntity> mHostWhite = new ArrayList<>();
    private final List<HostEntity> mHostBlack = new ArrayList<>();
    private int mSearchHistoryMaxCount = 100;
    private long mUpdateIgnoreDuration = 1 * 24 * 60 * 60 * 1000L;

    private static class Holder {
        private static final SettingUtils INSTANCE = new SettingUtils();
    }

    public static SettingUtils getInstance() {
        return Holder.INSTANCE;
    }

    private SettingUtils() {
        mSystemTheme = mSPUtils.get(KEY_SYSTEM_THEME, mSystemTheme);
        mDarkTheme = mSPUtils.get(KEY_DARK_THEME, mDarkTheme);
        mShowReadLaterNotification = mSPUtils.get(KEY_SHOW_READ_LATER_NOTIFICATION, mShowReadLaterNotification);
        mShowTop = mSPUtils.get(KEY_SHOW_TOP, mShowTop);
        mShowBanner = mSPUtils.get(KEY_SHOW_BANNER, mShowBanner);
        mUrlInterceptType = mSPUtils.get(KEY_URL_INTERCEPT_TYPE, mUrlInterceptType);
        mSearchHistoryMaxCount = mSPUtils.get(KEY_SEARCH_HISTORY_MAX_COUNT, mSearchHistoryMaxCount);
        mUpdateIgnoreDuration = mSPUtils.get(KEY_UPDATE_IGNORE_DURATION, mUpdateIgnoreDuration);
        Gson gson = new Gson();
        String jsonWhite = mSPUtils.get(KEY_HOST_WHITE, "");
        try {
            List<HostEntity> list = gson.fromJson(jsonWhite, new TypeToken<List<HostEntity>>() {
            }.getType());
            mHostWhite.addAll(list);
        } catch (Exception e) {
            for (String host : HostInterceptUtils.WHITE_HOST) {
                mHostWhite.add(new HostEntity(host, false));
            }
            mSPUtils.save(KEY_HOST_WHITE, gson.toJson(mHostWhite));
        }
        String jsonBlack = mSPUtils.get(KEY_HOST_BLACK, "");
        try {
            List<HostEntity> list = gson.fromJson(jsonBlack, new TypeToken<List<HostEntity>>() {
            }.getType());
            mHostBlack.addAll(list);
        } catch (Exception e) {
            for (String host : HostInterceptUtils.BLACK_HOST) {
                mHostBlack.add(new HostEntity(host, false));
            }
            mSPUtils.save(KEY_HOST_BLACK, gson.toJson(mHostBlack));
        }
    }

    public void setSystemTheme(boolean systemTheme) {
        mSystemTheme = systemTheme;
        mSPUtils.save(KEY_SYSTEM_THEME, systemTheme);
    }

    public boolean isSystemTheme() {
        return mSystemTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        mDarkTheme = darkTheme;
        mSPUtils.save(KEY_DARK_THEME, darkTheme);
    }

    public boolean isDarkTheme() {
        return mDarkTheme;
    }

    public void setShowReadLaterNotification(boolean showReadLaterNotification) {
        mShowReadLaterNotification = showReadLaterNotification;
        mSPUtils.save(KEY_SHOW_READ_LATER_NOTIFICATION, showReadLaterNotification);
    }

    public boolean isShowReadLaterNotification() {
        return mShowReadLaterNotification;
    }

    public void setShowTop(boolean showTop) {
        mShowTop = showTop;
        mSPUtils.save(KEY_SHOW_TOP, showTop);
    }

    public boolean isShowTop() {
        return mShowTop;
    }

    public void setShowBanner(boolean showBanner) {
        mShowBanner = showBanner;
        mSPUtils.save(KEY_SHOW_BANNER, showBanner);
    }

    public boolean isShowBanner() {
        return mShowBanner;
    }

    public void setUrlInterceptType(int type) {
        mUrlInterceptType = type;
        mSPUtils.save(KEY_URL_INTERCEPT_TYPE, type);
    }

    public int getUrlInterceptType() {
        return mUrlInterceptType;
    }

    public void setHostWhiteIntercept(@NonNull List<HostEntity> hosts) {
        mHostWhite.clear();
        mHostWhite.addAll(hosts);
        mSPUtils.save(KEY_HOST_WHITE, new Gson().toJson(mHostWhite));
    }

    @NonNull
    public List<HostEntity> getHostWhiteIntercept() {
        return mHostWhite;
    }

    public void setHostBlackIntercept(@NonNull List<HostEntity> hosts) {
        mHostBlack.clear();
        mHostBlack.addAll(hosts);
        mSPUtils.save(KEY_HOST_BLACK, new Gson().toJson(mHostBlack));
    }

    @NonNull
    public List<HostEntity> getHostBlackIntercept() {
        return mHostBlack;
    }

    public void setSearchHistoryMaxCount(int count) {
        mSearchHistoryMaxCount = count;
        mSPUtils.save(KEY_SEARCH_HISTORY_MAX_COUNT, count);
    }

    public int getSearchHistoryMaxCount() {
        return mSearchHistoryMaxCount;
    }

    public void setUpdateIgnoreDuration(long dur) {
        mUpdateIgnoreDuration = dur;
        mSPUtils.save(KEY_UPDATE_IGNORE_DURATION, dur);
    }

    public long getUpdateIgnoreDuration() {
        return mUpdateIgnoreDuration;
    }
}
