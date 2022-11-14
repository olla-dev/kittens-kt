package eu.codeplumbers.kittens_kt.feature_kittens.data.remote

import eu.codeplumbers.kittens_kt.feature_kittens.data.remote.dto.KittenDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface KittenApi {
    @Streaming
    @GET("{width}/{height}")
    suspend fun getRandomKitten(@Path("width") width: Int?, @Path("height") height: Int?): Response<ResponseBody>

    companion object {
        const val BASE_URL = "https://placekitten.com"
    }
}