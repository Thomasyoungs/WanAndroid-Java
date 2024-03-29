package com.pigeon.cloud.module.book.contract

import kotlinx.coroutines.*
import com.pigeon.basic.core.base.BasePresenter
import com.pigeon.basic.core.base.BaseView
import per.goweii.rxhttp.request.exception.ExceptionHandle
import com.pigeon.cloud.db.executor.ReadRecordExecutor
import com.pigeon.cloud.db.model.ReadRecordModel
import com.pigeon.cloud.http.RequestListener
import com.pigeon.cloud.module.book.model.BookChapterBean
import com.pigeon.cloud.module.book.model.BookRequest
import com.pigeon.cloud.module.main.model.ArticleListBean
import kotlin.coroutines.resumeWithException

interface BookDetailsView : BaseView {
    fun getBookChaptersSuccess(list: List<BookChapterBean>, curPage: Int, isOver: Boolean)
    fun getBookChaptersFailed()
}

class BookDetailsPresenter : BasePresenter<BookDetailsView>() {
    private lateinit var mainScope: CoroutineScope
    private lateinit var readRecordExecutor: ReadRecordExecutor

    override fun attach(baseView: BookDetailsView) {
        super.attach(baseView)
        mainScope = MainScope()
        readRecordExecutor = ReadRecordExecutor()
    }

    override fun detach() {
        super.detach()
        mainScope.cancel()
        readRecordExecutor.destroy()
    }

    fun getChapters(id: Int, page: Int) {
        BookRequest.getBookChapterList(rxLife, id, page, object :
            RequestListener<ArticleListBean> {
            override fun onStart() {
            }

            override fun onSuccess(code: Int, data: ArticleListBean) {
                fillReadRecord(data)
            }

            override fun onFailed(code: Int, msg: String?) {
                if (isAttach) {
                    baseView.getBookChaptersFailed()
                }
            }

            override fun onError(handle: ExceptionHandle?) {
            }

            override fun onFinish() {
            }
        })
    }

    private fun fillReadRecord(data: ArticleListBean) = mainScope.launch {
        val list = withContext(Dispatchers.Default) {
            data.datas.map { BookChapterBean(it) }
        }
        val records = getReadRecords(list)
        list.forEach { bookChapterBean ->
            records.find { it.link == bookChapterBean.articleBean.link }?.let { readRecordModel ->
                bookChapterBean.time = readRecordModel.lastTime
                bookChapterBean.percent = readRecordModel.percentFloat
            }
        }
        if (isAttach) {
            baseView.getBookChaptersSuccess(list, data.curPage, data.isOver)
        }
    }

    private suspend fun getReadRecords(articles: List<BookChapterBean>) =
        suspendCancellableCoroutine<List<ReadRecordModel>> { continuation ->
            val links = articles.map { it.articleBean.link }
            readRecordExecutor.findByLinks(links, {
                continuation.resumeWith(Result.success(it))
            }, {
                continuation.resumeWithException(it)
            })
        }
}