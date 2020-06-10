package com.swpu.ylq.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * 定义一个统一的网络数据源访问入口
 * 对所有的网络请求的API进行封装
 */
object SunnyWeatherNetwork {
    // 创建一个PlaceService接口的动态代理对象
    private val placeService  = ServiceCreator.create(PlaceService::class.java)
    // 定义函数  用于调用在PlaceService接口中定义的searchPlaces（）方法 发起搜索城市数据的请求
    suspend fun searchPlaces(query:String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T>{
                override fun onResponse(call: Call<T>,response: Response<T>){
                    val body = response.body()
                    if(body !=null) continuation.resume(body)

                    else continuation.resumeWithException(java.lang.RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}