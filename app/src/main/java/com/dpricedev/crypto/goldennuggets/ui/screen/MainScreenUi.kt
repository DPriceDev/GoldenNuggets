package com.dpricedev.crypto.goldennuggets.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dpricedev.crypto.goldennuggets.ui.theme.GoldenNuggetsTheme

object MainScreenUi {

    @Composable
    fun Layout() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Greeting("Android")
        }
    }

    @Composable
    private fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }
}

@Preview
@Composable
private fun PreviewMainScreenLayout() {
    GoldenNuggetsTheme {
        MainScreenUi.Layout()
    }
}