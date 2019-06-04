package com.zgj.base

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDialog
import com.coder.zzq.smartshow.dialog.LoadingDialog
import com.coder.zzq.smartshow.dialog.SmartDialog

/**
 *  @author Simple
 *  @date 2019/6/3
 *  @description ：
 **/
open class BasePresenter<GV : IBaseView>() {

    var mContext: Activity? = null
    var mFragment: Fragment? = null
    var mView: GV? = null
    var mLargeLoadingDialog: SmartDialog<AppCompatDialog>? = null

    constructor(context: Activity, view: GV) : this() {
        mContext = context
        mView = view
    }

    constructor(fragment: Fragment, view: GV) : this() {
        mFragment = fragment
        mContext = fragment.activity
        mView = view
    }

    fun onDetch(): Unit {
        mView = null
    }

    fun onLoading() {
        if (mLargeLoadingDialog == null) {
            mLargeLoadingDialog = LoadingDialog().large().withMsg(true).message("正在加载")
        }
        mLargeLoadingDialog?.showInActivity(mContext)
    }

    fun onLoadinged() {
        mLargeLoadingDialog?.dismiss()
        if (mLargeLoadingDialog != null) {
            mLargeLoadingDialog = null
        }
    }


}