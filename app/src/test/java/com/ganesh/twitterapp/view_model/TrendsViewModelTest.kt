package com.ganesh.twitterapp.view_model



import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.mockito.*
import androidx.arch.core.executor.testing.*

import com.ganesh.twitterapp.data.model.*

import com.ganesh.twitterapp.domain.TrendsUsecases
import com.google.android.gms.location.LocationRequest
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.disposables.Disposable
import io.reactivex.Scheduler
import java.util.concurrent.Executor
import org.mockito.Mockito.*
import org.mockito.internal.verification.Times
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import java.util.concurrent.TimeUnit


class TrendsViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var trendsUsecases: TrendsUsecases

    @Mock
    lateinit var locationProvider: ReactiveLocationProvider

    @Mock
    lateinit var locationRequest: LocationRequest


    @InjectMocks
    private lateinit var rateListViewModel: TrendsListViewModel

    private lateinit var spyViewModel: TrendsListViewModel


    @Before
    fun initAll() {

        MockitoAnnotations.initMocks(this)

        rateListViewModel = TrendsListViewModel(
            trendsUsecases, locationRequest, locationProvider
        )

        spyViewModel = spy(rateListViewModel)

    }

    @Test
    fun getTrends_validInput_success() {
        val x = mutableListOf(Trends("", "", "", "", 0))

        val lo = mutableListOf(Locations("", 1))

        val data = listOf(TrendsOuterResponseModel(x, "", "", lo))

        val response = Single.just(data)


        `when`(trendsUsecases.getTrends("", "")).thenReturn(response)

        spyViewModel.getTrends()

        verify(spyViewModel).onSuccess(data)
        verify(spyViewModel, times(0)).onFailure(Throwable())

    }


    @Test
    fun getTrends_error_failure() {

        val x = mutableListOf(Trends("", "", "", "", 0))

        val lo = mutableListOf(Locations("", 1))

        val data = listOf(TrendsOuterResponseModel(x, "", "", lo))

        val eror = Throwable("Unknown error")

        val response: Single<List<TrendsOuterResponseModel>>? = Single.error(eror)

        `when`(trendsUsecases.getTrends("", "")).thenReturn(response)

        spyViewModel.getTrends()

        verify(spyViewModel, Times(0)).onSuccess(data)
        verify(spyViewModel, times(1)).onFailure(eror)

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