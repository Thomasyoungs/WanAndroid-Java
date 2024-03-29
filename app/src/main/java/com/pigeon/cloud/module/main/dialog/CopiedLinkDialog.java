package com.pigeon.cloud.module.main.dialog;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.pigeon.cloud.utils.CopiedTextProcessor;
import com.pigeon.cloud.utils.router.Router;

import per.goweii.anylayer.Layer;
import per.goweii.anylayer.popup.PopupLayer;
import per.goweii.anylayer.widget.SwipeLayout;
import com.pigeon.cloud.R;
import com.pigeon.cloud.utils.CopiedTextProcessor;
import com.pigeon.cloud.utils.router.Router;
import com.pigeon.cloud.utils.CopiedTextProcessor;
import com.pigeon.cloud.utils.router.Router;

/**
 * @author yangzhikuan
 * @date 2019/10/19
 * 
 */
public class CopiedLinkDialog extends PopupLayer {

    private final String link;

    public CopiedLinkDialog(View targetView, String link) {
        super(targetView);
        this.link = link;
        setContentView(R.layout.dialog_copied_link);
        setInterceptKeyEvent(false);
        setOutsideInterceptTouchEvent(false);
        setHorizontal(Align.Horizontal.ALIGN_LEFT);
        setVertical(Align.Vertical.ALIGN_BOTTOM);
        setDirection(Align.Direction.HORIZONTAL);
        setSwipeDismiss(SwipeLayout.Direction.LEFT);
        addOnClickToDismissListener(new OnClickListener() {
            @Override
            public void onClick(@NonNull Layer layer, @NonNull View v) {
                CopiedTextProcessor.getInstance().processed();
            }
        }, R.id.dialog_copied_link_iv_close);
        addOnClickToDismissListener(new OnClickListener() {
            @Override
            public void onClick(@NonNull Layer layer, @NonNull View v) {
                CopiedTextProcessor.getInstance().processed();
                Router.routeTo(link);
            }
        }, R.id.dialog_copied_link_rl);
    }

    public String getLink() {
        return link;
    }

    @Override
    public void onAttach() {
        super.onAttach();
        TextView tvLink = requireView(R.id.dialog_copied_link_tv_link);
        tvLink.setText(link);
    }
}
