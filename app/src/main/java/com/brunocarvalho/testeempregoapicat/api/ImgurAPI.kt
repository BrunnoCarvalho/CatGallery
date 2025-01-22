package com.brunocarvalho.testeempregoapicat.api

import com.brunocarvalho.testeempregoapicat.model.GallerySearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImgurAPI {

    @GET("gallery/search/{page}")
    suspend fun pesquisarImagens(
        @Path("page") page: Int = 1,
        @Query("q") q :String = "cats",
        @Query("q_type") type: String = "jpeg"
    ): Response<GallerySearchResponse>

}