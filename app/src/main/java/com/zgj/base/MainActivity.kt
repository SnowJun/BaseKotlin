package com.zgj.base

import android.content.Intent
import android.view.View
import android.widget.Toast
import butterknife.OnClick
import com.orhanobut.logger.Logger
import com.zgj.base.event.AppBus
import com.zgj.base.event.AppEvent
import com.zgj.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), IMainView {



    override fun initView() {
    }

    override fun initPresenter(intent: Intent?) {
        mPresenter = MainPresenter(this, this)
    }

    override fun initLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        initBus()

    }

    private fun initBus() {
        AppBus.instance.acceptDefaultEvent(this, object : AppBus.Receiver<String> {
            override fun onReceive(event: AppEvent<*>?) {
                Toast.makeText(this@MainActivity, event.toString(), Toast.LENGTH_LONG).show()
                Logger.d("消息内容：${event?.event}")
                Logger.d("消息内容：${event.toString()}")
            }
        })

    }

    @OnClick(R.id.hello)
    internal fun onClick(view:View){
        when(view.id){
            R.id.hello->{
                Logger.d("点击Hello")
//                AppBus.instance.sendDefaultEvent(AppEvent(2,2,"2from","23event"))
            }
        }
    }


}
