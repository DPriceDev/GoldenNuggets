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

        val new = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            while (true) {
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
                    updateBlockChainUseCase.update(newBlockChain)
                }

                ensureActive()
            }
        }


//        val test = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
//            val myFlow = flow<Int> {
//                while (true) {
//                    val minedBlock = blockMiner.mineBlock(block, 2)
//
//                    block = minedBlock.copy(
//                        index = block.index + 1,
//                        previousHash = blockHasher.hashBlock(minedBlock)
//                    )
//                    emit(minedBlock.proof)
//                    ensureActive()
//                }
//            }
//
//            myFlow.collect {
//                Log.e("David Log", "proof = $it")
//            }
//        }

        return new
    }

    private fun createMiningTransaction(address: String) = Transaction(
        "",
        address,
        25
    )

    private fun createMiningBlock(
        blockChain: BlockChain,
        transaction: Transaction
    ) : Block {
        val previousHash = blockHasher.hashBlock(blockChain.blocks.last())

        return Block(
            blockChain.blocks.size + 1,
            0, // todo
            listOf(
                transaction
            ),
            0,
            previousHash
        )
    }
}