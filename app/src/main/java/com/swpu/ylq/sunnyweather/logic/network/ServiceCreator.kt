package com.swpu.ylq.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {//object生成单例类
    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()//使用Retrofit.Builder 构建出一个Retrofit对象
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())//解析数据时 所使用的转换库
        .build()
    //调用Retrofit对象的create（）方法创建动态代理对象
    fun <T> create(serviceClass: Class<T>):T = retrofit.create(serviceClass)
    //定义一个不带参数的create（）方法
    //inline && reified 修饰泛型   泛型实化的两大条件
    inline fun <reified  T> create():T = create(T::class.java)
    //调用时 更加简单 val appService = ServiceCreator.create<AppService>()
}