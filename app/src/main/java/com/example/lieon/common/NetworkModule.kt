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
    private const val BASE_URL = "http://10.0.2.2:8080/"
    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY // 디버그 모드에서는 요청 및 응답 바디를 로그로 출력
            } else {
                HttpLoggingInterceptor.Level.NONE // 릴리즈 모드에서는 로그를 출력하지 않음
            }
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