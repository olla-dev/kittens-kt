package eu.codeplumbers.kittens_kt.feature_kittens.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.codeplumbers.kittens_kt.KittenApp
import eu.codeplumbers.kittens_kt.core.api.Resource
import eu.codeplumbers.kittens_kt.feature_kittens.data.util.NetworkListener
import eu.codeplumbers.kittens_kt.feature_kittens.domain.use_case.GetRandomKitten
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getRandomKitten: GetRandomKitten,
    private val networkListener: NetworkListener
) : ViewModel() {
    private var _state = MutableStateFlow<MainScreenState>(MainScreenState())
    val state: StateFlow<MainScreenState> get() = _state

    init {
        if (_state.value.internetConnected) {
            fetchNewKitten()
        }
    }

    fun updateNetworkStatus(connected: Boolean) {
        _state.value = MainScreenState(
            _state.value.loading,
            _state.value.latestKitten,
            _state.value.error,
            connected
        )
    }

    fun fetchNewKitten(){
        CoroutineScope(Dispatchers.IO).launch {
            getRandomKitten.invoke()
                .catch { exception ->
                    _state.value = MainScreenState(
                        loading = true,
                        error= exception.message.toString()
                    )
                }
                .collect { result ->
                    _state.value = MainScreenState(loading = true)
                    when(result){
                        is Resource.Loading -> _state.value = MainScreenState(
                            loading = true,
                            latestKitten = result.data
                        )
                        is Resource.Error -> _state.value = MainScreenState(error = result.message)
                        is Resource.Success ->
                            _state.value = MainScreenState(
                                loading = false,
                                latestKitten = result.data,
                                error = ""
                            )
                    }
                }
        }
    }
}