package me.brunofelix.cstvapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import me.brunofelix.cstvapp.BuildConfig
import me.brunofelix.cstvapp.data.api.ApiService
import me.brunofelix.cstvapp.data.api.interceptor.ApiInterceptor
import me.brunofelix.cstvapp.data.api.repository.MatchRepository
import me.brunofelix.cstvapp.data.api.repository.MatchRepositoryImpl
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
    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiInterceptor())
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideApi(): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideClient())
        .build()
        .create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideIODispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context.applicationContext

    @Singleton
    @Provides
    fun provideMatchRepository(
        api: ApiService, @ApplicationContext context: Context
    ): MatchRepository = MatchRepositoryImpl(api, context)

}
