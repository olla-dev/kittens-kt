package eu.codeplumbers.kittens_kt.feature_kittens.presentation

import eu.codeplumbers.kittens_kt.feature_kittens.domain.model.Kitten

data class MainScreenState(
    val loading: Boolean = false,
    val latestKitten: Kitten? = null,
    val error: String? = "",
    val internetConnected: Boolean = true
)