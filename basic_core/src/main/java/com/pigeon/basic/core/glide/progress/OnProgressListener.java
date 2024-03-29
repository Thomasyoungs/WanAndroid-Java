package com.pigeon.basic.core.glide.progress;

import androidx.annotation.WorkerThread;

/**
 * 描述：
 *
 * @author yangzhikuan
 * @date 2018/9/17
 */
public interface OnProgressListener {
    @WorkerThread
    void onProgress(float progress);
}
