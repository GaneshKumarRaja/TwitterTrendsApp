
package com.ganesh.twitterapp.data.model

public data class TrendsOuterResponseModel(

    val trends: List<Trends>,
    val as_of: String,
    val created_at: String,
    val locations: List<Locations>
) {




}


data class Trends(

    val name: String,
    val url: String,
    val promoted_content: String,
    val query: String,
    val tweet_volume: Int
)


data class Locations(

    val name: String,
    val woeid: Int
)
