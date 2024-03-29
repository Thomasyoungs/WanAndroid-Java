package com.pigeon.cloud.module.main.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;

import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.Layer;
import per.goweii.anylayer.widget.SwipeLayout;
import com.pigeon.cloud.R;

/**
 * @author yangzhikuan
 * @date 2019/5/20
 * 
 */
public class WebShareDialog {

    public static void show(@NonNull Context context,
                            @NonNull OnShareClickListener listener) {
        AnyLayer.dialog(context)
                .setContentView(R.layout.dialog_web_share)
                .setGravity(Gravity.BOTTOM)
                .setBackgroundDimDefault()
                .setSwipeDismiss(SwipeLayout.Direction.BOTTOM)
                .addOnClickToDismissListener(new Layer.OnClickListener() {
                                                 @Override
                                                 public void onClick(@NonNull Layer layer, @NonNull View v) {
                                                     switch (v.getId()) {
                                                         default:
                                                             break;
                                                         case R.id.dialog_web_share_iv_capture:
                                                             listener.onCapture();
                                                             break;
                                                         case R.id.dialog_web_share_iv_qrcode:
                                                             listener.onQrcode();
                                                             break;
                                                     }
                                      }
                                  },
                        R.id.dialog_web_share_iv_capture,
                        R.id.dialog_web_share_iv_qrcode,
                        R.id.dialog_web_share_iv_dismiss)
                .show();
    }

    public interface OnShareClickListener {
        void onCapture();

        void onQrcode();
    }

}
