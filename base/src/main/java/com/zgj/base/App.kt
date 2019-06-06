package com.zgj.base

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Process
import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 *  @author Simple
 *  @date 2019/6/5
 *  @description ：
 **/
class App : Application() {

    companion object {
        const val TAG = "App"
        var instance: App? = null
        var context: Context? = null
        var handler: Handler? = null
        var mainThreadId: Int = -1
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = instance!!.applicationContext
        mainThreadId = Process.myPid()
        handler = Handler()
        initLogger()
    }

    private fun initLogger() {
        //object  匿名对象实现LogStrategy接口
        val logStrategy = object : LogStrategy {

            private val prefix = arrayOf(". ", " .")
            private var index = 0

            override fun log(priority: Int, tag: String?, message: String) {
                index = index xor 1
                Log.println(priority, prefix[index] + tag!!, message)
            }
        }
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .logStrategy(logStrategy)
            .showThreadInfo(true)
            .methodCount(1)
            .methodOffset(0)
            .tag(TAG)
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

}