package com.zgj.base.service

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  @author Simple
 *  @date 2019/6/5
 *  @description ：
 **/
interface IUserService {

    @GET("/api-customer/user/sendAuthCode")
    fun genCode(@Query("phoneNum") phoneNum: String): Observable<String>

}