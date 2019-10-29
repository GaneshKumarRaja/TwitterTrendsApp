package com.ganesh.twitterapp.view_model


import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.ganesh.twitterapp.BuildConfig
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
import io.reactivex.disposables.Disposable
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


    var locationData: Location? = null

    var trendsLiveData: MutableLiveData<List<Trends>> = MutableLiveData()

    lateinit var placeData: PlaceOuterResponseModel


    lateinit var locationProvideDisposel: Disposable

    fun doAuthendicate(): Boolean {

        canShowLoading.postValue(true)

        clearAll()

        disposable.add(
            apiRepo.doAuthendicate(BuildConfig.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<AuthendicateModel>() {

                    override fun onSuccess(value: AuthendicateModel?) {

                        setToken(value)
                        initPlaceDetails()

                    }

                    override fun onError(e: Throwable?) {
                        handleEror(e)
                    }
                })
        )

        return true
    }


    fun getPlaceDetails(oAuthtoken: String, lat: String, lon: String) {

        canShowLoading.postValue(true)
        clearAll()


        disposable.add(
            apiRepo.getPcaeDetails(oAuthtoken, lat, lon)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PlaceOuterResponseModel>>() {

                    override fun onSuccess(value: List<PlaceOuterResponseModel>?) {

                        if (value!!.isNotEmpty()) {
                            //placeLiveData.postValue(value[0])
                            placeData = value[0]
                            initTrendsDetails()

                        }

                    }

                    override fun onError(e: Throwable?) {
                        handleEror(e)

                    }
                })
        )
    }


    fun getTrendsData(token: String, placeID: String) {

        canShowLoading.postValue(true)
        clearAll()

        disposable.add(
            apiRepo.getTrends(token, placeID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<TrendsOuterResponseModel>>() {

                    override fun onSuccess(value: List<TrendsOuterResponseModel>?) {
                        print(value)
                        // successMessage(value)
                        trendsLiveData.postValue(value!![0].trends)
                        canShowLoading.postValue(false)
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


    // whereever error occured, the error message need to be shown to the user and close the progress indicator
    fun handleEror(e: Throwable?) {

        if (e is UnknownHostException) {

            if (!connectivityVerifier.isNetworkConnected()) {
                errorMessage.postValue(context.getString(R.string.inter_connection))
            }

        } else {
            errorMessage.postValue(e!!.message ?: context.getString(R.string.unknown_error))
        }

        canShowLoading.postValue(false)

    }


    // get user current location
    fun fecthLocation() {
        //setProgressEnum(ProgressEnum.MAIN_PROGRESS_BAR)
        canShowLoading.postValue(true)

        // onlocation received
        locationProvideDisposel =
            locationProvider.getUpdatedLocation(locationRequest).subscribe { location ->

                disposeLocationProvider()
                initAuthenticatation(location)
            }

    }

    // remove location update, once it gets a location
    fun disposeLocationProvider() {
        locationProvideDisposel.dispose()
    }

    // assign the location to locationData and call the place webservie
    fun initAuthenticatation(location: Location) {
        locationData = location
        doAuthendicate()
    }


    // get tlken from shaered preference
    fun getTocken(): String? {
        return sheredPref.getString(context.getString(R.string.token_key))
    }


    // store tokne inot shared prefernce
    fun setToken(value: AuthendicateModel?): Boolean {

        sheredPref.setString(
            context.getString(R.string.token_key),
            value!!.token_type + " " + value.access_token
        )

        return true
    }


    fun initTrendService() {

        // to show progress indicator
        canShowLoading.postValue(true)

        // if location is not fecthced yet
        if (locationData == null) {
            fecthLocation()
            return
        }

        // if authentication is not done yet
        if (getTocken()!!.length == 0) {
            doAuthendicate()
            return
        }

        // if both location and authentication is done already, get place details from server
        initPlaceDetails()


    }

    // call place web serice
    fun initPlaceDetails() {
        getPlaceDetails(getTocken()!!, "" + locationData!!.latitude, "" + locationData!!.longitude)
    }


    // call trends web service
    fun initTrendsDetails() {
        getTrendsData(getTocken()!!, "" + placeData.woeid)
    }


}