package com.example.contadordeclics.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.contadordeclics.R


@Composable
fun ClickContent(modifier: Modifier = Modifier) {
    val nombre: String = "Lucia"
    var times by rememberSaveable { mutableStateOf(value = 0) }

    val textSizeMedium = dimensionResource(id = R.dimen.text_size_medium).value.sp
    val textSizeLarge = dimensionResource(id = R.dimen.text_size_large).value.sp
    val textSizeButton = dimensionResource(id = R.dimen.text_size_button).value.sp

    val spacerHeight = dimensionResource(id = R.dimen.spacer_height)
    val buttonPadding = dimensionResource(id = R.dimen.button_padding)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.content_text, times),
            fontSize = textSizeMedium
        )

        Spacer(modifier = Modifier.height(spacerHeight))

        Text(
            text = stringResource(R.string.frase, nombre, times),
            fontSize = textSizeLarge
        )

        Spacer(modifier = Modifier.height(spacerHeight))

        Button(
            onClick = {
                times++
                Log.i("BOTON_CLIC", "Clicks: $times")
            }
        ) {
            Text(
                text = stringResource(R.string.btn_pulsame),
                fontSize = textSizeButton,
                modifier = Modifier.padding(buttonPadding)
            )
        }

        Button(onClick = { times = 0 }) {
            Text(
                text = stringResource(id = R.string.reset),
                fontSize = textSizeButton,
                modifier = Modifier.padding(buttonPadding)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4)
@Preview(
    showBackground = true,
    name = "Englis"
)
@Composable
fun ContentPreview() {
    MyAppContent {innerPadding ->
        ClickContent(modifier = Modifier.padding(innerPadding))

    }
}