package com.zgj.base.net

import android.content.Context
import com.orhanobut.logger.Logger
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *  @author Simple
 *  @date 2019/6/4
 *  @description ：
 **/
class MainRetrofit {

    companion object {
        var BASE_URL: String? = null
        private var retrofit: Retrofit? = null
        private var client: OkHttpClient? = null
        private const val DEFAULT_TIME_OUT: Long = 5
        var headers: Map<String, String>? = null
        var context: Context? = null

        private fun provideOkHttp() {
            var sslParams: HttpUtils.SslParams = HttpUtils.getSslSocketFactory()
            client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message: String ->
                    Logger.d(
                        message
                    )
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(getInterceptor())
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MINUTES)
                .cache(getCacheInstance())
                .build()
            provideRetrofit()
        }

        private fun getCacheInstance(): Cache? {
            var cacheFile = File(context?.cacheDir, "cache")
            var cache = Cache(cacheFile, 50 * 1024 * 1024)
            return cache
        }

        private fun getInterceptor(): Interceptor {
            return Interceptor { chain ->
                val builder = chain.request().newBuilder()
                headers?.forEach {
                    builder.addHeader(it.key, it.value)
                }
                var request = builder.build()
                chain.proceed(request)

            }
        }

        private fun provideRetrofit() {
            retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
        }

        fun initNet(context: Context, headers: Map<String, String>) {
            this.headers = headers
            this.context = context
            provideOkHttp()
        }
    }

    fun <T> generateService(clazz: Class<T>): T? {
        return retrofit?.create(clazz)
    }


}