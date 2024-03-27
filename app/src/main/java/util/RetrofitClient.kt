package util

import api.CbrApi
import models.ValCurs
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.cbr-xml-daily.ru/"


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun createApi(): CbrApi {
        return retrofit.create(CbrApi::class.java)
    }

}