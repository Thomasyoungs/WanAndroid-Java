package com.pigeon.cloud.utils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pigeon.cloud.widget.BravhLoadMoreView;

import com.pigeon.cloud.widget.BravhLoadMoreView;
import com.pigeon.cloud.widget.BravhLoadMoreView;

/**
 * @author yangzhikuan
 * @date 2019/5/20
 *
 */
public class RvConfigUtils {

    public static class RvAnim {
        public static final int NONE = 0;
        public static final int ALPHAIN = 1;
        public static final int SCALEIN = 2;
        public static final int SLIDEIN_BOTTOM = 3;
        public static final int SLIDEIN_LEFT = 4;
        public static final int SLIDEIN_RIGHT = 5;
    }

    public static String getName(int anim) {
        String name = "";
        switch (anim) {
            default:
                break;
            case RvAnim.NONE:
                name = "无";
                break;
            case RvAnim.ALPHAIN:
                name = "渐显";
                break;
            case RvAnim.SCALEIN:
                name = "缩放";
                break;
            case RvAnim.SLIDEIN_BOTTOM:
                name = "底部滑入";
                break;
            case RvAnim.SLIDEIN_LEFT:
                name = "左侧滑入";
                break;
            case RvAnim.SLIDEIN_RIGHT:
                name = "右侧滑入";
                break;
        }
        return name;
    }

    public static void init(BaseQuickAdapter<?, ? extends BaseViewHolder> adapter) {
        adapter.setLoadMoreView(new BravhLoadMoreView());
    }

    public static void setAnim(BaseQuickAdapter<?, ? extends BaseViewHolder> adapter, int anim) {
        switch (anim) {
            default:
                break;
            case RvAnim.NONE:
                adapter.closeLoadAnimation();
                break;
            case RvAnim.ALPHAIN:
                adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                break;
            case RvAnim.SCALEIN:
                adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                break;
            case RvAnim.SLIDEIN_BOTTOM:
                adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
                break;
            case RvAnim.SLIDEIN_LEFT:
                adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                break;
            case RvAnim.SLIDEIN_RIGHT:
                adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
                break;
        }
    }
}
