package com.pigeon.cloud.module.mine.activity

import android.content.Context
import android.content.Intent
import android.view.View
import butterknife.BindView
import kotlinx.android.synthetic.main.activity_message.*
import per.goweii.actionbarex.ActionBarEx
import per.goweii.actionbarex.common.ActionIconView
import com.pigeon.basic.core.adapter.FixedFragmentPagerAdapter
import com.pigeon.basic.core.base.BaseActivity
import com.pigeon.basic.core.base.BasePresenter
import com.pigeon.basic.core.base.BaseView
import com.pigeon.cloud.R
import com.pigeon.cloud.module.mine.fragment.MessageReadedFragment
import com.pigeon.cloud.module.mine.fragment.MessageUnreadFragment
import com.pigeon.cloud.utils.MagicIndicatorUtils

/**
 * @author yangzhikuan
 * @date 2019/5/17
 * 
 */
class MessageActivity : BaseActivity<BasePresenter<BaseView>>() {

    @BindView(R.id.ab)
    lateinit var ab: ActionBarEx

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, MessageActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId() = R.layout.activity_message

    override fun initPresenter(): BasePresenter<BaseView>? = null

    override fun initView() {
        ab.getView<ActionIconView>(R.id.action_bar_fixed_magic_indicator_iv_back).apply {
            visibility = View.VISIBLE
            setOnClickListener {
                finish()
            }
        }
        val adapter = FixedFragmentPagerAdapter(
            supportFragmentManager
        )
        adapter.setTitles("新消息", "历史消息")
        adapter.setFragmentList(
                MessageUnreadFragment.create(),
                MessageReadedFragment.create()
        )
        vp.adapter = adapter
        MagicIndicatorUtils.commonNavigator(ab.getView(R.id.mi), vp, adapter, null)
    }

    override fun loadData() {}
}