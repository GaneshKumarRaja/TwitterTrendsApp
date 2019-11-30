package com.ganesh.twitterapp.view_model


import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.ganesh.twitterapp.data.model.Trends
import com.ganesh.twitterapp.data.model.TrendsOuterResponseModel
import com.ganesh.twitterapp.domain.TrendsUsecases
import com.google.android.gms.location.LocationRequest
import io.reactivex.disposables.Disposable
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import javax.inject.Inject

open class TrendsListViewModel @Inject constructor(
    private var trendsUsecases: TrendsUsecases,
    private var locationRequest: LocationRequest,
    private var locationProvider: ReactiveLocationProvider

) :
    BaseViewModel() {


    private var locationData: Location? = null

    var trendsLiveData: MutableLiveData<List<Trends>> = MutableLiveData()

    private lateinit var locationProvideDisposel: Disposable

    private var lattitude: String =""
    private var longitude: String=""

    fun init() {

        // to show progress indicator
        canShowLoading.postValue(true)

        // if location is not fecthced yet
        if (locationData == null) {
            fecthLocation()
            return
        }
    }

    private fun setLocation() {
        lattitude = locationData?.latitude.toString()
        longitude = locationData?.longitude.toString()
    }

    /**
     *  get Trends
     */
    fun getTrends(): Boolean {

        canShowLoading.value = true

        disposable.add(
            trendsUsecases.getTrends(lattitude, longitude)
                .subscribe(this::onSuccess, this::onFailure)
        )

        return true
    }


     fun onSuccess(res: List<TrendsOuterResponseModel>?) {
        trendsLiveData.postValue(res!![0].trends)
        canShowLoading.postValue(false)
    }

     fun onFailure(e: Throwable) {
        errorMessage.value = e.message
        canShowLoading.postValue(false)
    }


    // get user current location
    private fun fecthLocation() {
        //setProgressEnum(ProgressEnum.MAIN_PROGRESS_BAR)
        canShowLoading.postValue(true)

        // onlocation received
        locationProvideDisposel =
            locationProvider.getUpdatedLocation(locationRequest).subscribe { location ->
                disposeLocationProvider()
                locationData = location
                setLocation()
                getTrends()
            }

    }

    // remove location update, once it gets a location
    private fun disposeLocationProvider() {
        this.locationProvideDisposel.dispose()
    }




}