package com.akmanaev.filmstrip.di

import com.akmanaev.filmstrip.io.ApiService
import com.akmanaev.filmstrip.convertor.DateConverter
import com.akmanaev.filmstrip.io.Repository
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesBaseUrl() : String = "https://diafilmy.su"

    @Provides
    fun providesLoggingInterceptor() : HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient =
        OkHttpClient().newBuilder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()

    @Provides
    fun providesXmlParser() : TikXml =
        TikXml.Builder()
            .exceptionOnUnreadXml(true)
            .addTypeConverter(Date::class.java, DateConverter())
            .addTypeConverter(String::class.java, HtmlEscapeStringConverter())
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(baseUrl: String, okHttpClient: OkHttpClient, xmlParser: TikXml) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(TikXmlConverterFactory.create(xmlParser))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit : Retrofit) : ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesRepository(apiService: ApiService) : Repository = Repository(apiService)

}