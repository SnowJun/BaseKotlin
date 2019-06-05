package com.example.testapp.util

import android.app.Activity

/**
 *  @author Simple
 *  @date 2019/6/3
 *  @description ：
 **/
class ActivityHelper private constructor() {

    companion object {
        val instance: ActivityHelper by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityHelper()
        }
    }

    var activities: MutableList<Activity> = ArrayList()

    fun add(activity: Activity) {
        activities.add(activity)
    }

    fun remove(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            activity.finish()
        }
    }


}