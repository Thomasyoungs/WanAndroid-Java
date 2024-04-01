package com.pigeon.cloud.module.main.dialog

import android.animation.Animator
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import per.goweii.anylayer.dialog.DialogLayer
import per.goweii.anylayer.utils.AnimatorHelper
import per.goweii.anylayer.widget.SwipeLayout
import com.pigeon.basic.core.glide.GlideHelper
import com.pigeon.basic.core.glide.transformation.ScaleDownTransformation
import com.pigeon.basic.core.utils.ext.gone
import com.pigeon.basic.core.utils.ext.visible
import com.pigeon.cloud.R
import com.pigeon.cloud.widget.ImagePreviewView

/**
 * @author yangzhikuan
 * @date 2020/3/7
 */
class ImagePreviewDialog(
        context: Context,
        private val imageUrl: String
) : DialogLayer(context) {

    private var imageMenuDialog: ImageMenuDialog? = null

    init {
        setBackgroundDimAmount(1F)
        setInterceptKeyEvent(true)
        setCancelableOnClickKeyBack(true)
        setContentView(R.layout.dialog_image_preview)
        setContentAnimator(object : AnimatorCreator {
            override fun createInAnimator(target: View): Animator {
                val ll_bar = requireView<LinearLayout>(R.id.dialog_image_preview_ll_bar)
                val dl = requireView<SwipeLayout>(R.id.dialog_image_preview_dl)
                return AnimatorSet().apply {
                    playTogether(
                            AnimatorHelper.createTopInAnim(ll_bar),
                            AnimatorHelper.createZoomAlphaInAnim(dl)
                    )
                }
            }

            override fun createOutAnimator(target: View): Animator {
                val ll_bar = requireView<LinearLayout>(R.id.dialog_image_preview_ll_bar)
                val dl = requireView<SwipeLayout>(R.id.dialog_image_preview_dl)
                return AnimatorSet().apply {
                    playTogether(
                            AnimatorHelper.createTopOutAnim(ll_bar),
                            AnimatorHelper.createZoomAlphaOutAnim(dl)
                    )
                }
            }
        })
    }

    private val ll_bar by lazy { requireView<LinearLayout>(R.id.dialog_image_preview_ll_bar) }
    private val iv_close by lazy { requireView<ImageView>(R.id.dialog_image_preview_iv_close) }
    private val ipv by lazy { requireView<ImagePreviewView>(R.id.dialog_image_preview_pv) }
    private val dl by lazy { requireView<SwipeLayout>(R.id.dialog_image_preview_dl) }
    private val tv_tip by lazy { requireView<TextView>(R.id.dialog_image_preview_tv_tip) }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onAttach() {
        super.onAttach()
        iv_close.setOnClickListener {
            dismiss()
        }
        ipv.onImagePreviewListener = object : ImagePreviewView.OnImagePreviewListener {
            override fun onTap() {
                dismiss()
            }

            override fun onTouching1() {
                if (ipv.isShown) {
                    val dl = requireView<SwipeLayout>(R.id.dialog_image_preview_dl)
                    dl.setSwipeDirection(SwipeLayout.Direction.BOTTOM)
                }
            }

            override fun onTouching2() {
                if (ipv.isShown) {
                    val dl = requireView<SwipeLayout>(R.id.dialog_image_preview_dl)
                    dl.setSwipeDirection(0)
                }
            }

            override fun onTouchingUp() {
                if (ipv.isShown) {
                    val dl = requireView<SwipeLayout>(R.id.dialog_image_preview_dl)
                    dl.setSwipeDirection(SwipeLayout.Direction.BOTTOM)
                }
            }

            override fun onLongClick() {
                imageMenuDialog?.dismiss()
                ImageMenuDialog.create(activity, ipv) {
                    imageMenuDialog = it
                }
            }
        }
        GlideHelper.with(ipv.context)
                .cache(true)
                .placeHolder(ContextCompat.getDrawable(ipv.context, R.drawable.shape_image_perview_place_holder))
                .errorHolder(ContextCompat.getDrawable(ipv.context, R.drawable.shape_image_perview_place_holder))
                .load(imageUrl)
                .onProgressListener { progress ->
                    when {
                        progress >= 1F -> {
                            tv_tip.gone()
                            tv_tip.text = "加载成功"
                        }
                        progress < 0F -> {
                            tv_tip.visible()
                            tv_tip.text = "加载失败"
                        }
                        progress == 0F -> {
                            tv_tip.visible()
                            tv_tip.text = "加载中"
                        }
                        else -> {
                            tv_tip.visible()
                            tv_tip.text = "加载中(${(progress * 100).toInt()}%)"
                        }
                    }
                }
                .transformation(ScaleDownTransformation())
                .into(ipv)
        dl.setSwipeDirection(SwipeLayout.Direction.BOTTOM)
        dl.setOnSwipeListener(object : SwipeLayout.OnSwipeListener {
            override fun onStart(direction: Int, fraction: Float) {
                imageMenuDialog?.dismiss()
            }

            override fun onSwiping(direction: Int, fraction: Float) {
                viewHolder.background.alpha = 1F - fraction
                ll_bar.translationY = -ll_bar.bottom * fraction
            }

            override fun onEnd(direction: Int, fraction: Float) {
                if (fraction == 1F) {
                    dl.post { dismiss(false) }
                }
            }
        })
    }
}