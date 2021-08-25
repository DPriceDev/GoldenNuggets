package dev.dprice.crypto.goldennuggets.blockchain

import android.util.Log
import dev.dprice.crypto.goldennuggets.blockchain.domain.BlockHasher
import dev.dprice.crypto.goldennuggets.blockchain.domain.BlockMiner
import javax.inject.Inject
import dev.dprice.crypto.goldennuggets.blockchain.model.*
import dev.dprice.crypto.goldennuggets.blockchain.usecase.GetBlockChainUseCase
import dev.dprice.crypto.goldennuggets.blockchain.usecase.SaveBlockUseCase
import dev.dprice.crypto.goldennuggets.blockchain.usecase.UpdateBlockChainUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat

interface BlockChainMiner {
    fun mine() : Job
}

class BlockChainMinerImpl @Inject constructor(
    private val blockMiner: BlockMiner,
    private val blockHasher: BlockHasher,
    private val getBlockChainUseCase: GetBlockChainUseCase,
    private val saveBlockUseCase: SaveBlockUseCase,
    private val updateBlockChainUseCase: UpdateBlockChainUseCase
) : BlockChainMiner {

    private val exceptionHandler = CoroutineExceptionHandler { context, ex ->
        Log.e("Davids Log", "Cancelled!")
    }

    override fun mine() : Job {
        val address = "123123"

        return CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            getBlockChainUseCase.getBlockChain().collect { blockChain ->

                // create new transaction to me
                val transaction = createMiningTransaction(address)

                // create new block
                val newBlock = createMiningBlock(blockChain, transaction)

                // save current block being mined
                saveBlockUseCase.saveBlock(newBlock)

                // mine block
                val minedBlock = blockMiner.mineBlock(newBlock, 2)

                val newBlockChain = blockChain.copy(
                    blocks = blockChain.blocks.plus(minedBlock)
                )

                // update blockchain with new block and chain
                Log.e("Davids Log", "mined blockchain: $newBlockChain")
                updateBlockChainUseCase.update(newBlockChain)
            }
            ensureActive()
        }
    }

    private fun createMiningTransaction(address: String) = Transaction(
        "",
        address,
        1
    )

    private fun createMiningBlock(
        blockChain: BlockChain,
        transaction: Transaction
    ) : Block {
        val previousHash = blockHasher.hashBlock(blockChain.blocks.last())
        val time = Clock.System.now().toEpochMilliseconds()

        return Block(
            blockChain.blocks.size,
            time,
            listOf(
                transaction
            ),
            0,
            previousHash
        )
    }
}