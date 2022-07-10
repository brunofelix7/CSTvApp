package me.brunofelix.cstvapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.brunofelix.cstvapp.BuildConfig
import me.brunofelix.cstvapp.data.api.ApiService
import me.brunofelix.cstvapp.data.api.interceptor.ApiInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideClient() = OkHttpClient.Builder()
        .addInterceptor(ApiInterceptor())
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideApi(): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideClient())
        .build()
        .create(ApiService::class.java)

}