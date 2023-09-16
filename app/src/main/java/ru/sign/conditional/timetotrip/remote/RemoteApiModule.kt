package ru.sign.conditional.timetotrip.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.sign.conditional.timetotrip.BuildConfig
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteApiModule {
    companion object {
        private const val WORK_URL =
            "${BuildConfig.BASE_URL}/stream/api/avia-service/v1/suggests/"
    }

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(WORK_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Singleton
    @Provides
    fun provideFlightRemoteApi(
        retrofit: Retrofit
    ) : FlightRemoteApi =
        retrofit.create()
}