package com.example.padelscorelucia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.padelscorelucia.ui.theme.PadelScoreLuciaTheme
/*
Crea un proyecto en Android Studio llamado PadelScoreTuNombre.
Esta aplicación permitirá llevar la puntuación de un partido de pádel.
La aplicación tendrá 3 botones, uno para sumar puntos para el equipo A, otro para sumar puntos
para el equipo B y un tercer botón para resetear todo a cero.
Cuando un equipo consigue 2 sets gana el partido y no se debe poder sumar puntos. En ese
momento para poder sumar puntos se deberá resetear el marcador.
Igual que existe el componente Column para organizar los elementos en forma de columna,
también existe el componente Row para organizarlos en fila. Se pueden incluir elementos Row
dentro de un Column y también se pueden incluir elementos Column dentro de un Row.
Alinear el contenido de un Column y un Row es un poco diferente, se puede ver en las siguientes
capturas:
La aplicación deberá ser multi idioma: castellano, valenciano (catalán) e inglés.
Se puede usar todo lo visto en los apuntes de la unidad hasta este momento.
A continuación, se muestra un ejemplo de la interfaz de la aplicación. Tu aplicación no tiene que
ser idéntica a la interfaz de ejemplo.
En el pádel la puntuación es similar a la del tenis, pero con unas pequeñas variaciones:
- Se juega al mejor de 3 sets (el equipo que gana 2 sets gana el partido).


- Para ganar un set un equipo debe ganar 6 juegos con diferencia de dos juegos, en 6-5 no acaba el

set, deberá acabar en 7-5 o 7-6.
- En cada juego los dos equipos empiezan con cero puntos.


- Para ganar un juego se deben acumular puntos, cada punto ganado va subiendo el marcador de

la siguiente manera: 0 à 15 à 30 à 40.
- Si se llega a 40 y el otro equipo tiene menos de 40 entonces se gana el juego.


- Si se empata a 40 puntos en un juego, el equipo que gane el siguiente punto gana el juego (punto

de oro).
- Si se empata a 6 juegos se debe jugar el tie break. En el tie-break la puntuación es de 1 en 1 y el

equipo que llegue a 7 puntos gana el set siempre que tenga diferencia de dos puntos, si al llegar a
7 no se tiene diferencia de 2 puntos se seguirán jugando puntos hasta conseguir esa diferencia
(por ejemplo 6-7 no acaba el juego, pero 8-6 sí).
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PadelScoreLuciaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {

    val textSizeHeader = dimensionResource(id = R.dimen.text_size_header).value.sp
    val textSizeScore = dimensionResource(id = R.dimen.text_size_score).value.sp
    val textSizeButton = dimensionResource(id = R.dimen.text_size_button).value.sp
    val headerPaddingVertical = dimensionResource(id = R.dimen.header_padding_vertical)
    val paddingHorizontalMedium = dimensionResource(id = R.dimen.padding_horizontal_medium)
    val paddingVerticalSmall = dimensionResource(id = R.dimen.padding_vertical_small)
    val paddingBottomMedium = dimensionResource(id = R.dimen.padding_bottom_medium)

    // Estados de puntuación
    var setsA by rememberSaveable { mutableIntStateOf(0) }
    var setsB by rememberSaveable { mutableIntStateOf(0) }

    var juegosA by rememberSaveable { mutableIntStateOf(0) }
    var juegosB by rememberSaveable { mutableIntStateOf(0) }

    var puntosIndexA by rememberSaveable { mutableIntStateOf(0) } // 0=0, 1=15, 2=30, 3=40
    var puntosIndexB by rememberSaveable { mutableIntStateOf(0) }

    var tieBreakA by rememberSaveable { mutableIntStateOf(0) }
    var tieBreakB by rememberSaveable { mutableIntStateOf(0) }

    var esTieBreak by rememberSaveable { mutableStateOf(false) }
    var partidoFinalizado by rememberSaveable { mutableStateOf(false) }

    val valoresPuntos = listOf("0", "15", "30", "40")
    //Sirve para ver los valores de los puntos y tener constancia de ellos
    fun resetearTodo() {
        setsA = 0
        setsB = 0
        juegosA = 0
        juegosB = 0
        puntosIndexA = 0
        puntosIndexB = 0
        tieBreakA = 0
        tieBreakB = 0
        esTieBreak = false
        partidoFinalizado = false
    }
    // Funcion para sumar puntos de juego al quipo y sus distintas posivilidades dentro de lo que puede pasar
    fun sumarJuego(equipoA: Boolean) {
        puntosIndexA = 0
        puntosIndexB = 0

        if (equipoA) juegosA++ else juegosB++

        if (esTieBreak) {
            // El tie-break decide el set directamente
            if (equipoA) setsA++ else setsB++
            juegosA = 0
            juegosB = 0
            tieBreakA = 0
            tieBreakB = 0
            esTieBreak = false
        } else {
            // Reglas de fin de set normal
            val difJuegos = kotlin.math.abs(juegosA - juegosB)
            if ((juegosA >= 6 || juegosB >= 6) && difJuegos >= 2) {
                if (juegosA > juegosB) setsA++ else setsB++
                juegosA = 0
                juegosB = 0
            } else if (juegosA == 6 && juegosB == 6) {
                esTieBreak = true
                tieBreakA = 0
                tieBreakB = 0
            }
        }

        if (setsA == 2 || setsB == 2) {
            partidoFinalizado = true
        }
    }
    // Funcion para anotar un punto al equipo
    fun anotarPunto(equipoA: Boolean) {
        if (partidoFinalizado) return

        if (esTieBreak) {
            if (equipoA) tieBreakA++ else tieBreakB++
            val difTieBreak = kotlin.math.abs(tieBreakA - tieBreakB)
            if ((tieBreakA >= 7 || tieBreakB >= 7) && difTieBreak >= 2) {
                sumarJuego(equipoA = tieBreakA > tieBreakB)
            }
        } else {
            // Juego Normal + Punto de Oro (empate a 40)
            if (equipoA) {
                if (puntosIndexA == 3) {
                    sumarJuego(equipoA = true)
                } else {
                    puntosIndexA++
                }
            } else {
                if (puntosIndexB == 3) {
                    sumarJuego(equipoA = false)
                } else {
                    puntosIndexB++
                }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- CABECERA SETS ---
        Text(
            text = stringResource(R.string.sets).uppercase(),
            fontSize = textSizeHeader,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.orange_header))
                .padding(vertical = headerPaddingVertical)
        )

        // --- FILA MARCADOR SETS ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$setsA",
                fontSize = textSizeScore,
                color = colorResource(id = R.color.blue_team)
            )
            Text(
                text = "$setsB",
                fontSize = textSizeScore,
                color = colorResource(id = R.color.green_team)
            )
        }

        // --- CABECERA JUEGOS ---
        Text(
            text = stringResource(R.string.juegos_padel).uppercase(),
            fontSize = textSizeHeader,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.orange_header))
                .padding(vertical = headerPaddingVertical)
        )

        // --- FILA MARCADOR JUEGOS ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$juegosA",
                fontSize = textSizeScore,
                color = colorResource(id = R.color.blue_team)
            )
            Text(
                text = "$juegosB",
                fontSize = textSizeScore,
                color = colorResource(id = R.color.green_team)
            )
        }

        // --- CABECERA SET ACTUAL ---
        Text(
            text = if (esTieBreak) "TIE-BREAK" else stringResource(R.string.set_actual).uppercase(),
            fontSize = textSizeHeader,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.orange_header))
                .padding(vertical = headerPaddingVertical)
        )

        // --- FILA MARCADOR SET ACTUAL ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (esTieBreak) "$tieBreakA" else valoresPuntos[puntosIndexA],
                fontSize = textSizeScore,
                color = colorResource(id = R.color.blue_team)
            )
            Text(
                text = if (esTieBreak) "$tieBreakB" else valoresPuntos[puntosIndexB],
                fontSize = textSizeScore,
                color = colorResource(id = R.color.green_team)
            )
        }

        // --- BOTONES EQUIPOS ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = paddingHorizontalMedium, vertical = paddingVerticalSmall),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { anotarPunto(equipoA = true) },
                enabled = !partidoFinalizado,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.blue_team)
                )
            ) {
                Text(
                    text = stringResource(R.string.puntos_a),
                    fontSize = textSizeButton,
                    color = colorResource(id = R.color.white)
                )
            }
            Button(
                onClick = { anotarPunto(equipoA = false) },
                enabled = !partidoFinalizado,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.green_team)
                )
            ) {
                Text(
                    text = stringResource(R.string.puntos_b),
                    fontSize = textSizeButton,
                    color = colorResource(id = R.color.white)
                )
            }
        }

        // --- BOTÓN RESETEAR ---
        Button(
            onClick = { resetearTodo() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.red_reset)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = paddingHorizontalMedium, end = paddingHorizontalMedium, bottom = paddingBottomMedium)
        ) {
            Text(
                text = stringResource(R.string.resetear),
                fontSize = textSizeButton,
                color = colorResource(id = R.color.white)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    PadelScoreLuciaTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Content(modifier = Modifier.padding(innerPadding))
        }
    }
}