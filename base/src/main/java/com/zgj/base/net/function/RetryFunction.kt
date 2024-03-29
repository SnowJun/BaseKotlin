package com.zgj.base.net.function

import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 *  @author Simple
 *  @date 2019/6/5
 *  @description ：
 **/
class RetryFunction : Function<Observable<Throwable>, ObservableSource<*>> {

    private val maxConnectCount = 3
    private var currentRetryCount = 0
    private var waitRetryTime = 0

    var url: String = "未知URL"


    override fun apply(t: Observable<Throwable>): ObservableSource<*> {
        // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
        return t.flatMap {
            Logger.e("发生异常 = $it")
            when (it) {
                is HttpException -> {
                    val code = it.response().code()
                    Logger.d("Http:$code")
                    //todo 此处拦截401 异常
                    return@flatMap Observable.error<Any>(it)
                }
                /**
                 * 需求1：根据异常类型选择是否重试
                 * 即，当发生的异常 = 网络异常 = IO异常 才选择重试
                 */
                is IOException -> {
                    Logger.e("$url,属于IO异常，需重试")
                    /**
                     * 需求2：限制重试次数
                     * 即，当已重试次数 < 设置的重试次数，才选择重试
                     */
                    if (currentRetryCount < maxConnectCount) {
                        currentRetryCount++
                        Logger.d(url + "重试次数 = " + currentRetryCount)
                        /**
                         * 需求2：实现重试
                         * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                         *
                         * 需求3：延迟1段时间再重试
                         * 采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
                         *
                         * 需求4：遇到的异常越多，时间越长
                         * 在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
                         */
                        // 设置等待时间
                        waitRetryTime = 1000 + currentRetryCount * 1000
                        Logger.d("等待时间 =$waitRetryTime")
                        return@flatMap Observable.just(1).delay(waitRetryTime.toLong(), TimeUnit.MILLISECONDS)
                    } else {
                        // 若重试次数已 > 设置重试次数，则不重试
                        // 通过发送error来停止重试（可在观察者的onError（）中获取信息）
                        return@flatMap Observable.error<Any>(Throwable("重试次数已超过设置次数 = $currentRetryCount，即 不再重试"))
                    }
                }
                else -> return@flatMap Observable.error<Any>(Throwable("发生了非网络异常（非I/O异常）"))
            }


        }
    }
}