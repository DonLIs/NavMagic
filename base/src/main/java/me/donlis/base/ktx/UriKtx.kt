package me.donlis.base.ktx

import android.net.Uri


/**
 * Created by CDL on 2021/10/28 16:12
 *
 */

/**
 * Uri拼接参数
 */
fun Uri.append(key: String, value: String): Uri {
    return this.buildUpon().appendQueryParameter(key, value).build()
}

/**
 * Uri拼接参数
 * map: uri参数
 */
fun Uri.appendMap(map: MutableMap<String, String>): Uri {
    val buildUpon = this.buildUpon()
    for (entry in map.iterator()) {
        buildUpon.appendQueryParameter(entry.key, entry.value)
    }
    return this
}

/**
 * Uri 替换参数
 */
fun Uri.replace(key: String, newValue: String): Uri {
    val params: Set<String> = this.queryParameterNames
    val newUri: Uri.Builder = this.buildUpon().clearQuery()
    for (param in params) {
        newUri.appendQueryParameter(
            param,
            if (param == key) newValue else this.getQueryParameter(param)
        )
    }
    return newUri.build()
}

/**
 * Uri 替换参数
 */
fun Uri.replace(map: MutableMap<String, String>): Uri {
    val params: Set<String> = this.queryParameterNames
    val newUri: Uri.Builder = this.buildUpon().clearQuery()
    for (param in params) {
        newUri.appendQueryParameter(
            param,
            if (map.containsKey(param)) map[param] else this.getQueryParameter(param)
        )
    }
    return newUri.build()
}