package eu.codeplumbers.kittens_kt.feature_kittens.domain.use_case

import eu.codeplumbers.kittens_kt.core.api.Resource
import eu.codeplumbers.kittens_kt.feature_kittens.domain.model.Kitten
import eu.codeplumbers.kittens_kt.feature_kittens.domain.repository.KittenRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRandomKitten @Inject constructor(
    private val repository: KittenRepository
) {
    suspend fun invoke(): Flow<Resource<Kitten>> {
        return repository.getRandomKitten()
    }
}