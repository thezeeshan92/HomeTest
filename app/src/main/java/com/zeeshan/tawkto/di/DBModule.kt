package com.zeeshan.tawkto.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zeeshan.tawkto.remote.UserRemoteDataSource
import com.zeeshan.tawkto.remote.Webservice
import com.zeeshan.tawkto.repository.UserRepository
import com.zeeshan.tawkto.room.AppDatabase
import com.zeeshan.tawkto.room.dao.UserDao
import com.zeeshan.tawkto.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DBModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.userDao()

}