package com.swpu.ylq.sunnyweather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.swpu.ylq.sunnyweather.SunnyWeatherApplication
import com.swpu.ylq.sunnyweather.logic.model.Place

object PlaceDao {
    fun savePlace(place: Place){
        sharedPreferences().edit{
            putString("place",Gson().toJson(place))//通过GSON将place对象转成一个JSON字符串，然后用字符串存储方式来保存数据
        }
    }

    fun getSavedPlace():Place{
        val placeJson = sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)//先将JSON字符串读取出来，然后通过GSON将JSON字符串解析程Place对象并返回
    }

    //判断数据是否已被存储
    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather",Context.MODE_PRIVATE)

}