package eu.codeplumbers.kittens_kt.feature_kittens.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.codeplumbers.kittens_kt.feature_kittens.data.local.entities.KittenEntity

@Database(
    entities = [KittenEntity::class],
    version= 1
)
abstract class KittenDatabase : RoomDatabase() {
    abstract val noteDao: KittenDao

    companion object {
        const val DATABASE_NAME = "kitten_db"
    }
}