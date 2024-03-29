package com.pigeon.cloud.module.question.view

import com.pigeon.basic.core.base.BaseView
import com.pigeon.cloud.module.main.model.ArticleListBean

/**
 * @author yangzhikuan
 * @date 2020/3/25
 */
interface QuestionView : BaseView {
    fun getQuestionListSuccess(code: Int, data: ArticleListBean)
    fun getQuestionListFail(code: Int, msg: String)
}