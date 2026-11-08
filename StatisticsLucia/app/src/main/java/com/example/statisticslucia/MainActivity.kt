package com.example.statisticslucia

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.statisticslucia.ui.theme.StatisticsLuciaTheme

/*
Crea un proyecto en Android Studio llamado StatisticsTuNombre.
Esta aplicación permitirá llevar la cuenta de personas, patinetes, bicicletas
y coches que pasan por una calle mediante botones que permitirán sumar
uno, restar uno o reiniciar el contador.
La aplicación debe mostrar un título arriba del todo que ponga "Estadísticas TuNombre".
Además, se mostrará el total y un botón para reiniciar todos los contadores y también se deben
mostrar los porcentajes de cada uno de los elementos contados.
Igual que existe el componente Column, también existe el componente Row que permite
organizar en fila los elementos.
Para cambiar el color de un botón se puede usar la clase Color bien indicando una de sus
propiedades preestablecidas para color o bien con su constructor pasando el valor hexadecimal
(alpha, rojo, verde, azul):
Un color se puede almacenar en una variable si se quiere reutilizar.
Se puede usar todo lo visto en los apuntes de la unidad hasta este momento.
A continuación, se muestra un ejemplo de la interfaz de la aplicación. Tu aplicación no tiene que
ser idéntica a la interfaz de ejemplo.
 */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StatisticsLuciaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Composable principal de la pantalla de contenido.
 * Gestiona los estados de los contadores (personas, patinetes, bicicletas, coches),
 * calcula los totales y porcentajes, y organiza los componentes visuales.
 */
@Composable
fun Content(modifier: Modifier = Modifier) {
    // Estado de la aplicación retenido tras cambios de configuración (ej. rotación)
    var personas by rememberSaveable { mutableStateOf(0) }
    var patinetes by rememberSaveable { mutableStateOf(0) }
    var bicicletas by rememberSaveable { mutableStateOf(0) }
    var coches by rememberSaveable { mutableStateOf(0) }

    // Cálculo del total de elementos contados
    val totalFinal = personas + patinetes + bicicletas + coches

    // Cálculo del porcentaje relativo de cada elemento respecto al total
    val perEsta = if (totalFinal > 0) (personas.toFloat() / totalFinal) * 100 else 0f
    val patEsta = if (totalFinal > 0) (patinetes.toFloat() / totalFinal) * 100 else 0f
    val bicEsta = if (totalFinal > 0) (bicicletas.toFloat() / totalFinal) * 100 else 0f
    val coEsta = if (totalFinal > 0) (coches.toFloat() / totalFinal) * 100 else 0f

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la aplicación
        Text(
            text = stringResource(R.string.titulo_arriba),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Indicador numérico del total global
        Text(
            text = stringResource(R.string.indicador_total, totalFinal),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fila del contador para Personas
        CounterRow(
            label = stringResource(R.string.label_personas),
            count = personas,
            onIncrement = { personas++ },
            onDecrement = { if (personas > 0) personas-- },
            onReset = { personas = 0 }
        )

        // Fila del contador para Patinetes
        CounterRow(
            label = stringResource(R.string.label_patinetes),
            count = patinetes,
            onIncrement = { patinetes++ },
            onDecrement = { if (patinetes > 0) patinetes-- },
            onReset = { patinetes = 0 }
        )

        // Fila del contador para Bicicletas
        CounterRow(
            label = stringResource(R.string.label_bicicletas),
            count = bicicletas,
            onIncrement = { bicicletas++ },
            onDecrement = { if (bicicletas > 0) bicicletas-- },
            onReset = { bicicletas = 0 }
        )

        // Fila del contador para Coches
        CounterRow(
            label = stringResource(R.string.label_coches),
            count = coches,
            onIncrement = { coches++ },
            onDecrement = { if (coches > 0) coches-- },
            onReset = { coches = 0 }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para reiniciar todos los contadores simultáneamente
        Button(
            onClick = {
                personas = 0
                patinetes = 0
                bicicletas = 0
                coches = 0
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.btn_reset_all))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sección descriptiva de las estadísticas en porcentaje
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.title_estadisticas),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = stringResource(R.string.total_personas, perEsta))
            Text(text = stringResource(R.string.total_patinetes, patEsta))
            Text(text = stringResource(R.string.total_bicicletas, bicEsta))
            Text(text = stringResource(R.string.total_coches, coEsta))
        }
    }
}

/**
 * Función composable reutilizable para renderizar cada fila de contador individual.
 * Evita la duplicación de código proporcionando el valor actual y los botones
 * para incrementar, decrementar y reiniciar dicho elemento.
 *
 */
@Composable
fun CounterRow(
    label: String,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$count",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(0.5f)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(2.5f)
            ) {
                Button(onClick = onIncrement, modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.btn_suma))
                }
                Button(onClick = onDecrement, modifier = Modifier.weight(1f)) {
                    Text(stringResource(R.string.btn_resta))
                }
                Button(onClick = onReset, modifier = Modifier.weight(1.2f)) {
                    Text(stringResource(R.string.btn_reset))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StatisticsLuciaTheme {
        Content()
    }
}