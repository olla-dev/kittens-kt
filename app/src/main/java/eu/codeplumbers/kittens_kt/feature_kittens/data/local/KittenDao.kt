package eu.codeplumbers.kittens_kt.feature_kittens.data.local

import androidx.room.*
import eu.codeplumbers.kittens_kt.feature_kittens.data.local.entities.KittenEntity

@Dao
interface KittenDao {
    @Query("Select * from kittens ORDER BY timestamp DESC LIMIT 1")
    fun getLatestKitten(): KittenEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKitten(kitten: KittenEntity)
}
