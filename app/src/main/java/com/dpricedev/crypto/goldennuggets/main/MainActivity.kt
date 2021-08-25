package com.dpricedev.crypto.goldennuggets.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dpricedev.crypto.goldennuggets.ui.screen.MainScreenUi
import com.dpricedev.crypto.goldennuggets.ui.theme.GoldenNuggetsTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import dev.dprice.crypto.goldennuggets.blockchain.server.BlockChainServer
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private inner class BlockChainServiceConnection : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BlockChainService.BlockChainBinder
            blockChainService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            blockChainService = null
        }
    }

    private val serviceIntent by lazy { Intent(this, BlockChainService::class.java) }

    private var blockChainService: BlockChainService? = null
    private var connection: BlockChainServiceConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GoldenNuggetsTheme {
                MainScreenUi.Layout(
                    onServerStateChanged = {
                        if (it) startBlockChainService() else stopBlockChainService()
                    },
                    onMiningStateChanged = {
                        if (it) startMining() else stopMining()
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unBindFromService()
    }

    private fun startMining() {
        blockChainService?.startMining()
    }

    private fun stopMining() {
        blockChainService?.stopMining()
    }

    private fun startBlockChainService() {
        serviceIntent.also { intent ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
                bindToService(intent)
            }
        }
    }

    private fun stopBlockChainService() {
        unBindFromService()
        stopService(serviceIntent)
    }

    private fun bindToService(intent: Intent) {
        connection = BlockChainServiceConnection().also {
            bindService(intent, it, Context.BIND_AUTO_CREATE)
        }
    }

    private fun unBindFromService() {
        blockChainService = null
        connection?.let { unbindService(it) }
        connection = null
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoldenNuggetsTheme {
        MainScreenUi.Layout()
    }
}