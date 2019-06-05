package com.zgj.base.service

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  @author Simple
 *  @date 2019/6/5
 *  @description ：
 **/
interface IIndexService {

    @GET("/wechatbusiness/bp/goods/list")
    fun getGoodsList(@Query("areaCode") areaCode:String):Observable<String>

}