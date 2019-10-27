package com.ganesh.twitterapp


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
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.disposables.Disposable
import io.reactivex.Scheduler
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit



class TrendsViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var appApiHelper: AppApiHelper


    @InjectMocks
    private var rateListViewModel: TrendsListViewModel = TrendsListViewModel()

    private lateinit var spyViewModel: TrendsListViewModel


    @Before
    fun initAll() {

        MockitoAnnotations.initMocks(this)

        spyViewModel = spy(rateListViewModel)

    }


    @Test
    fun `success case for autehntication web service calling`() {

        val authModel = AuthendicateModel("sa", "a")

        val response: Single<AuthendicateModel>? = Single.just(authModel)

        `when`(appApiHelper.doAuthendicate("XXXXXXX")).thenReturn(response)

        spyViewModel.doAuthendicate("XXXXXXX")

        assert(spyViewModel.tokenStatus.value == true)

        assert(spyViewModel.canShowLoading.value == false)

        assert(spyViewModel.errorMessage.value == null)

    }

    @Test
    fun `failure case for autehntication web service calling`() {

        val eror = Throwable("Unknown error")

        val response: Single<AuthendicateModel>? = Single.error(eror)

        `when`(appApiHelper.doAuthendicate("XXXXXXX")).thenReturn(response)

        spyViewModel.doAuthendicate("XXXXXXX")

        assert(spyViewModel.tokenStatus.value == false)

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
    fun `success case for fetching place details web service calling`() {

        val x = mutableListOf(
            PlaceOuterResponseModel(
                "", "", "", 0,
                PlaceOuterResponseModel.PlaceType(0, ""), "", 1
            )
        )

        val response: Single<List<PlaceOuterResponseModel>>? = Single.just(x)

        `when`(appApiHelper.getPcaeDetails("sd a", "", "")).thenReturn(response)

        spyViewModel.getPlaceDetails("sd a", "", "")

        assert(spyViewModel.placeLiveData.value != null)

        assert(spyViewModel.canShowLoading.value == false)

        assert(spyViewModel.errorMessage.value == null)

    }


    @Test
    fun `failure case for fetching place details web service calling`() {

        val eror = Throwable("Unknown error")

        val response: Single<List<PlaceOuterResponseModel>>? = Single.error(eror)

        `when`(appApiHelper.getPcaeDetails("sd a", "", "")).thenReturn(response)

        spyViewModel.getPlaceDetails("sd a", "", "")

        assert(spyViewModel.placeLiveData.value == null)

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