package com.pigeon.cloud.module.main.activity

import com.pigeon.basic.core.base.BaseActivity
import com.pigeon.basic.core.base.BasePresenter
import com.pigeon.basic.core.base.BaseView
import com.pigeon.cloud.R
import com.pigeon.cloud.module.main.model.ChapterBean

/**
 * @author yangzhikuan
 * @date 2020/3/22
 */
class ArticleListActivity : BaseActivity<BasePresenter<BaseView>>() {

    companion object {
        @JvmStatic
        fun start(chapterBean: ChapterBean) {

        }
    }

    override fun getLayoutId(): Int = R.layout.activity_article_list

    override fun initPresenter(): BasePresenter<BaseView>? = null

    override fun initView() {
    }

    override fun loadData() {
    }
}