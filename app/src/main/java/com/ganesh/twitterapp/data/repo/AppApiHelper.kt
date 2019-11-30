package com.ganesh.twitterapp.data.repo


import com.ganesh.twitterapp.BuildConfig
import com.ganesh.twitterapp.data.model.AuthendicateModel
import com.ganesh.twitterapp.data.model.PlaceOuterResponseModel
import com.ganesh.twitterapp.data.model.TrendsOuterResponseModel
import com.ganesh.twitterapp.data.remote.APIInterface
import com.ganesh.twitterapp.data.repo.APIHelper
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

    var newToken = ""

    override
    fun getTrends(auth: String, id: Int): Single<List<TrendsOuterResponseModel>> {

        return api.getTrends(auth, id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())

//            .flatMap {
//                newToken = it!!.token_type + " " + it.access_token + "00"
//                getPlace(newToken, lat, lon)
//            }
//
//            .flatMap {
//                getTrend(it)
//            }

    }


//    fun getPlace(
//        oauthToken: String,
//        lat: String,
//        lan: String
//    ): Observable<List<PlaceOuterResponseModel>> {
//
//        return api.getLocationID(oauthToken, lat, lan)
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .map {
//                it
//            }
//    }
//
//
//    fun getTrend(place: List<PlaceOuterResponseModel>): Observable<List<TrendsOuterResponseModel>> {
//        return api.getTrends(
//            newToken,
//            "" + place[0].woeid
//        ).subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .map {
//                it
//            }
//    }


}


