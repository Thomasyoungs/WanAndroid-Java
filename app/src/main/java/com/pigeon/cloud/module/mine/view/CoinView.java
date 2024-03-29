package com.pigeon.cloud.module.mine.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.mine.model.CoinRecordBean;
import com.pigeon.basic.core.base.BaseView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface CoinView extends BaseView {
    void getCoinSuccess(int code, int coin);

    void getCoinFail(int code, String msg);

    void getCoinRecordListSuccess(int code, CoinRecordBean data);

    void getCoinRecordListFail(int code, String msg);
}
