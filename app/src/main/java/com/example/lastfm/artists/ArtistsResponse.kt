package com.example.lastfm.artists

import com.google.gson.annotations.SerializedName

data class ArtistsResponse(
    val results: Results? = null,
)

data class Results(
    val artistmatches: Artists? = null,
)
data class Artists(
    @SerializedName("artist")
    val artists: List<Artist>? = null,
)
data class Artist(
    val image: List<Image?>? = null,
    val listeners: String? = null,
    val mbid: String? = null,
    val name: String? = null,
    val streamable: String? = null,
    val url: String? = null,
) {
    data class Image(
        @SerializedName("#text")
        val text: String? = null,
        val size: String? = null,
    )
}
