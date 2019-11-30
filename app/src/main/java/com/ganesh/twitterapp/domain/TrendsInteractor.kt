package com.ganesh.twitterapp.domain

import com.ganesh.twitterapp.data.model.TrendsOuterResponseModel
import com.ganesh.twitterapp.data.remote.KeyValueHandler
import com.ganesh.twitterapp.data.repo.APIHelper
import io.reactivex.Single
import javax.inject.Inject

class TrendsInteractor @Inject constructor(
    private val api: APIHelper,
    private val keyHandler: KeyValueHandler
) : TrendsUsecases {

    override fun getTrends(lat: String, lon: String): Single<List<TrendsOuterResponseModel>> {
        var key: String
               // 1. get key
        return getKey()
            .flatMap { authKey ->
                key = authKey
                // 2. get place info
                getPlaceDetails(authKey, lat, lon)
                    .flatMap { placeID ->
                        // 3. get trends
                        getTrends(key, placeID)
                    }
            }
    }

    /**
     * to get access token
     */
    private fun getKey(): Single<String> {

        // get the key from shaered preference
        var keyDetails = keyHandler.getToken()?.let {
            it
        }

        if (keyDetails.isNullOrEmpty()) {
            // get the key from server
            api.doAuthenticate().map {
                keyDetails = it!!.token_type + " " + it.access_token
            }
        }
        // return the key
        return Single.just(keyDetails)

    }

    /**
     * get the place details
     */
    private fun getPlaceDetails(key: String, lat: String, lon: String): Single<Int> {

        return api.getLocationID(key, lat, lon)
            .map {
                it[0].woeid
            }
    }


    /**
     * get the trends by place ID
     */
    private fun getTrends(key: String, id: Int): Single<List<TrendsOuterResponseModel>> {
        return api.getTrends(key, id)
    }
}