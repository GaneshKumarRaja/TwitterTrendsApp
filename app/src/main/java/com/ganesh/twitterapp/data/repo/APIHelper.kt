package com.ganesh.twitterapp.data.repo


import com.ganesh.twitterapp.data.model.AuthendicateModel
import com.ganesh.twitterapp.data.model.PlaceOuterResponseModel
import com.ganesh.twitterapp.data.model.TrendsOuterResponseModel
import io.reactivex.Single

interface APIHelper {

    fun getTrends(oauthToken: String, id: String): Single<List<TrendsOuterResponseModel>>

    fun getPcaeDetails(
        oauthToken: String,
        lat: String,
        lan: String
    ): Single<List<PlaceOuterResponseModel>>

    fun doAuthendicate(token:String ): Single<AuthendicateModel>

}