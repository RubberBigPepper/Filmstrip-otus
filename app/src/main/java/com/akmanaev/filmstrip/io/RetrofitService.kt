package com.akmanaev.filmstrip.io

object RetrofitService {
    private const val BASE_URL = "https://diafilmy.su"
    val retrofitService: Api
        get() = RetrofitClient.getClient(BASE_URL).create(Api::class.java)
}