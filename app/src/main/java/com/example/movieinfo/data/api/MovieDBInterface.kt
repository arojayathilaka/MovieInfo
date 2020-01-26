package com.example.movieinfo.data.api

import com.example.movieinfo.data.vo.PopularMovies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<PopularMovies>

}