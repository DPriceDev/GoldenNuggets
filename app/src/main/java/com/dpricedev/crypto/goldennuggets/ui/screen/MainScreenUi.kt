package com.dpricedev.crypto.goldennuggets.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.dpricedev.crypto.goldennuggets.ui.theme.GoldenNuggetsTheme

object MainScreenUi {

    @Composable
    fun Layout(
        viewModel: MainScreenViewModel = hiltViewModel<MainScreenViewModelImpl>(),
        onServerStateChanged: (Boolean) -> Unit = { },
        onMiningStateChanged: (Boolean) -> Unit = { },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val checkedState = remember { mutableStateOf(false) }
            val mineCheckedState = remember { mutableStateOf(false) }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Greeting("Android")

                Switch(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        onServerStateChanged(it)
                    }
                )

                Switch(
                    checked = mineCheckedState.value,
                    onCheckedChange = {
                        mineCheckedState.value = it
                        onMiningStateChanged(it)
                    }
                )
            }

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
    val testViewModel = object: MainScreenViewModel { }

    GoldenNuggetsTheme {
        MainScreenUi.Layout(testViewModel)
    }
}