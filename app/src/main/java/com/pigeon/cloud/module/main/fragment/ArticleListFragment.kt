package com.pigeon.cloud.module.main.fragment

import com.pigeon.basic.core.base.BaseFragment
import com.pigeon.cloud.R
import com.pigeon.cloud.module.main.contract.ArticleListPresenter
import com.pigeon.cloud.module.main.contract.ArticleListView

/**
 * @author yangzhikuan
 * @date 2020/3/22
 */
class ArticleListFragment : BaseFragment<ArticleListPresenter>(), ArticleListView {

    override fun getLayoutRes(): Int = R.layout.fragment_article_list

    override fun initPresenter(): ArticleListPresenter = ArticleListPresenter()

    override fun initView() {
    }

    override fun loadData() {
    }
}