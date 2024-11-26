package com.example.lieon.common

import com.arthenica.mobileffmpeg.BuildConfig
import com.example.lieon.record.data.repository.LieDetectionRepository
import com.example.lieon.record.data.source.LieDetectionSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            // 항상 BODY 레벨로 로그를 출력하도록 설정
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // 로깅 인터셉터 추가
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLieDetectionSource(retrofit: Retrofit): LieDetectionSource {
        return retrofit.create(LieDetectionSource::class.java)
    }

    @Singleton
    @Provides
    fun provideLieDetectionRepository(lieDetectionSource: LieDetectionSource) = LieDetectionRepository(lieDetectionSource)


}