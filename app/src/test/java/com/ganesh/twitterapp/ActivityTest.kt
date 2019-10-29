package com.ganesh.twitterapp

import androidx.lifecycle.MutableLiveData
import com.ganesh.twitterapp.util.EnableGPS
import com.ganesh.twitterapp.view_model.TrendsListViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class ActivityTest {

    @Mock
    lateinit var gpsEnableView: EnableGPS

    @Mock
    lateinit var viewModel: TrendsListViewModel

    @InjectMocks
    var main: MainActivity = MainActivity()

    @Before
    fun initALl() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `show popup to enable gps when gps not enabled`() {


        `when`(gpsEnableView.hasGPSPermission()).thenReturn(true)

        main.setGpsEnableView()

        verify(gpsEnableView, times(1)).isEnabledGPS()


    }

    @Test
    fun `do not show popup to_enable gps when gps not enabled`() {

        `when`(gpsEnableView.hasGPSPermission()).thenReturn(false)

        main.setGpsEnableView()

        verify(gpsEnableView, times(0)).isEnabledGPS()

    }


}