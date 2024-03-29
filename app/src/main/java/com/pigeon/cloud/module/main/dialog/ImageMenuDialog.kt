package com.pigeon.cloud.module.main.dialog

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.widget.TextView
import com.github.chrisbanes.photoview.PhotoView
import per.goweii.anylayer.dialog.DialogLayer
import per.goweii.anylayer.widget.SwipeLayout
import per.goweii.anypermission.RequestListener
import com.pigeon.basic.core.permission.PermissionUtils
import com.pigeon.basic.ui.toast.ToastMaker
import com.pigeon.basic.utils.bitmap.BitmapUtils
import com.pigeon.basic.utils.ext.gone
import com.pigeon.basic.utils.ext.visible
import per.goweii.codex.decoder.CodeDecoder
import per.goweii.codex.processor.zxing.ZXingMultiDecodeQRCodeProcessor
import com.pigeon.cloud.R
import com.pigeon.cloud.utils.UrlOpenUtils
import kotlin.math.max

/**
 * @author yangzhikuan
 * @date 2020/3/7
 */
class ImageMenuDialog(
        private val context: Context,
        private val bitmap: Bitmap,
        private val qrcode: String?
) : DialogLayer(context) {

    companion object {
        fun create(context: Context, iv: PhotoView, onCreate: (ImageMenuDialog) -> Unit) {
            try {
                val drawable = iv.drawable ?: return
                val bd = drawable as BitmapDrawable
                val bitmap = bd.bitmap ?: return
                val scale: Float = max(720F / bitmap.width.toFloat(), 720F / bitmap.height.toFloat())
                val newBitmap = if (scale < 1F) {
                    val w = (bitmap.width * scale).toInt()
                    val h = (bitmap.height * scale).toInt()
                    val bitmapScaled = Bitmap.createScaledBitmap(bitmap, w, h, false)
                    bitmapScaled
                } else {
                    bitmap
                }
                CodeDecoder(ZXingMultiDecodeQRCodeProcessor()).decode(newBitmap, onSuccess = {
                    val dialog = ImageMenuDialog(context, bitmap, it.first().text).apply {
                        show()
                    }
                    onCreate.invoke(dialog)
                }, onFailure = {
                    val dialog = ImageMenuDialog(context, bitmap, null).apply {
                        show()
                    }
                    onCreate.invoke(dialog)
                })
            } catch (e: Exception) {
            }
        }
    }

    init {
        setBackgroundDimAmount(0.3F)
        setContentView(R.layout.dialog_image_menu)
        setSwipeDismiss(SwipeLayout.Direction.BOTTOM)
        setGravity(Gravity.BOTTOM)
        addOnClickToDismissListener(R.id.dialog_image_menu_iv_dismiss)
    }

    private val tv_save by lazy { requireView<TextView>(R.id.dialog_image_menu_tv_save) }
    private val tv_qrcode by lazy { requireView<TextView>(R.id.dialog_image_menu_tv_qrcode) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onAttach() {
        super.onAttach()
        if (qrcode.isNullOrEmpty()) {
            tv_qrcode.gone()
        } else {
            tv_qrcode.visible()
            tv_qrcode.setOnClickListener {
                UrlOpenUtils.with(qrcode).open(context)
                dismiss()
            }
        }
        tv_save.setOnClickListener {
            saveBitmap(bitmap)
            dismiss()
        }
    }

    private fun saveBitmap(bitmap: Bitmap) {
        PermissionUtils.request(object : RequestListener {
            override fun onSuccess() {
                if (BitmapUtils.saveGallery(bitmap, "wanandroid_article_image_${System.currentTimeMillis()}")) {
                    ToastMaker.showShort("以保存到相册")
                } else {
                    ToastMaker.showShort("保存失败")
                }
            }

            override fun onFailed() {}
        }, context, 0, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}