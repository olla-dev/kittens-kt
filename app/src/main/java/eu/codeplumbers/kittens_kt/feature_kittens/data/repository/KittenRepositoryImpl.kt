package eu.codeplumbers.kittens_kt.feature_kittens.data.repository

import android.content.Context
import android.util.Log
import eu.codeplumbers.kittens_kt.KittenApp
import eu.codeplumbers.kittens_kt.core.api.Resource
import eu.codeplumbers.kittens_kt.feature_kittens.data.local.KittenDao
import eu.codeplumbers.kittens_kt.feature_kittens.data.local.entities.KittenEntity
import eu.codeplumbers.kittens_kt.feature_kittens.data.remote.KittenApi
import eu.codeplumbers.kittens_kt.feature_kittens.domain.model.Kitten
import eu.codeplumbers.kittens_kt.feature_kittens.domain.repository.KittenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.random.Random

class KittenRepositoryImpl (
    private val api: KittenApi,
    private val dao: KittenDao
) : KittenRepository {
    override suspend fun getRandomKitten(): Flow<Resource<Kitten>> = flow {
        // if something in db
        var cachedKitten = dao.getLatestKitten()
        emit(Resource.Loading(data = cachedKitten?.toKitten()))

        try {
            val width: Int = Random.nextInt(from=100, until = 1000)
            val height: Int = Random.nextInt(from=100, until = 1000)
            // get a random kitten by randomizing width and height
            val remoteKittenResponse = api.getRandomKitten(
                width,
                height
            )

            if (remoteKittenResponse.isSuccessful) {
                val filename = "${width}_${height}.jpeg"
                // save file to internal storage
                val input = remoteKittenResponse.body()?.byteStream()
                val fileOutputStream = FileOutputStream("${KittenApp.instance.cacheDir}/${filename}")
                fileOutputStream.use { output ->
                    val buffer = ByteArray(4 * 1024) // or other buffer size
                    var read: Int
                    while (input?.read(buffer).also { read = it!! } != -1) {
                        output.write(buffer, 0, read)
                    }
                    output.flush()
                }
                input?.close()

                // update cached data
                val remoteKitten = KittenEntity(timestamp = Date().time, imageFileName = filename )
                dao.insertKitten(remoteKitten)
            } else {
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = cachedKitten?.toKitten()
                )
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!",
                    data = cachedKitten?.toKitten()
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = cachedKitten?.toKitten()
                )
            )
        }

        cachedKitten = dao.getLatestKitten()
        val newKitten = cachedKitten?.toKitten()
        emit(Resource.Success(newKitten))
    }
}
