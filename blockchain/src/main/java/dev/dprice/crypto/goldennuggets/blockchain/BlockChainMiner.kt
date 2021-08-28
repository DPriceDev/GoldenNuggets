package dev.dprice.crypto.goldennuggets.blockchain

import android.util.Log
import dev.dprice.crypto.goldennuggets.blockchain.domain.BlockHasher
import dev.dprice.crypto.goldennuggets.blockchain.domain.BlockMiner
import javax.inject.Inject
import dev.dprice.crypto.goldennuggets.blockchain.model.*
import dev.dprice.crypto.goldennuggets.blockchain.usecase.GetBlockChainUseCase
import dev.dprice.crypto.goldennuggets.blockchain.usecase.SaveBlockUseCase
import dev.dprice.crypto.goldennuggets.blockchain.usecase.UpdateBlockChainUseCase
import dev.dprice.crypto.goldennuggets.core.di.Computation
import dev.dprice.crypto.goldennuggets.core.di.IO
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
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
    private val updateBlockChainUseCase: UpdateBlockChainUseCase,
    @Computation private val computationDispatcher: CoroutineDispatcher
) : BlockChainMiner {

    private var miningJob: Job? = null

    override fun mine() : Job = CoroutineScope(computationDispatcher).launch {
        getBlockChainUseCase.getBlockChain().collect { blockChain ->
            miningJob?.cancel()
            miningJob = CoroutineScope(computationDispatcher).launch {
                // create new transaction to me
                val transaction = createMiningTransaction(ADDRESS)

                // create new block
                val newBlock = createMiningBlock(blockChain, transaction)

                // save current block being mined
                // todo: is needed?
                saveBlockUseCase.saveBlock(newBlock)

                // mine block
                val minedBlock = blockMiner.mineBlock(newBlock, MINING_DIFFICULTY)

                val newBlockChain = blockChain.copy(
                    blocks = blockChain.blocks.plus(minedBlock)
                )

                // update blockchain with new block and chain
                Log.e("Davids Log", "mined blockchain: $newBlockChain")

                updateBlockChainUseCase.update(newBlockChain)
            }
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

    companion object {
        // todo: Make variable against time to mine a block i.e. every 10 seconds?
        private const val MINING_DIFFICULTY = 3
        // todo: Make generatable - save in datastore?
        private const val ADDRESS = "123123"
    }
}