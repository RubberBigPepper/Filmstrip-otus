package com.akmanaev.filmstrip.io

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.Date
import java.util.concurrent.TimeUnit


object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            val httpLoggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build()

            val parser = TikXml.Builder()
                .exceptionOnUnreadXml(true)
                .addTypeConverter(Date::class.java, DateConverter())
                .addTypeConverter(String::class.java, HtmlEscapeStringConverter())
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()
        }
        return retrofit!!
    }
}