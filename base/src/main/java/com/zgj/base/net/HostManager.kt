package com.zgj.base.net

/**
 *  @author Simple
 *  @date 2019/6/5
 *  @description ：
 * * retrofit 多host管理
 * 根据header来确定 header的键为 hoster
 *
 * 使用时候在service添加header如下：
 *     @see HostManager.instance.HOST_HEADER_KEY
 *     1) HostManager.instance.addHost("test","https://app-test.qms888.com");
 *     2) @Headers({"hoster:test"})
 **/
class HostManager private constructor(){


    private var hosters:MutableMap<String,String>  = HashMap()

    companion object {

        const val HOST_HEADER_KEY = "hoster"

        val instance:HostManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            HostManager()
        }
    }

    fun getHost(hostKey:String): String? {
        return hosters[hostKey]
    }

    fun addHost(hostKey: String,hostValue: String){
        hosters[hostKey] = hostValue
    }

    fun removeHost(hostKey: String){
        hosters.remove(hostKey)
    }

    fun init(hosters:MutableMap<String,String>){
        this.hosters = hosters
    }

}