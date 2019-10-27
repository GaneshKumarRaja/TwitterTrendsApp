package com.ganesh.twitterapp.data.repo


import com.ganesh.twitterapp.data.model.AuthendicateModel
import com.ganesh.twitterapp.data.model.PlaceOuterResponseModel
import com.ganesh.twitterapp.data.model.TrendsOuterResponseModel
import com.ganesh.twitterapp.data.remote.APIInterface
import com.ganesh.twitterapp.data.repo.APIHelper

import io.reactivex.Single
import javax.inject.Inject

open class AppApiHelper @Inject constructor(
    var api: APIInterface
) : APIHelper {


    override fun getTrends(oauthToken: String, id: String): Single<List<TrendsOuterResponseModel>> {

        return api.getTrends(
            oauthToken,
            id
        )

    }

    override
    fun getPcaeDetails(
        oauthToken: String,
        lat: String,
        lan: String
    ): Single<List<PlaceOuterResponseModel>> {

        return api.getLocationID(oauthToken, lat, lan)
    }


    override fun doAuthendicate(token: String): Single<AuthendicateModel> {
        return api.doQuthendicate(token)
    }


}


