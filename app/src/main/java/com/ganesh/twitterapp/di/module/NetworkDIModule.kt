package com.ganesh.twitterapp.di.module


import com.ganesh.twitterapp.BuildConfig
import com.ganesh.twitterapp.data.remote.APIInterface
import com.ganesh.twitterapp.data.repo.APIHelper
import com.ganesh.twitterapp.data.repo.AppApiHelper
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkDIModule {

    lateinit var inter: APIInterface
    lateinit var clit: OkHttpClient

//    @Provides
//    @Singleton
//    fun createHttpClient(): OkHttpClient {
//
////        var consumer = OkHttpOAuthConsumer(
////            "d6o6YvRTLHFbB7PTKznxtl3C7",
////            "WzFsezX8ghU1F0L2P6bO6edSlADIP6NOIW7o43cP0m9hxuYlbc"
////        )
////        consumer.setTokenWithSecret(
////            "828155430958358529-MiQ4cFfquuFdCrZm5oAPqHJlHyWgJpX",
////            "61BFg6vQXp4JdPfAUoEZM0JBv3fjradfk6OYPh9I3KByd"
////        )
//        //var consumer =  OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
//        // consumer.setTokenWithSecret("828155430958358529-MiQ4cFfquuFdCrZm5oAPqHJlHyWgJpX", "61BFg6vQXp4JdPfAUoEZM0JBv3fjradfk6OYPh9I3KByd");
//
////
//
////        return OkHttpClient.Builder()
////            .addInterceptor(SigningInterceptor(consumer))
////            .build()
//
//        // val logging = HttpLoggingInterceptor()
//        //logging.level = HttpLoggingInterceptor.Level.BODY
//        //val client = OkHttpClient.Builder().addInterceptor(logging)
//        // client.readTimeout(5 * 60, TimeUnit.SECONDS)
//        return OkHttpClient().newBuilder()
//
//            .addInterceptor {
//
//                val original = it.request()
//                val requestBuilder = original.newBuilder()
//                requestBuilder.header("Content-Type", "application/x-www-form-urlencoded")
//                    .addHeader(
//                        "Authorization",
//                        "OAuth oauth_consumer_key=d6o6YvRTLHFbB7PTKznxtl3C7,oauth_signature_method=HMAC-SHA1,oauth_timestamp=1571774183,oauth_nonce=HSfahl,oauth_version=1.0,oauth_signature=egcLKi9D3wHBjdmgGNnUbTpSc5A%3D"
//                    )
//
////            requestBuilder.header("consumer_key", "d6o6YvRTLHFbB7PTKznxtl3C7")
////            requestBuilder.header(
////                "consumer_secret",
////                "WzFsezX8ghU1F0L2P6bO6edSlADIP6NOIW7o43cP0m9hxuYlbc"
////            )
////            requestBuilder.header(
////                "AccessToken",
////                "828155430958358529-MiQ4cFfquuFdCrZm5oAPqHJlHyWgJpX"
////            )
////            requestBuilder.header("TokenSecret", "61BFg6vQXp4JdPfAUoEZM0JBv3fjradfk6OYPh9I3KByd")
//                // val request = requestBuilder.method(original.method(), original.body()).build()
//                return@addInterceptor it.proceed(request)
//            }.build()
//
//
//        //.addInterceptor(SigningInterceptor(consumer)).build()
//
//    }

    @Provides
    @Singleton
    fun createHttpClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging)
        client.readTimeout(5 * 60, TimeUnit.SECONDS)

        clit = client.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.header("Content-Type", "application/json")
//                .addHeader(
//                    "Authorization",
//                    "   String authorization =" +
//                            "        OAuth   OAUTH_CONSUMER_KEY  =   consumerKeyValue  ,  OAUTH_NONCE =  " +
//                            "  oauthNonce  ,  OAUTH_SIGNATURE  = + UrlEscapeUtils.escape(signature) " +
//                            "           ,  OAUTH_SIGNATURE_METHOD  =  OAUTH_SIGNATURE_METHOD_VALUE , " +
//                            "             OAUTH_TIMESTAMP + =  oauthTimestamp ,  OAUTH_ACCESS_TOKEN  =" +
//                            "             accessTokenValue ,  OAUTH_VERSION =  OAUTH_VERSION_VALUE ";
//                    "OAuth oauth_consumer_key=d6o6YvRTLHFbB7PTKznxtl3C7,oauth_token=828155430958358529-jdFLFI73mmYKf6wTyBn0UK52uVYpeZ7,oauth_signature_method=HMAC-SHA1,oauth_timestamp=1571869398,oauth_nonce=clu2of,oauth_version=1.0,oauth_signature=h2G2nklNVJXHKpRu0%2FSvWLazj%2BQ%3D"

//"OAuth oauth_consumer_key=d6o6YvRTLHFbB7PTKznxtl3C7,oauth_token=828155430958358529-jdFLFI73mmYKf6wTyBn0UK52uVYpeZ7,oauth_signature_method=HMAC-SHA1,oauth_timestamp=1571869398,oauth_nonce=clu2of,oauth_version=1.0,oauth_signature=h2G2nklNVJXHKpRu0%2FSvWLazj%2BQ%3D"
//OAuth oauth_consumer_key="d6o6YvRTLHFbB7PTKznxtl3C7",oauth_signature_method="HMAC-SHA1",oauth_timestamp="1571866285",oauth_nonce="hWNFyv",oauth_version="1.0",oauth_signature="6L0TLIgwJRgbvSuS07BvjJ1Bapg%3D"
            // )
            val request = requestBuilder.method(original.method(), original.body()).build()
            return@addInterceptor it.proceed(request)
        }.build()

        return clit

    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): APIInterface {

        inter = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            //.addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build().create(APIInterface::class.java)

        return inter

    }



    @Singleton
    @Provides
    open fun getHelper(): APIHelper {
        createHttpClient()
        provideRetrofit(clit)
        return AppApiHelper(inter)
    }


}