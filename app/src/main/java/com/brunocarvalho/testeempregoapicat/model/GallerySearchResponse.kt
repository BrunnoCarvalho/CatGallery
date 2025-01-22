package com.brunocarvalho.testeempregoapicat.model

data class GallerySearchResponse(
    val `data`: List<Data>,
    val status: Int,
    val success: Boolean
)