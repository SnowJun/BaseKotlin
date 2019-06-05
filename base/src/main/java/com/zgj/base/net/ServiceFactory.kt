package com.zgj.base.net

import com.zgj.base.service.IIndexService
import com.zgj.base.service.IUserService

/**
 *  @author Simple
 *  @date 2019/6/5
 *  @description ：
 **/
class ServiceFactory {

    companion object {
        var indexService: IIndexService? = null
            get() {
                synchronized(lockIndex) {
                    if (null == field) {
                        field = MainRetrofit().generateService(IIndexService::class.java)
                    }
                }
                return field
            }
        var userService: IUserService? = null
            get() {
                synchronized(lockUser) {
                    if (null == field) {
                        field = MainRetrofit().generateService(IUserService::class.java)
                    }
                }
                return field
            }

        var lockIndex = Any()
        var lockUser = Any()
    }

}