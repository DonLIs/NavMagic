package me.donlis.base.model

import java.io.Serializable

/**
 * Created by CDL on 2021/11/9 10:22
 *
 */
class Response<T>(
    var code: Int = 0,

    var data: T? = null,

    var message: String? = null
) : Serializable {



}