package com.zgj.base.module

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import com.zgj.base.net.ServiceFactory
import com.zgj.base.net.function.RetryFunction
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *  @author Simple
 *  @date 2019/6/5
 *  @description ：
 **/
class IndexModule private constructor(private val lifecycleProvider: LifecycleProvider<*>){

    companion object {
        val indexService = ServiceFactory.indexService
    }


    fun rx(observable: Observable<*> ,observer: Observer<Any>){
        var observable1 = observable.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retryWhen(RetryFunction())
        when(lifecycleProvider){
            is RxAppCompatActivity ->
                observable1.compose(lifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribe(observer)
            is RxFragment ->
                observable1.compose(lifecycleProvider.bindUntilEvent(FragmentEvent.DESTROY))
                    .subscribe(observer)
        }
    }


}