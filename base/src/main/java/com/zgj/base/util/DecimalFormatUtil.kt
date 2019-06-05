package com.example.testapp.util

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 *  @author Simple
 *  @date 2019/6/3
 *  @description ：
 **/
class DecimalFormatUtil private constructor() {

    companion object {
        val instance: DecimalFormatUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DecimalFormatUtil()
        }
    }

    /**
     * 格式化 舍去法
     * @param format 格式化的格式  如"0.00",保留两位小数
     * @param value 格式化的值
     */
    fun format(format: String, value: Double): String {
        var formater = DecimalFormat(format)
        formater.roundingMode = RoundingMode.DOWN
        return formater.format(value)
    }

    /**
     * 格式化，四舍五入
     * @param format 格式化的格式  如"0.00",保留两位小数
     * @param value 格式化的值
     */
    fun formatHalf(format: String, value: Double): String {
        var formater = DecimalFormat(format)
        formater.roundingMode = RoundingMode.HALF_UP
        return formater.format(value)
    }

}