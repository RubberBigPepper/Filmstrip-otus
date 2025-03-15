package com.akmanaev.filmstrip

import com.akmanaev.filmstrip.di.AppModule
import com.akmanaev.filmstrip.io.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.net.HttpURLConnection

class ApiServiceUnitTest {
    private val mockWebServer = MockWebServer()
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        val retrofit = AppModule.providesRetrofit(
            mockWebServer.url("/").toString(),
            AppModule.providesOkHttpClient(AppModule.providesLoggingInterceptor()),
            AppModule.providesXmlParser()
        )
        apiService = AppModule.providesApiService(retrofit)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun filmContentParsing(){
        val mockXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><title><![CDATA[Горшок каши]]></title>" +
                "<url><![CDATA[https://diafilmy.su/5-gorshok-kashi.html]]></url><imgs>" +
                "<img><![CDATA[https://diafilmy.su/uploads/posts/2020-07/1594731101_00a.jpg]]></img><mp3></mp3>" +
                "<img><![CDATA[https://diafilmy.su/uploads/posts/2020-07/1594731158_00b.jpg]]></img><mp3></mp3>" +
                "<img><![CDATA[https://diafilmy.su/uploads/posts/2020-07/1594731161_00c.jpg]]></img><mp3></mp3>" +
                "<img><![CDATA[https://diafilmy.su/uploads/posts/2020-07/1594731130_00d.jpg]]></img><mp3></mp3>" +
                "</imgs></data>"
        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(mockXml)
        mockWebServer.enqueue(response)

        runBlocking {
            val content = apiService.getFilmContent("5").body()
            assertNotNull(content)
            assertEquals( "Горшок каши", content?.title)
            assertEquals( "https://diafilmy.su/5-gorshok-kashi.html", content?.url)
            assertEquals( 4, content?.frames?.size)
            assertEquals( 4, content?.sounds?.size)

        }
    }

    @Test
    fun filmCollectParsing(){
        val mockXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data>" +
                "<post><newsdate>2020-07-14 15:52:46</newsdate><id>5</id>" +
                "<cat><![CDATA[Сказки]]></cat>" +
                "<title><![CDATA[Горшок каши]]></title>" +
                "<url>https://diafilmy.su/5-gorshok-kashi.html</url>" +
                "<img>https://diafilmy.su/uploads/posts/2020-07/1594731125_01.jpg</img>" +
                "<year>1968</year><studio>Диафильм</studio>" +
                "</post></data>"
        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(mockXml)
        mockWebServer.enqueue(response)

        runBlocking {
            val content = apiService.getFilmsForAndroidClient().body()
            assertNotNull(content)
            assertNotNull(content?.films)
            val film =  content?.films!![0]
            assertEquals( "Горшок каши", film.title)
            assertEquals( "Сказки", film.categories)
            assertEquals( "1968", film.year)
            assertEquals( "Диафильм", film.studio)
            assertEquals( "5", film.id)
            assertEquals( "https://diafilmy.su/5-gorshok-kashi.html", film.url)
            assertEquals( "https://diafilmy.su/uploads/posts/2020-07/1594731125_01.jpg", film.imageUrl)


        }
    }
}