package com.zgj.base.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.WindowManager
import butterknife.ButterKnife
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 *  @author Simple
 *  @date 2019/6/4
 *  @description ：
 **/
abstract class BaseActivity<P : BasePresenter<out IBaseView>> : RxAppCompatActivity() {

    private var isTrans: Boolean = false
    protected var mPresenter: P? = null

    protected var fragmentManager: FragmentManager? = null
    protected var fragmentTransaction: FragmentTransaction? = null
    protected var mFragments: Array<Fragment>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initStatusBar(false)
        }
        setContentView(initLayout())
        ButterKnife.bind(this)
        initPresenter(intent)
        checkPresenterIsNull()
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
            mPresenter?.onDetch()
    }


    private fun checkPresenterIsNull() {
        if (mPresenter == null) {
            throw IllegalArgumentException("please init presenter in initPresenter() method")
        }
    }

    private fun initStatusBar(isTrans: Boolean) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (isTrans) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            this.isTrans = isTrans
            window.statusBarColor = Color.TRANSPARENT
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            this.isTrans = isTrans
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.BLACK
        }
    }

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化View
     */
    abstract fun initView()

    /**
     * 初始化Presenter
     */
    abstract fun initPresenter(intent: Intent?)

    /**
     * 初始化布局
     */
    abstract fun initLayout(): Int


}