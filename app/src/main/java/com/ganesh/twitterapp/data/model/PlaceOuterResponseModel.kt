
package com.ganesh.twitterapp.data.model

data class PlaceOuterResponseModel (

	val country : String,
	val countryCode : String,
	val name : String,
	val parentid : Int,
	val placeType : PlaceType,
	val url : String,
	val woeid : Int
){
	data class PlaceType (

		val code : Int,
		val name : String
	)
}