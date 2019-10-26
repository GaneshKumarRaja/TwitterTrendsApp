package com.ganesh.twitterapp.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.ganesh.twitterapp.R
import com.ganesh.twitterapp.adapter.TrendsAdapter.*
import com.ganesh.twitterapp.data.model.Trends
import kotlinx.android.synthetic.main.trends_adapter_layout.view.*
import java.util.ArrayList
import javax.inject.Inject

class TrendsAdapter @Inject constructor() : RecyclerView.Adapter<TrendsHolder>() {
    private var data: List<Trends>? = null


    fun setData(data: List<Trends>) {
        this.data = data
    }

    fun getData() :List<Trends>?{
       return  data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendsHolder {
        var inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.trends_adapter_layout, parent, false)
        return TrendsHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: TrendsHolder, position: Int) {
        val model = data!!.get(position)
        holder.name.text = model.name ?: ""
        holder.volume.text = "" + model.tweet_volume ?: ""
        holder.url.text = model.url ?: ""
        holder.query.text = model.query ?: ""
    }


    class TrendsHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var name: TextView
        lateinit var volume: TextView
        lateinit var url: TextView
        lateinit var query: TextView

        init {
            name = itemView.txt_name
            volume = itemView.txt_volume
            url = itemView.txt_url
            query = itemView.txt_query

        }
    }


}

