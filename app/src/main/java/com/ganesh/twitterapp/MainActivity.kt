package com.ganesh.twitterapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ganesh.twitterapp.adapter.TrendsAdapter
import com.ganesh.twitterapp.data.model.Trends
import com.ganesh.twitterapp.util.EnableGPS
import com.ganesh.twitterapp.view_model.TrendsListViewModel

import com.ganesh.twitterapp.di.component.DaggerActivityComponent
import com.ganesh.twitterapp.di.module.ActivityModule
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var gpsEnableView: EnableGPS


    @Inject
    lateinit var viewModel: TrendsListViewModel

    @Inject
    lateinit var trendsAdapter: TrendsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDagger()
        initRecycreView()
        initViewModel()
    }


    override fun onResume() {
        super.onResume()
        setGpsEnableView()
    }

    private fun initViewModel() {
        observableViewModel()
    }

    private fun initDagger() {
        val appcom = (application as TwitterApplication).appComponent

        DaggerActivityComponent.builder().appComponent(appcom)
            .activityModule(ActivityModule(this)).build().inject(this)

    }


    private fun initRecycreView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = trendsAdapter
    }


    private fun setDataToRecuclerView(data: List<Trends>) {
        trendsAdapter.setData(data)
        trendsAdapter.notifyDataSetChanged()
    }


    private fun observableViewModel() {

        viewModel.canShowLoading.observe(this, Observer {

            progress_circular.visibility = View.VISIBLE

            if (!it) {
                progress_circular.visibility = View.GONE
            }

        })

        viewModel.errorMessage.observe(this, Observer {
            setDataToRecuclerView(emptyList())
            txt_error.text = it
        })

        viewModel.trendsLiveData.observe(this, Observer {
            setDataToRecuclerView(it)
        })


    }


    fun setGpsEnableView() {
        // verify user has enabled GPS permission in app setting
        if (gpsEnableView.hasGPSPermission()) {

            // GPS is turned on
            if (gpsEnableView.isEnabledGPS()) {
                viewModel.initTrendService()
            }
        }
    }


}

