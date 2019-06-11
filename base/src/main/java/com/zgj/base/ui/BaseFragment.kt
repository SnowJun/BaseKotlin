package com.zgj.base.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 *  @author Simple
 *  @date 2019/6/3
 *  @description ：
 **/
abstract class BaseFragment<P : BasePresenter<out IBaseView>> : RxFragment() {

    protected var mPresenter: P? = null
    protected var mActivity: Activity? = null
    private var isFirstLoad: Boolean = false

    private var unbinder: Unbinder? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity?
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isFirstLoad && isVisibleToUser) {
            isFirstLoad = false
            pullData()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(initLayout(), container, false)
        unbinder = ButterKnife.bind(this, view)
        initPresenter()
        checkPresenterIsNull()
        initView(view, savedInstanceState)
        isFirstLoad = true
        if (userVisibleHint) {
            pullData()
            isFirstLoad = false
        }
        return view
    }


    private fun checkPresenterIsNull() {
        if (mPresenter == null) {
            throw IllegalArgumentException("please init presenter in initPresenter method")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        isFirstLoad = false
        unbinder?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDetch()
    }

    /**
     * 初始化布局
     * @return 布局资源id
     */
    abstract fun initLayout(): Int

    /**
     * 初始化View相关
     * @param view 根布局
     * @param savedInstanceState 数据包
     */
    abstract fun initView(view: View?, savedInstanceState: Bundle?)

    /**
     * 初始化Presenter
     */
    abstract fun initPresenter()

    /**
     * 拉取数据
     */
    abstract fun pullData()


}