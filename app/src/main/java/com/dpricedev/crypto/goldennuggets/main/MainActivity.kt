package com.dpricedev.crypto.goldennuggets.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dpricedev.crypto.goldennuggets.ui.screen.MainScreenUi
import com.dpricedev.crypto.goldennuggets.ui.theme.GoldenNuggetsTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.dprice.crypto.goldennuggets.blockchain.server.BlockChainServer
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var blockChainServer: BlockChainServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(this, BlockChainService::class.java).also { intent ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            }
        }

        setContent {
            GoldenNuggetsTheme {
                MainScreenUi.Layout()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoldenNuggetsTheme {
        MainScreenUi.Layout()
    }
}