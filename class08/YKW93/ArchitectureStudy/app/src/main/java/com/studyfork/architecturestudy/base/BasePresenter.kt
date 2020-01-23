package com.studyfork.architecturestudy.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter : BaseContract.Presenter {

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun subscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}