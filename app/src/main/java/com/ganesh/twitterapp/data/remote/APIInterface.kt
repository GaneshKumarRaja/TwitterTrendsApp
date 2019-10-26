package com.ganesh.twitterapp.data.remote


import com.ganesh.twitterapp.data.model.AuthendicateModel
import com.ganesh.twitterapp.data.model.PlaceOuterResponseModel
import com.ganesh.twitterapp.data.model.TrendsOuterResponseModel
import io.reactivex.Single
import retrofit2.http.*

interface APIInterface {


    @GET("1.1/trends/place.json?")
    fun getTrends(
        @Header("Authorization") auth: String,
        @Query("id") id: String
    ): Single<List<TrendsOuterResponseModel>>


    @GET("1.1/trends/closest.json?")
    fun getLocationID(
        @Header("Authorization") auth: String,
        @Query("lat") lat: String,
        @Query("long") lon: String
    ): Single<List<PlaceOuterResponseModel>>


    @POST(value = "oauth2/token?grant_type=client_credentials")
    fun doQuthendicate(@Header("Authorization") auth: String): Single<AuthendicateModel>


}

