package com.ganesh.twitterapp

import com.ganesh.twitterapp.util.EnableGPS
import com.ganesh.twitterapp.view.MainActivity


import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainActivityTest {
    @Mock
    lateinit var gpsEnableView: EnableGPS


    @InjectMocks
    var main: MainActivity = MainActivity()

    @Before
    fun initALl() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `show popup to enable gps when gps not enabled`() {


        Mockito.`when`(gpsEnableView.hasGPSPermission()).thenReturn(true)

        main.setGpsEnableView()

        Mockito.verify(gpsEnableView, Mockito.times(1)).isEnabledGPS()


    }

    @Test
    fun `do not show popup to_enable gps when gps not enabled`() {

        Mockito.`when`(gpsEnableView.hasGPSPermission()).thenReturn(false)

        main.setGpsEnableView()

        Mockito.verify(gpsEnableView, Mockito.times(0)).isEnabledGPS()

    }
}