package com.zgj.base.event

/**
 *  @author Simple
 *  @date 2019/6/5
 *  @description ：
 **/
class AppEvent<T> {

    var type: Int = -1
    var code: Int = -1
    var from: String? = null
    var event: T? = null

    constructor(type: Int, code: Int, event: T?) {
        this.type = type
        this.code = code
        this.event = event
    }

    constructor(type: Int, code: Int, from: String?, event: T?) {
        this.type = type
        this.code = code
        this.from = from
        this.event = event
    }

    override fun toString(): String {
        return "[QmsEvent: type=$type ,code=$code , from=$from event.toString()=${event.toString()}]"
    }

}