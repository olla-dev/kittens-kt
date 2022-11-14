package eu.codeplumbers.kittens_kt.feature_kittens.data.remote.dto

import eu.codeplumbers.kittens_kt.feature_kittens.data.local.entities.KittenEntity

data class KittenDto(
    val imageFileName: String,
    val timestamp: Long,
) {
    fun toKittenEntity(): KittenEntity {
        return KittenEntity(
            imageFileName= imageFileName,
            timestamp= timestamp,
        )
    }
}