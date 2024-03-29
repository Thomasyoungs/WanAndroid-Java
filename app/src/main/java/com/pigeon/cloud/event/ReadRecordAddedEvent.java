package com.pigeon.cloud.event;

import com.pigeon.cloud.db.model.ReadRecordModel;

import io.reactivex.annotations.NonNull;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class ReadRecordAddedEvent extends BaseEvent {
    private final ReadRecordModel readRecordModel;

    public ReadRecordAddedEvent(@NonNull ReadRecordModel readRecordModel) {
        this.readRecordModel = readRecordModel;
    }

    @NonNull
    public ReadRecordModel getReadRecordModel() {
        return readRecordModel;
    }
}
