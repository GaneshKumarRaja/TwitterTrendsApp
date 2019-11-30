package com.ganesh.twitterapp.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.ganesh.twitterapp.R
import com.ganesh.twitterapp.view.TrendsAdapter.*
import com.ganesh.twitterapp.data.model.Trends
import kotlinx.android.synthetic.main.trends_adapter_layout.view.*
import javax.inject.Inject

class TrendsAdapter @Inject constructor() : RecyclerView.Adapter<TrendsHolder>() {
    private var data: List<Trends>? = null


    fun setData(data: List<Trends>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.trends_adapter_layout, parent, false)
        return TrendsHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: TrendsHolder, position: Int) {
        val model = data!![position]
        holder.name.text = model.name
        holder.volume.text = model.tweet_volume.toString()
        holder.url.text = model.url
        holder.query.text = model.query
    }

    class TrendsHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.txt_name
        var volume: TextView = itemView.txt_volume
        var url: TextView = itemView.txt_url
        var query: TextView = itemView.txt_query
    }
}

