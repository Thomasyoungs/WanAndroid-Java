package com.pigeon.cloud.module.mine.adapter

import android.text.Html
import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.SwipeLayout.SwipeListener
import com.pigeon.basic.core.utils.StringUtils
import com.pigeon.cloud.R
import com.pigeon.cloud.module.mine.model.MessageBean
import java.util.*

/**
 * @author yangzhikuan
 * @date 2020/5/16
 */
class MessageReadedAdapter : BaseQuickAdapter<MessageBean, BaseViewHolder>(
        R.layout.rv_item_message_readed
) {

    private val mUnCloseList: ArrayList<SwipeLayout> = ArrayList()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    closeAll(null)
                }
            }
        })
    }

    fun closeAll(layout: SwipeLayout?) {
        for (swipeLayout in mUnCloseList) {
            if (layout === swipeLayout) {
                continue
            }
            if (swipeLayout.openStatus != SwipeLayout.Status.Open) {
                continue
            }
            swipeLayout.close()
        }
    }

    override fun convert(helper: BaseViewHolder, item: MessageBean) {
        val sl = helper.getView<SwipeLayout>(R.id.sl)
        sl.addSwipeListener(object : SwipeListener {
            override fun onStartOpen(layout: SwipeLayout) {
                closeAll(layout)
            }

            override fun onOpen(layout: SwipeLayout) {
                mUnCloseList.add(layout)
            }

            override fun onStartClose(layout: SwipeLayout) {}
            override fun onClose(layout: SwipeLayout) {
                mUnCloseList.remove(layout)
            }

            override fun onUpdate(layout: SwipeLayout, leftOffset: Int, topOffset: Int) {}
            override fun onHandRelease(layout: SwipeLayout, xvel: Float, yvel: Float) {}
        })
        helper.addOnClickListener(R.id.rl_message, R.id.tv_delete)

        if (TextUtils.isEmpty(item.tag)) {
            helper.setGone(R.id.tv_tag, false)
        } else {
            helper.setGone(R.id.tv_tag, true)
            helper.setText(R.id.tv_tag, item.tag)
        }
        helper.setText(R.id.tv_user, item.fromUser)
        helper.setText(R.id.tv_data, item.niceDate)
        helper.setText(R.id.tv_detail, item.title)
        var content: String = Html.fromHtml(item.message).toString()
        content = StringUtils.removeAllBank(content, 2)
        helper.setGone(R.id.tv_content, content.isNotEmpty())
        helper.setText(R.id.tv_content, content)
    }
}