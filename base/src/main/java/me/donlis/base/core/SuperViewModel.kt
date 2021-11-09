package me.donlis.base.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import me.donlis.base.interfaces.SuperCallback
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.donlis.base.interfaces.ILoading
import me.donlis.base.model.ApiException
import me.donlis.base.model.ComponentAction
import me.donlis.base.model.Response
import kotlin.coroutines.CoroutineContext

/**
 * Created by CDL on 2021/10/29 14:23
 * 封装 ViewModel，添加协程调用方法和回调动作处理
 */
open class SuperViewModel : ViewModel(), ILoading {

    //-------------------Action-----------------------

    /**
     * 响应一个动作，与activity、fragment交互
     */
    val actionLiveData: MutableLiveData<ComponentAction> by lazy {
        MutableLiveData()
    }

    /**
     * 发送一个动作给actionLiveData的订阅者，通知去处理具体操作
     */
    private fun sendAction(action: ComponentAction) {
        actionLiveData.value = action
    }

    override fun showLoading() {
        //发送一个展示loading的动作
        sendAction(ComponentAction.ShowLoading())
    }

    override fun dismissLoading() {
        //发送一个关闭loading的动作
        sendAction(ComponentAction.DismissLoading())
    }

    //-------------------Action-----------------------

    //-------------------协程-----------------------

    /**
     * 启动一个协程任务 (Flow)
     * @param callBack callBack
     * @param context 默认IO
     * @param start 默认启动方式
     * @param block 执行的任务
     */
    fun <T> launch(
        callBack: SuperCallback<T>? = null,
        loading: ILoading? = null,
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend () -> Flow<T>
    ): Job {

        return viewModelScope.launch(
            context = context,
            start = start,
            block = {
                handleFlow(block.invoke(), callBack, loading)
            })
    }

    /**
     * 启动一个协程任务 (Flow)
     * @param callBack callBack
     * @param context 默认IO
     * @param start 默认启动方式
     * @param block 执行的任务
     */
    fun <T> launchApi(
        callBack: SuperCallback<T>? = null,
        loading: ILoading? = null,
        context: CoroutineContext = Dispatchers.IO,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: () -> Response<T>
    ): Job {

        return viewModelScope.launch(
            context = context,
            start = start,
            block = {
                flowApi(context) {
                    block.invoke()
                }.also {
                    handleFlow(it, callBack, loading)
                }
            })
    }

    /**
     * 响应请求，输出流（Flow）
     */
    private suspend fun <T> flowApi(
        context: CoroutineContext = Dispatchers.IO,
        block: () -> Response<T>
    ): Flow<T> {
        return flow {
            // 拿取响应体
            val response = block.invoke()
            // 响应出去，需要捕获异常
            emit(getResponseBody(response))
        }.catch {
            throw it  //可添加异常处理
        }.flowOn(context)
    }

    /**
     * 处理流（Flow）结果
     * 耗时操作
     */
    private suspend fun <T> handleFlow(
        flow: Flow<T>,
        callBack: SuperCallback<T>? = null,
        loading: ILoading? = null
    ) {
        return flow.onStart {
            loading?.showLoading()
        }.onCompletion {
            loading?.dismissLoading()
        }.catch {
            val exception = analysisException(it)
            callBack?.onFailure(exception)
        }.collectLatest { result ->
            callBack?.onSuccess(result)
        }
    }

    //-------------------协程-----------------------

    //-------------------响应&异常-----------------------

    /**
     * 获取响应体
     */
    private fun <T> getResponseBody(response: Response<T>): T {
        //如果需要判断响应code，在此处添加code的判断
        if (isSuccess(response.code)) {
            return response.data ?: let {
                throw Exception("response data may be null") //可添加异常处理
            }
        } else {
            throw ApiException(response)
        }
    }

    /**
     * 对异常 进行分析处理
     */
    protected open suspend fun analysisException(
        throwable: Throwable
    ): Throwable {
        //
        return when(throwable) {
            is ApiException -> {
                //接口不成功异常
                throwable
            }
            else -> throwable
        }
    }

    /**
     * 是否是请求成功，判断响应码code，默认200
     */
    protected open fun isSuccess(code: Int): Boolean {
        return code == 200
    }

    //-------------------响应&异常-----------------------

}