package eu.codeplumbers.kittens_kt.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import eu.codeplumbers.kittens_kt.feature_kittens.data.util.NetworkListener
import eu.codeplumbers.kittens_kt.feature_kittens.presentation.MainScreen
import eu.codeplumbers.kittens_kt.ui.theme.KittensktTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var networkListener: NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkListener = NetworkListener(this)

        setContent {
            KittensktTheme {
                Scaffold(
                    topBar = {
                    }
                ) {
                    MainScreen()
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KittensktTheme {
        Scaffold(
            topBar = {
            }
        ) {
            MainScreen()
        }
    }
}