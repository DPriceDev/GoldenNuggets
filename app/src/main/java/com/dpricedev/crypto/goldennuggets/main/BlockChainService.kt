package com.dpricedev.crypto.goldennuggets.main

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import dev.dprice.crypto.goldennuggets.blockchain.BlockHasher
import dev.dprice.crypto.goldennuggets.blockchain.BlockMiner
import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import dev.dprice.crypto.goldennuggets.blockchain.model.Transaction
import com.dpricedev.crypto.goldennuggets.util.ForegroundNotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@AndroidEntryPoint
class BlockChainService : Service() {

    @Inject
    @ApplicationContext
    lateinit var context: Context

    @Inject
    lateinit var foregroundNotificationHelper: ForegroundNotificationHelper

    @Inject
    lateinit var blockMiner: BlockMiner

    @Inject
    lateinit var blockHasher: BlockHasher

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = foregroundNotificationHelper.getNotification(pendingIntent)

        startForeground(NOTIFICATION_ID, notification)

        mine()

        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun mine() {
        val transaction = Transaction(
            "",
            "",
            25
        )

        var block = Block(
            1,
            0, // todo
            listOf(
                transaction
            ),
            0,
            ""
        )

        val exceptionHandler = CoroutineExceptionHandler { context, ex ->
            Log.e("Davids Log", "Cancelled!")
        }

        val test = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val myFlow = flow<Int> {
                while (true) {
                    val minedBlock = blockMiner.mineBlock(block, 2)

                    block = minedBlock.copy(
                        index = block.index+1,
                        previousHash = blockHasher.hashBlock(minedBlock)
                    )

                    this.emit(minedBlock.proof)

                    ensureActive()
                }
            }

            myFlow.collect {
                Log.e("David Log", "proof = $it")
            }
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 123
    }
}