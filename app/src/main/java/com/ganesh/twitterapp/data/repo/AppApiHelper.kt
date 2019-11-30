package com.ganesh.twitterapp.data.repo


import com.ganesh.twitterapp.BuildConfig
import com.ganesh.twitterapp.data.model.AuthendicateModel
import com.ganesh.twitterapp.data.model.PlaceOuterResponseModel
import com.ganesh.twitterapp.data.model.TrendsOuterResponseModel
import com.ganesh.twitterapp.data.remote.APIInterface
import com.ganesh.twitterapp.data.repo.APIHelper
import com.ganesh.twitterapp.util.ConnectivityVerifier
import io.reactivex.Observable

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class AppApiHelper @Inject constructor(
    var api: APIInterface
) : APIHelper {


    override fun getLocationID(
        auth: String,
        lat: String,
        lon: String
    ): Single<List<PlaceOuterResponseModel>> {
        return api.getLocationID(auth, lat, lon)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun doAuthenticate(): Single<AuthendicateModel> {
        return api.doQuthendicate(BuildConfig.API_KEY)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override
    fun getTrends(auth: String, id: Int): Single<List<TrendsOuterResponseModel>> {
        return api.getTrends(auth, id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }


}


