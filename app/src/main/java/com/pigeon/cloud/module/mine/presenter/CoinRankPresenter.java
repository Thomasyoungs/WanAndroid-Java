package com.pigeon.cloud.module.mine.presenter;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.mine.model.CoinRankBean;
import com.pigeon.cloud.module.mine.model.MineRequest;
import com.pigeon.cloud.module.mine.view.CoinRankView;
import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.mine.model.CoinRankBean;
import com.pigeon.cloud.module.mine.model.MineRequest;
import com.pigeon.cloud.module.mine.view.CoinRankView;
import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.mine.model.CoinRankBean;
import com.pigeon.cloud.module.mine.model.MineRequest;
import com.pigeon.cloud.module.mine.view.CoinRankView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 * 
 */
public class CoinRankPresenter extends BasePresenter<CoinRankView> {

    public void getCoinRankList(int page) {
        addToRxLife(MineRequest.getCoinRankList(page, new RequestListener<CoinRankBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, CoinRankBean data) {
                if (isAttach()) {
                    getBaseView().getCoinRankListSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getCoinRankListFail(code, msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
            }
        }));
    }

}
