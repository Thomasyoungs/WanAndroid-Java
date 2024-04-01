package com.pigeon.cloud.module.main.dialog;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.pigeon.basic.core.utils.ResUtils;
import com.pigeon.cloud.utils.SettingUtils;
import com.pigeon.cloud.utils.UserUtils;
import com.pigeon.cloud.utils.web.HostInterceptUtils;

import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.Layer;
import per.goweii.anylayer.widget.SwipeLayout;

import com.pigeon.cloud.R;
import com.pigeon.cloud.module.mine.activity.HostInterruptActivity;
import com.pigeon.cloud.module.mine.activity.SettingActivity;

/**
 * @author yangzhikuan
 * @date 2019/5/20
 *
 */
public class WebMenuDialog {

    public static void show(@NonNull Context context,
                            final String url,
                            final boolean collected,
                            final boolean readLater,
                            @NonNull OnMenuClickListener listener) {
        AnyLayer.dialog(context)
                .setContentView(R.layout.dialog_web_menu)
                .setBackgroundDimDefault()
                .setSwipeDismiss(SwipeLayout.Direction.BOTTOM)
                .setGravity(Gravity.BOTTOM)
                .addOnClickToDismissListener(
                        new Layer.OnClickListener() {
                            @Override
                            public void onClick(@NonNull Layer layer, @NonNull View v) {
                                switch (v.getId()) {
                                    default:
                                        break;
                                    case R.id.dialog_web_menu_iv_share_article:
                                        if (UserUtils.getInstance().doIfLogin(v.getContext())) {
                                            listener.onShareArticle();
                                        }
                                        break;
                                    case R.id.dialog_web_menu_iv_collect:
                                        if (UserUtils.getInstance().doIfLogin(v.getContext())) {
                                            listener.onCollect();
                                        }
                                        break;
                                    case R.id.dialog_web_menu_iv_read_later:
                                        listener.onReadLater();
                                        break;
                                    case R.id.dialog_web_menu_iv_home:
                                        listener.onHome();
                                        break;
                                    case R.id.dialog_web_menu_iv_refresh:
                                        listener.onRefresh();
                                        break;
                                    case R.id.dialog_web_menu_iv_close_activity:
                                        listener.onCloseActivity();
                                        break;
                                    case R.id.dialog_web_menu_iv_setting:
                                        SettingActivity.start(context);
                                        break;
                                    case R.id.dialog_web_menu_iv_share:
                                        listener.onShare();
                                        break;
                                    case R.id.dialog_web_menu_iv_go_top:
                                        listener.onGoTop();
                                        break;
                                }
                            }
                        },
                        R.id.dialog_web_menu_iv_share_article,
                        R.id.dialog_web_menu_iv_read_later,
                        R.id.dialog_web_menu_iv_home,
                        R.id.dialog_web_menu_iv_collect,
                        R.id.dialog_web_menu_iv_refresh,
                        R.id.dialog_web_menu_iv_go_top,
                        R.id.dialog_web_menu_iv_close_activity,
                        R.id.dialog_web_menu_iv_dismiss,
                        R.id.dialog_web_menu_iv_setting,
                        R.id.dialog_web_menu_iv_share)
                .addOnClickListener(
                        new Layer.OnClickListener() {
                            @Override
                            public void onClick(@NonNull Layer layer, @NonNull View v) {
                                switch (SettingUtils.getInstance().getUrlInterceptType()) {
                                    default:
                                    case HostInterceptUtils.TYPE_NOTHING:
                                        SettingUtils.getInstance().setUrlInterceptType(HostInterceptUtils.TYPE_ONLY_WHITE);
                                        break;
                                    case HostInterceptUtils.TYPE_ONLY_WHITE:
                                        SettingUtils.getInstance().setUrlInterceptType(HostInterceptUtils.TYPE_INTERCEPT_BLACK);
                                        break;
                                    case HostInterceptUtils.TYPE_INTERCEPT_BLACK:
                                        SettingUtils.getInstance().setUrlInterceptType(HostInterceptUtils.TYPE_NOTHING);
                                        break;
                                }
                                ImageView iv_interrupt = layer.requireView(R.id.dialog_web_menu_iv_interrupt);
                                TextView tv_interrupt = layer.requireView(R.id.dialog_web_menu_tv_interrupt);
                                switchInterruptState(iv_interrupt, tv_interrupt);
                            }
                        },
                        R.id.dialog_web_menu_iv_interrupt)
                .addOnBindDataListener(new Layer.OnBindDataListener() {
                    @Override
                    public void onBindData(@NonNull Layer layer) {
                        TextView tv_host = layer.requireView(R.id.dialog_web_menu_tv_host);
                        String host = null;
                        if (!TextUtils.isEmpty(url)) {
                            Uri uri = Uri.parse(url);
                            host = uri.getHost();
                        }
                        if (TextUtils.isEmpty(host)) {
                            tv_host.setVisibility(View.GONE);
                        } else {
                            tv_host.setVisibility(View.VISIBLE);
                            tv_host.setText("网页由 " + host + " 提供");
                        }
                        ImageView iv_collect = layer.requireView(R.id.dialog_web_menu_iv_collect);
                        TextView tv_collect = layer.requireView(R.id.dialog_web_menu_tv_collect);
                        switchCollectState(iv_collect, tv_collect, collected);
                        ImageView iv_read_later = layer.requireView(R.id.dialog_web_menu_iv_read_later);
                        switchReadLaterState(iv_read_later, readLater);
                        ImageView iv_interrupt = layer.requireView(R.id.dialog_web_menu_iv_interrupt);
                        TextView tv_interrupt = layer.requireView(R.id.dialog_web_menu_tv_interrupt);
                        switchInterruptState(iv_interrupt, tv_interrupt);
                        iv_interrupt.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                HostInterruptActivity.start(context);
                                layer.dismiss();
                                return true;
                            }
                        });
                    }
                })
                .show();
    }

    private static void switchCollectState(ImageView iv_collect, TextView tv_collect, boolean collected) {
        setIconChecked(iv_collect, collected);
        if (collected) {
            tv_collect.setText("已收藏");
        } else {
            tv_collect.setText("收藏");
        }
    }

    private static void switchReadLaterState(ImageView iv_read_later, boolean readLater) {
        setIconChecked(iv_read_later, readLater);
    }

    private static void switchInterruptState(ImageView iv_interrupt, TextView tv_interrupt) {
        switch (SettingUtils.getInstance().getUrlInterceptType()) {
            default:
            case HostInterceptUtils.TYPE_NOTHING:
                setIconChecked(iv_interrupt, false);
                tv_interrupt.setText(HostInterceptUtils.getName(HostInterceptUtils.TYPE_NOTHING));
                break;
            case HostInterceptUtils.TYPE_ONLY_WHITE:
                setIconChecked(iv_interrupt, true);
                tv_interrupt.setText(HostInterceptUtils.getName(HostInterceptUtils.TYPE_ONLY_WHITE));
                break;
            case HostInterceptUtils.TYPE_INTERCEPT_BLACK:
                setIconChecked(iv_interrupt, true);
                tv_interrupt.setText(HostInterceptUtils.getName(HostInterceptUtils.TYPE_INTERCEPT_BLACK));
                break;
        }
    }

    private static void setIconChecked(ImageView iv, boolean checked) {
        if (checked) {
            iv.setColorFilter(ResUtils.getThemeColor(iv, R.attr.colorIconOnMain));
            iv.setBackgroundResource(R.drawable.bg_press_color_main_radius_max);
        } else {
            iv.setColorFilter(ResUtils.getThemeColor(iv, R.attr.colorIconSurface));
            iv.setBackgroundResource(R.drawable.bg_press_color_surface_top_radius_max);
        }
    }

    public interface OnMenuClickListener {
        void onShareArticle();

        void onCollect();

        void onReadLater();

        void onHome();

        void onRefresh();

        void onGoTop();

        void onCloseActivity();

        void onShare();
    }

}
