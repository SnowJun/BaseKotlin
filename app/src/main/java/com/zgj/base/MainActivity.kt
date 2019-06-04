package com.zgj.base

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : BaseActivity<BasePresenter<IMainView>>(),IMainView {


    override fun initView() {

    }

    override fun initPresenter(intent: Intent?) {
        mPresenter = MainPresenter(this,this)
    }

    override fun initLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }


}
