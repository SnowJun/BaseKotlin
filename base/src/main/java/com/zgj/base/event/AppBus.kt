package com.zgj.base.event

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 *  @author Simple
 *  @date 2019/6/5
 *  @description ：
 **/
class AppBus private constructor() {

    companion object {

        const val DEFAULT_KEY = "key_default"

        val instance: AppBus by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            AppBus()
        }
    }


    fun acceptDefaultEvent(lifecycleOwner: LifecycleOwner,receiver: Receiver<*>){
        LiveEventBus.get().with(DEFAULT_KEY,AppEvent::class.java).observe(lifecycleOwner, object : Observer<AppEvent<*>> {
            override fun onChanged(t: AppEvent<*>?) {
                receiver.onReceive(t)
            }
        })
    }

    fun acceptDefaultEventSticky(lifecycleOwner: LifecycleOwner,receiver: Receiver<*>){
        LiveEventBus.get().with(DEFAULT_KEY,AppEvent::class.java).observeSticky(lifecycleOwner,object :Observer<AppEvent<*>>{
            override fun onChanged(t: AppEvent<*>?) {
                receiver.onReceive(t)
            }
        })
    }

    fun sendDefaultEvent(event: AppEvent<*>){
        LiveEventBus.get().with(DEFAULT_KEY).post(event)
    }

    interface Receiver<T> {
        /**
         * 接收到消息
         * 此处传AppEvent写T会报错
         * Out-projected type 'Receiver<*>' prohibits the use of 'public abstract fun onReceive(appEvent: AppEvent<T>): Unit defined in com.zgj.base.event.Receiver'
         * @param event
         */
        fun onReceive(event: AppEvent<*>?)
    }
}

