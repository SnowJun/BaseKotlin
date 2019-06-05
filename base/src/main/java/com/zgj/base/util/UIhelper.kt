package com.example.testapp.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 *  @author Simple
 *  @date 2019/6/3
 *  @description ：
 **/
object UIhelper {

    fun toActivity(activity: Activity, clazz: Class<*>) {
        val intent = Intent(activity, clazz)
        activity.startActivity(intent)
    }

    fun toActivity(activity: Activity, clazz: Class<*>, bundle: Bundle){
        val intent = Intent(activity,clazz)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }





}