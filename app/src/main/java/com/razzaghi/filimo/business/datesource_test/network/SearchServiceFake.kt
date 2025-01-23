package com.razzaghi.filimo.business.datesource_test.network

import com.razzaghi.filimo.business.datasource.network.search.ApiService
import com.razzaghi.filimo.business.datesource_test.network.data.SearchDataEmpty
import com.razzaghi.filimo.business.datesource_test.network.data.SearchDataValid
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SearchServiceFake {

    fun build(type: SearchServiceResponseType): ApiService {

        val BASE_URL = "https://www.filimo.com/api/en/v1/"
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()


        return Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(MockInterceptor(type)).build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build().create(ApiService::class.java)
    }

    private inner class MockInterceptor(val type: SearchServiceResponseType) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val responseString: String
            val responseCode: Int

            when (type) {
                is SearchServiceResponseType.EmptyList -> {
                    responseString = SearchDataEmpty.data
                    responseCode = 200
                }

                is SearchServiceResponseType.Error406 -> {
                    responseString = SearchDataEmpty.data
                    responseCode = 406
                }

                is SearchServiceResponseType.GoodData -> {
                    responseString = SearchDataValid.data
                    responseCode = 200
                }
            }
            return chain.proceed(chain.request())
                .newBuilder()
                .code(responseCode)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        }
    }
}
