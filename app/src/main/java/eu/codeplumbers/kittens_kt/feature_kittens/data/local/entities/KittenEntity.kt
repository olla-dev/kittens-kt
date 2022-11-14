package eu.codeplumbers.kittens_kt.feature_kittens.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import eu.codeplumbers.kittens_kt.feature_kittens.domain.model.Kitten

@Entity(tableName = "kittens")
data class KittenEntity (
    val imageFileName: String,
    val timestamp: Long,
    @PrimaryKey val id: Int? = null
) {
    fun toKitten(): Kitten {
        return Kitten(
            imageFileName= imageFileName,
            timestamp= timestamp,
        )
    }
}

class InvalidKittenException(message:String): Exception(message)