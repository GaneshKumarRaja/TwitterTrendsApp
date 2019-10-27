package com.ganesh.twitterapp.view_model


import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.ganesh.twitterapp.R
import com.ganesh.twitterapp.data.model.AuthendicateModel
import com.ganesh.twitterapp.data.model.PlaceOuterResponseModel
import com.ganesh.twitterapp.data.model.Trends
import com.ganesh.twitterapp.data.model.TrendsOuterResponseModel
import com.ganesh.twitterapp.util.ConnectivityVerifier
import com.ganesh.twitterapp.data.repo.APIHelper
import com.ganesh.twitterapp.util.SheredPref

import com.google.android.gms.location.LocationRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import java.net.UnknownHostException
import javax.inject.Inject

open class TrendsListViewModel @Inject constructor() :
    BaseViewModel() {


    @Inject
    lateinit var context: Context

    @Inject
    lateinit var locationProvider: ReactiveLocationProvider

    @Inject
    lateinit var locationRequest: LocationRequest

    @Inject
    lateinit var connectivityVerifier: ConnectivityVerifier

    @Inject
    lateinit var apiRepo: APIHelper

    @Inject
    lateinit var sheredPref: SheredPref

    var authenticationData: MutableLiveData<AuthendicateModel> = MutableLiveData()

    var locationLiveData: MutableLiveData<Location> = MutableLiveData()

    var trendsLiveData: MutableLiveData<List<Trends>> = MutableLiveData()

    var placeLiveData: MutableLiveData<PlaceOuterResponseModel> = MutableLiveData()

    var tokenStatus: MutableLiveData<Boolean> = MutableLiveData()

    fun doAuthendicate(twitterToken: String): Boolean {

        canShowLoading.value = true

        clearAll()

        disposable.add(
            apiRepo.doAuthendicate(twitterToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<AuthendicateModel>() {

                    override fun onSuccess(value: AuthendicateModel?) {

                        authenticationData.value = value
                        tokenStatus.value = true
                        canShowLoading.value = false
                    }

                    override fun onError(e: Throwable?) {

                        tokenStatus.value = false
                        handleEror(e)
                    }
                })
        )

        return true
    }


    fun getPlaceDetails(oAuthtoken: String, lat: String, lon: String) {

        canShowLoading.value = true
        clearAll()


        disposable.add(
            apiRepo.getPcaeDetails(oAuthtoken, lat, lon)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PlaceOuterResponseModel>>() {

                    override fun onSuccess(value: List<PlaceOuterResponseModel>?) {

                        if (value!!.isNotEmpty()) {
                            placeLiveData.value = value[0]
                            canShowLoading.value = false
                        }

                    }

                    override fun onError(e: Throwable?) {
                        handleEror(e)
                    }
                })
        )
    }


    fun getTrendsData(token: String, placeID: String) {

        canShowLoading.value = true
        clearAll()

        disposable.add(
            apiRepo.getTrends(token, placeID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<TrendsOuterResponseModel>>() {

                    override fun onSuccess(value: List<TrendsOuterResponseModel>?) {
                        print(value)
                        // successMessage(value)
                        trendsLiveData.value = value!![0].trends
                        canShowLoading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        handleEror(e)
                    }
                })
        )
    }

    private fun clearAll() {
        disposable.clear()
    }


    fun handleEror(e: Throwable?) {

        if (e is UnknownHostException) {

            if (!connectivityVerifier.isNetworkConnected()) {
                errorMessage.value = context.getString(R.string.inter_connection)
            }

        } else {
            errorMessage.value = e!!.message ?: context.getString(R.string.unknown_error)
        }

        canShowLoading.value = false

    }


    // initilize  location service and authenticate service one by one
    fun initService(): Boolean {

        // find both location and authenticated
        if (hasLocationFetched() && hasAuthenticated()) {
            placeLiveData.value = placeLiveData.value
            return false
        }

        // check location has got already
        if (!hasLocationFetched()) {
            fecthLocation()
            return false
        }

        if (!hasAuthenticated()) {
            tokenStatus.value = true
            return false
        }

        return true
    }

    // get user current location
    fun fecthLocation() {
        //setProgressEnum(ProgressEnum.MAIN_PROGRESS_BAR)
        canShowLoading.value = true

        // onlocation received
        locationProvider.getUpdatedLocation(locationRequest).subscribe { location ->
            locationLiveData.value = location
            //canShowLoading.setValue(false)
        }
    }


    fun hasLocationFetched(): Boolean {

        if (locationLiveData.value != null) {
            return true
        }

        return false
    }

    fun hasAuthenticated(): Boolean {

        if (sheredPref.getString(context.getString(R.string.token_key))!!.isNotEmpty()) {
            return true
        }

        return false
    }

    fun getTocken(): String? {
        return sheredPref.getString(context.getString(R.string.token_key))
    }


    fun setToken(value: AuthendicateModel?): Boolean {
        sheredPref.setString(
            context.getString(R.string.token_key),
            value!!.token_type + " " + value.access_token
        )
        return true
    }


}