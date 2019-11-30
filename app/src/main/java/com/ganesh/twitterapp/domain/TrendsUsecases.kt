package com.ganesh.twitterapp.domain

import com.ganesh.twitterapp.data.model.TrendsOuterResponseModel
import io.reactivex.Single


interface TrendsUsecases {
    fun getTrends(lat: String, lon: String): Single<List<TrendsOuterResponseModel>>
}