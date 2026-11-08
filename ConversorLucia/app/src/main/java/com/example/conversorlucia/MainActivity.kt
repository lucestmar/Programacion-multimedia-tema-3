package com.example.conversorlucia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.conversorlucia.ui.theme.ConversorLuciaTheme
import kotlin.random.Random

/*
La aplicación debe tener un botón que generará un número decimal entre 1 y 10000. Cuando
se genere el número se mostrará por pantalla.
Para realizar las conversiones se dispondrá de dos botones, uno para convertir a binario y
otro para convertir a hexadecimal. Cuando se pulse uno de estos botones debe aparecer el
número en hexadecimal. También tendrá un botón que borrará todos los números de la
pantalla.
La aplicación debe tener en la parte inferior un texto con tu nombre.
Se puede usar todo lo visto en los apuntes de la unidad hasta este momento.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConversorLuciaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Conversor(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Conversor(modifier: Modifier = Modifier) {
    var numeroGenerado by remember { mutableStateOf<Int?>(null) }
    var resultadoConversion by remember { mutableStateOf("") }

    // Carga de dimensiones
    val espaciadoNormal = dimensionResource(R.dimen.espaciado_normal)
    val espaciadoPequeno = dimensionResource(R.dimen.espaciado_pequeno)

    val tamanoTitulo = dimensionResource(R.dimen.texto_titulo).value.sp
    val tamanoCuerpo = dimensionResource(R.dimen.texto_cuerpo).value.sp
    val tamanoPiePagina = dimensionResource(R.dimen.texto_pie_pagina).value.sp

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(espaciadoNormal),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.titulo_conversor),
            fontSize = tamanoTitulo,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = espaciadoNormal)
        )

        // Botón para generar número entre 1 y 10000
        Button(
            onClick = {
                numeroGenerado = Random.nextInt(1, 10001)
                resultadoConversion = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.btn_crear_numero))
        }

        Spacer(modifier = Modifier.height(espaciadoNormal))

        // Visualización del número generado
        Text(
            text = numeroGenerado?.let { stringResource(R.string.numero_generado, it) }
                ?: stringResource(R.string.sin_numero_generado),
            fontSize = tamanoCuerpo,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(espaciadoNormal))

        // Botones de conversión
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val binarioFormat = stringResource(R.string.resultado_binario)
            Button(
                onClick = {
                    numeroGenerado?.let {
                        resultadoConversion = String.format(binarioFormat, Integer.toBinaryString(it))
                    }
                },
                enabled = numeroGenerado != null
            ) {
                Text(text = stringResource(R.string.btn_binario))
            }

            val hexFormat = stringResource(R.string.resultado_hexadecimal)
            Button(
                onClick = {
                    numeroGenerado?.let {
                        resultadoConversion = String.format(hexFormat, Integer.toHexString(it).uppercase())
                    }
                },
                enabled = numeroGenerado != null
            ) {
                Text(text = stringResource(R.string.btn_hexadecimal))
            }
        }

        Spacer(modifier = Modifier.height(espaciadoNormal))

        // Visualización del resultado de la conversión
        if (resultadoConversion.isNotEmpty()) {
            Text(
                text = resultadoConversion,
                fontSize = tamanoCuerpo,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(espaciadoNormal))

        // Botón para limpiar los datos en pantalla
        Button(
            onClick = {
                numeroGenerado = null
                resultadoConversion = ""
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(
                text = stringResource(R.string.btn_borrar_todo),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Pie de página con tu nombre
        Text(
            text = stringResource(R.string.foot_desarrollador),
            fontSize = tamanoPiePagina,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = espaciadoPequeno)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConversorLuciaTheme {
        Conversor(modifier = Modifier.padding())
    }
}