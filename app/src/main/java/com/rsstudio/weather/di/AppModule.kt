package com.rsstudio.weather.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.rsstudio.weather.app.App
import com.rsstudio.weather.data.local.preference.PreferenceProvider
import com.rsstudio.weather.data.network.apis.WeatherApiInterface
import com.rsstudio.weather.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun applicationContext( @ApplicationContext applicationContext: Context) : App {
        return applicationContext as App
    }

    @Singleton
    @Provides
    fun preferenceProvider(@ApplicationContext applicationContext: Context): PreferenceProvider {
        return PreferenceProvider(applicationContext)
    }

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApiInterface =
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WeatherApiInterface::class.java)

}