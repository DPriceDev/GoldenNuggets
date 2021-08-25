package com.dpricedev.crypto.goldennuggets.main

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import dev.dprice.crypto.goldennuggets.blockchain.BlockHasher
import dev.dprice.crypto.goldennuggets.blockchain.BlockMiner
import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import dev.dprice.crypto.goldennuggets.blockchain.model.Transaction
import com.dpricedev.crypto.goldennuggets.util.ForegroundNotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.dprice.crypto.goldennuggets.blockchain.server.BlockChainServer
import dev.dprice.crypto.goldennuggets.blockchain.usecase.MineUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@AndroidEntryPoint
class BlockChainService : Service() {

    inner class BlockChainBinder : Binder() {
        fun getService() : BlockChainService {
            return this@BlockChainService
        }
    }

    @Inject
    @ApplicationContext
    lateinit var context: Context

    @Inject
    lateinit var foregroundNotificationHelper: ForegroundNotificationHelper

    @Inject
    lateinit var blockChainServer: BlockChainServer

    @Inject
    lateinit var mineUseCase: MineUseCase

    private val blockChainBinder: IBinder = BlockChainBinder()

    private val pendingIntent by lazy {
        PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private var miningJob: Job? = null

    override fun onBind(intent: Intent?): IBinder = blockChainBinder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notification = foregroundNotificationHelper.getNotification(
            pendingIntent,
            "Service Started"
        )

        startForeground(NOTIFICATION_ID, notification)

        blockChainServer.startServer()

        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        blockChainServer.stopServer()
    }

    fun startMining() {
        foregroundNotificationHelper.updateNotificationMessage(
            NOTIFICATION_ID,
            pendingIntent,
            "Mining!"
        )
        miningJob = mineUseCase.mine()
    }

    fun stopMining() {
        foregroundNotificationHelper.updateNotificationMessage(
            NOTIFICATION_ID,
            pendingIntent,
            "Not Mining!"
        )
        miningJob?.cancel()
        miningJob = null
    }

    companion object {
        private const val NOTIFICATION_ID = 123
    }
}
