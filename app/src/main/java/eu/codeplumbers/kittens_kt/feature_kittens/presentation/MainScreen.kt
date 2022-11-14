package eu.codeplumbers.kittens_kt.feature_kittens.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import eu.codeplumbers.kittens_kt.KittenApp
import eu.codeplumbers.kittens_kt.R
import eu.codeplumbers.kittens_kt.ui.MainActivity
import java.io.File

@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()
    val cacheDir = LocalContext.current.cacheDir
    val mainActivity = LocalContext.current as MainActivity
    mainActivity.networkListener.getConnected().observe(mainActivity){
        connected -> viewModel.updateNetworkStatus(connected)
    }


    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {


        if (state.value.loading) {
            CircularProgressIndicator()
            Spacer(Modifier.size(30.dp))
        } else {
            if (state.value.latestKitten != null) {
                Image(
                    painter = rememberImagePainter(
                        data = File(cacheDir, state.value.latestKitten!!.imageFileName),
                        builder = {
                            crossfade(true) //Crossfade animation between images
                            placeholder(R.drawable.placeholder) //Used while loading
                            fallback(R.drawable.placeholder) //Used if data is null
                            error(R.drawable.placeholder) //Used when loading returns with error
                        }
                    ),
                    contentDescription = stringResource(R.string.placeholder_content_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(25.dp)
                        .fillMaxWidth()
                        .height(300.dp)
                        .defaultMinSize(250.dp)
                        .clip(RoundedCornerShape(10))
                        .border(2.dp, Color.Gray, RoundedCornerShape(10))
                )
            }
        }
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Button(
            enabled = state.value.internetConnected,
            onClick = {
                viewModel.fetchNewKitten()
            },
            // Uses ButtonDefaults.ContentPadding by default
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            ),
        ) {
            // Inner content including an icon and a text label
            if (state.value.loading) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.get_new_picture_content_description),
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                if (state.value.loading)
                    stringResource(R.string.fetching_new_picture_content_description)
                else
                    stringResource(R.string.get_new_picture_content_description)
            )
        }

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))

        if(state.value.error!!.isNotBlank()) {
            Text(
                text = state.value.error!!,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
        }

        if(!state.value.internetConnected) {
            Text(
                text = stringResource(id = R.string.not_connected),
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
        }
    }
}