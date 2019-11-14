package com.ganesh.twitterapp


import android.content.Context
import android.location.Location
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.*
import androidx.arch.core.executor.testing.*
import com.ganesh.twitterapp.data.model.*
import com.ganesh.twitterapp.view_model.TrendsListViewModel
import com.ganesh.twitterapp.data.repo.AppApiHelper
import com.ganesh.twitterapp.util.ConnectivityVerifier
import com.ganesh.twitterapp.util.KeyValueHandler
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.disposables.Disposable
import io.reactivex.Scheduler
import java.util.concurrent.Executor
import org.mockito.Mockito.*
import java.util.concurrent.TimeUnit


class TrendsViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var appApiHelper: AppApiHelper


    @InjectMocks
    private var rateListViewModel: TrendsListViewModel = TrendsListViewModel()

    private lateinit var spyViewModel: TrendsListViewModel


    @Mock
    lateinit var context: Context


    @Mock
    private lateinit var sheredPref: KeyValueHandler


    @Mock
    lateinit var location: Location


    @Mock
    lateinit var connectivityVerifier: ConnectivityVerifier

    @Before
    fun initAll() {

        MockitoAnnotations.initMocks(this)

        spyViewModel = spy(rateListViewModel)

    }


    @Test
    fun `ensure authentication web service is called and fetchlocation() is called, when location is already feteched`() {

        spyViewModel.locationData = location

        location.longitude = 0.0

        location.longitude = 0.0

        `when`(context.getString(R.string.token_key)).thenReturn("token")

        `when`(sheredPref.getString("token")).thenReturn("")

        resuseable_Doauthtication_method()

        resuseable_place_webservice_method()

        resuseable_trends_webservice_method()

        spyViewModel.initTrendService()

        `verify`(spyViewModel, times(1)).doAuthendicate()


        `verify`(spyViewModel, times(0)).fecthLocation()

    }


    @Test
    fun `ensure place web service is called, when location and authentication is done already`() {

        spyViewModel.locationData = location

        location.longitude = 0.0

        location.longitude = 0.0

        `when`(context.getString(R.string.token_key)).thenReturn("token")

        `when`(sheredPref.getString("token")).thenReturn("somevalue")

        resuseable_Doauthtication_method()

        resuseable_place_webservice_method()

        resuseable_trends_webservice_method()

        spyViewModel.initTrendService()

        `verify`(spyViewModel, times(0)).doAuthendicate()


        `verify`(spyViewModel, times(0)).fecthLocation()


        `verify`(spyViewModel, times(1)).initPlaceDetails()


    }


    fun resuseable_Doauthtication_method() {


        val authModel = AuthendicateModel("sa", "a")

        val response: Single<AuthendicateModel>? = Single.just(authModel)

        `when`(appApiHelper.doAuthendicate(BuildConfig.API_KEY)).thenReturn(response)

    }


    fun resuseable_place_webservice_method() {

        val x = mutableListOf(
            PlaceOuterResponseModel(
                "", "", "", 0,
                PlaceOuterResponseModel.PlaceType(0, ""), "", 1
            )
        )

        val response: Single<List<PlaceOuterResponseModel>>? = Single.just(x)

        `when`(appApiHelper.getPcaeDetails(spyViewModel.getTocken()!!, "0.0", "0.0")).thenReturn(
            response
        )

    }

    fun resuseable_trends_webservice_method() {

        val x = mutableListOf(Trends("", "", "", "", 0))

        val lo = mutableListOf(Locations("", 1))

        val data = mutableListOf(TrendsOuterResponseModel(x, "", "", lo))

        val response: Single<List<TrendsOuterResponseModel>>? = Single.just(data)

        `when`(appApiHelper.getTrends(spyViewModel.getTocken()!!, "1")).thenReturn(response)
    }


    @Test
    fun `failure case for autehntication web service calling`() {

        val eror = Throwable("Unknown error")

        val response: Single<AuthendicateModel>? = Single.error(eror)

        `when`(appApiHelper.doAuthendicate(BuildConfig.API_KEY)).thenReturn(response)

        spyViewModel.doAuthendicate()

        // assert(spyViewModel.tokenStatus.value == false)

        assert(spyViewModel.canShowLoading.value == false)

        assert(spyViewModel.errorMessage.value != null)
    }


    @Test
    fun `success case for fetching trending details web service calling`() {

        val x = mutableListOf(Trends("", "", "", "", 0))

        val lo = mutableListOf(Locations("", 1))

        val data = mutableListOf(TrendsOuterResponseModel(x, "", "", lo))

        val response: Single<List<TrendsOuterResponseModel>>? = Single.just(data)

        `when`(appApiHelper.getTrends("sd a", "1")).thenReturn(response)

        spyViewModel.getTrendsData("sd a", "1")

        assert(spyViewModel.trendsLiveData.value != null)

        assert(spyViewModel.canShowLoading.value == false)

        assert(spyViewModel.errorMessage.value == null)

    }


    @Test
    fun `failure case for fetching trending details web service calling`() {

        val eror = Throwable("Unknown error")

        val response: Single<List<TrendsOuterResponseModel>>? = Single.error(eror)


        `when`(appApiHelper.getTrends("sd a", "1")).thenReturn(response)



        spyViewModel.getTrendsData("sd a", "1")

        assert(spyViewModel.trendsLiveData.value == null)

        assert(spyViewModel.canShowLoading.value == false)

        assert(spyViewModel.errorMessage.value != null)


    }


    @Test
    fun `failure case for fetching place details web service calling`() {

        val eror = Throwable("Unknown error")

        val response: Single<List<PlaceOuterResponseModel>>? = Single.error(eror)

        `when`(appApiHelper.getPcaeDetails("sd a", "", "")).thenReturn(response)

        spyViewModel.getPlaceDetails("sd a", "", "")

        //  assert(spyViewModel.placeLiveData.value == null)

        assert(spyViewModel.canShowLoading.value == false)

        assert(spyViewModel.errorMessage.value != null)

    }




    @Before
    fun setUpRxSchedulers() {

        val immediate = object : Scheduler() {

            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }


}