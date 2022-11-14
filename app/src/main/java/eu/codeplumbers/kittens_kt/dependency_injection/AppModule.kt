package eu.codeplumbers.kittens_kt.dependency_injection

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.codeplumbers.kittens_kt.KittenApp
import eu.codeplumbers.kittens_kt.feature_kittens.data.local.KittenDatabase
import eu.codeplumbers.kittens_kt.feature_kittens.data.remote.KittenApi
import eu.codeplumbers.kittens_kt.feature_kittens.data.repository.KittenRepositoryImpl
import eu.codeplumbers.kittens_kt.feature_kittens.data.util.NetworkListener
import eu.codeplumbers.kittens_kt.feature_kittens.domain.repository.KittenRepository
import eu.codeplumbers.kittens_kt.feature_kittens.domain.use_case.GetRandomKitten
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGetKittenUseCase(repository: KittenRepository): GetRandomKitten {
        return GetRandomKitten(repository)
    }

    @Provides
    @Singleton
    fun provideKittenRepository(
        db: KittenDatabase,
        api: KittenApi
    ): KittenRepository {
        return KittenRepositoryImpl(api, db.noteDao)
    }

    @Provides
    @Singleton
    fun provideKittenDatabase(app: Application): KittenDatabase {
        return Room.databaseBuilder(
            app, KittenDatabase::class.java, "kitten_db"
        )
        .build()
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideKittenApi(okHttpClient: OkHttpClient): KittenApi {
        return Retrofit.Builder()
            .baseUrl(KittenApi.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(KittenApi::class.java)
    }


    @Provides
    @Singleton
    fun provideNetworkListener(): NetworkListener {
        return NetworkListener(KittenApp.instance)
    }
}