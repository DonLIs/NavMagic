package me.donlis.base.model


/**
 * Created by CDL on 2021/11/8 14:17
 * 自定义 - 响应体 - 错误码 - Exception
 */
class ApiException constructor(
    private val response: Response<*>,
    var code: Int? = response.code ?: 0
): Exception(response.message ?: "") {



}