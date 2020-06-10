package com.swpu.ylq.sunnyweather.logic.network

import com.swpu.ylq.sunnyweather.SunnyWeatherApplication
import com.swpu.ylq.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    //声明了一个@GET 注解 ：这样当调用search-Places()方法，Retrofit 就会自动发一条get请求，去访问@GET注解
    // 中的配置的地址
    @GET("v2/place?token = ${SunnyWeatherApplication.TOKEN}&lang = zh_CN")
    fun searchPlaces(@Query("query") query:String): Call<PlaceResponse>//sreachPlaces 方法的返回值是 Call<PlaceResponse>
    //为了能够使用这个接口 还需要创建一个Retrofit构建器
}