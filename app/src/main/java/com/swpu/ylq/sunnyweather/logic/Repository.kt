package com.swpu.ylq.sunnyweather.logic


import android.content.Context
import androidx.lifecycle.liveData
import com.swpu.ylq.sunnyweather.logic.dao.PlaceDao
import com.swpu.ylq.sunnyweather.logic.model.Place
import com.swpu.ylq.sunnyweather.logic.model.RealtimeResponse
import com.swpu.ylq.sunnyweather.logic.model.Weather
import com.swpu.ylq.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {//coroutineScope 创建一个协程作用域
            //async函数 ：两个或多个同时使用时 可以保证同时得到他们的响应结果（这里的同时得到是保证多个async函数  都运行返回结果了）
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }

    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }


    fun savePlace(place:Place) =PlaceDao.savePlace(place)

    fun getSavePlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}