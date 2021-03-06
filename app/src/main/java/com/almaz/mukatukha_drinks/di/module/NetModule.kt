package com.almaz.mukatukha_drinks.di.module

import com.almaz.mukatukha_drinks.data.MukatukhaAPI
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetModule {
    @Provides
    fun provideBaseUrl() = "http://192.168.1.2:8080/"

    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().setLenient().create())

    @Provides
    fun provideCallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        converterFactory: GsonConverterFactory,
        callAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .build()

    @Provides
    fun provideMukatukhaApi(retrofit: Retrofit): MukatukhaAPI =
        retrofit.create(MukatukhaAPI::class.java)
}