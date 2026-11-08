package com.example.contadordeclics.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.contadordeclics.ui.theme.ContadorDeClicsTheme

@Composable
fun MyAppContent(content: @Composable (PaddingValues) -> Unit){
    ContadorDeClicsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            content(innerPadding)
        }
    }
}