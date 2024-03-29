package com.pigeon.cloud.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.pigeon.basic.core.glide.GlideHelper;
import com.pigeon.basic.core.glide.transformation.BlurTransformation;
import com.pigeon.basic.core.glide.transformation.RoundTransformation;
import com.pigeon.basic.core.glide.GlideHelper;
import com.pigeon.basic.core.glide.transformation.BlurTransformation;
import com.pigeon.basic.core.glide.transformation.RoundTransformation;
import com.pigeon.cloud.R;
import com.pigeon.basic.core.glide.GlideHelper;
import com.pigeon.basic.core.glide.transformation.BlurTransformation;
import com.pigeon.basic.core.glide.transformation.RoundTransformation;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 * 
 */
public class ImageLoader {

    public static void image(ImageView imageView, String url) {
        GlideHelper.with(imageView.getContext())
                .errorHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .placeHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .cache(true)
                .load(url)
                .into(imageView);
    }

    public static void roundImage(ImageView imageView, String url, int radius) {
        GlideHelper.with(imageView.getContext())
                .errorHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .placeHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .transformation(new RoundTransformation(radius))
                .cache(true)
                .load(url)
                .into(imageView);
    }

    public static void gif(ImageView imageView, String url) {
        GlideHelper.with(imageView.getContext())
                .asGif()
                .errorHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .placeHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .cache(true)
                .load(url)
                .into(imageView);
    }

    public static void banner(ImageView imageView, String url) {
        GlideHelper.with(imageView.getContext())
                .errorHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .placeHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .cache(true)
                .load(url)
                .into(imageView);
    }

    public static void userIcon(ImageView imageView, String url) {
        GlideHelper.with(imageView.getContext())
                .cache(true)
                .errorHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .placeHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .load(url)
                .into(imageView);
    }

    public static void userIcon(ImageView imageView, Uri uri) {
        GlideHelper.with(imageView.getContext())
                .cache(true)
                .errorHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .placeHolder(ContextCompat.getDrawable(imageView.getContext(), R.drawable.shape_image_place_holder))
                .load(uri)
                .into(imageView);
    }

    public static void userBlur(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
            return;
        }
        GlideHelper.with(imageView.getContext())
                .cache(true)
                .load(url)
                .transformation(new BlurTransformation(0.2F))
                .into(imageView);
    }

    public static void userBlur(ImageView imageView, Uri uri) {
        if (uri == null) {
            imageView.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
            return;
        }
        GlideHelper.with(imageView.getContext())
                .cache(true)
                .load(uri)
                .transformation(new BlurTransformation(0.2F))
                .into(imageView);
    }

    public static void userBlur(ImageView imageView, int res) {
        GlideHelper.with(imageView.getContext())
//                .errorHolder(R.drawable.image_holder)
//                .placeHolder(R.drawable.image_holder)
                .cache(true)
                .load(res)
                .transformation(new BlurTransformation(0.2F))
                .into(imageView);
    }
}
