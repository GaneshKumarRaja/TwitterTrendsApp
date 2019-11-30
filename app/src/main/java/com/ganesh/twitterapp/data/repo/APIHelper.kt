package com.ganesh.twitterapp.data.repo


import com.ganesh.twitterapp.data.model.AuthendicateModel
import com.ganesh.twitterapp.data.model.PlaceOuterResponseModel
import com.ganesh.twitterapp.data.model.TrendsOuterResponseModel
import io.reactivex.Observable
import io.reactivex.Single


interface APIHelper {

    fun doAuthenticate(): Single<AuthendicateModel>

    fun getTrends(auth: String, id: Int): Single<List<TrendsOuterResponseModel>>

    fun getLocationID(
        auth: String,
        lat: String,
        lon: String
    ): Single<List<PlaceOuterResponseModel>>

}