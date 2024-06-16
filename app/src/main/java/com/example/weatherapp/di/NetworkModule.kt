package com.example.weatherapp.di

import com.example.weatherapp.data.RetrofitData
import com.example.weatherapp.data.WeatherApi
import com.example.weatherapp.data.WeatherRepositoryImpl
import com.example.weatherapp.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun provideRepo(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    companion object {

        @Provides
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(RetrofitData.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        fun provideApiService(retrofit: Retrofit): WeatherApi =
            retrofit.create(WeatherApi::class.java)

        @Provides
        fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
    }
}