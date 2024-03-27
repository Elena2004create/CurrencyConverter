package api

import models.ValCurs
import retrofit2.Call
import retrofit2.http.GET

interface CbrApi {
    @GET("https://www.cbr-xml-daily.ru/daily_json.js")
    fun getValCurs(): Call<ValCurs>
}