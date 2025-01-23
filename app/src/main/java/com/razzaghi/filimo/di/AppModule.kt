package com.razzaghi.filimo.di


import com.razzaghi.filimo.business.datasource.network.search.ApiService
import com.razzaghi.filimo.business.usecase.search.SearchUseCase
import com.razzaghi.filimo.presentation.ui.search.view_model.SearchViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


private const val CLIENT_TIME_OUT = 60L
private const val BASE_URL = "https://www.filimo.com/api/en/v1/"

val AppModule = module {

    single { createApiService(get()) }
    single { createMoshi() }
    single { createRetrofit(get(), get()) }
    single { createOkHttpClient() }


    single { SearchUseCase(get()) }

    viewModel { SearchViewModel(get()) }


}


fun createApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createMoshi(): Moshi {
    return Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}