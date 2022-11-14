package eu.codeplumbers.kittens_kt.feature_kittens.domain.repository

import eu.codeplumbers.kittens_kt.core.api.Resource
import eu.codeplumbers.kittens_kt.feature_kittens.domain.model.Kitten
import kotlinx.coroutines.flow.Flow

interface KittenRepository {
    suspend fun getRandomKitten(): Flow<Resource<Kitten>>
}