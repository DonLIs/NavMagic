package me.donlis.base.model


/**
 * 请求响应实体-基类
 */
sealed class SuperResult<out T> {

    /**
     * 成功
     * @param data 成功的结果
     */
    data class Success<out T>(val data: T) : SuperResult<T>()

    /**
     * 失败
     * @param throwable
     */
    data class Failure(var throwable: Throwable? = null) : SuperResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Failure[exception=$throwable]"
        }
    }
}