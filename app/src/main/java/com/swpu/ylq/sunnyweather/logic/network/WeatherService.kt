package com.swpu.ylq.sunnyweather.logic.network

import com.swpu.ylq.sunnyweather.SunnyWeatherApplication
import com.swpu.ylq.sunnyweather.logic.model.DailyResponse
import com.swpu.ylq.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 用于访问天气信息的API接口
 */
interface WeatherService {

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng:String,@Path("lat") lat:String):
            Call<RealtimeResponse>

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng:String,@Path("lat") lat:String):
            Call<DailyResponse>
}