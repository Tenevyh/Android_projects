package com.tenevyh.android.photogallery

import com.tenevyh.android.photogallery.FlickrApi.FlickrApi
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

class PhotoRepository {
    private val flickrApi: FlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.flickr.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        flickrApi = retrofit.create()
    }

    suspend fun fetchContents() = flickrApi.fetchContents()
}