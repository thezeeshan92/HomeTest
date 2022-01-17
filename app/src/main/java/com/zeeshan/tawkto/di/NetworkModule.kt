package com.zeeshan.tawkto.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zeeshan.tawkto.remote.UserRemoteDataSource
import com.zeeshan.tawkto.remote.Webservice
import com.zeeshan.tawkto.repository.UserRepository
import com.zeeshan.tawkto.room.dao.UserDao
import com.zeeshan.tawkto.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .disableHtmlEscaping()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideDispatcher(): Dispatcher {

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        return dispatcher
    }

    @Provides
    @Singleton
    fun provideHttpClient(dispatcher: Dispatcher): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .dispatcher(dispatcher)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()

    @Provides
    fun providesWebservices(retrofit: Retrofit): Webservice =
        retrofit.create(Webservice::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: Webservice) =
        UserRemoteDataSource(characterService)


    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: UserRemoteDataSource,
        localDataSource: UserDao
    ) =
        UserRepository(remoteDataSource, localDataSource)
}