package com.swpu.ylq.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * 该文件中定义的类和属性 是根据数据接口返回的json格式进行定义的
 *
 */

data class PlaceResponse(val status:String,val places:List<Place>)
//使用了@SerializedName 进行注解 ：用于将JSON字段和kotlin字段 之间建立映射关系
data class Place (val name:String, val location: Location,@SerializedName("formatted_address")val address:String)

data class Location(val lng:String ,val lat :String)