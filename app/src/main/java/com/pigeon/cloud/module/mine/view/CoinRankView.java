package com.pigeon.cloud.module.mine.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.mine.model.CoinRankBean;
import com.pigeon.basic.core.base.BaseView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface CoinRankView extends BaseView {
    void getCoinRankListSuccess(int code, CoinRankBean data);

    void getCoinRankListFail(int code, String msg);
}
