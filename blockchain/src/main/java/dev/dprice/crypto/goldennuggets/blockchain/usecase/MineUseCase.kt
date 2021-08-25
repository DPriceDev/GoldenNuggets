package dev.dprice.crypto.goldennuggets.blockchain.usecase

import android.util.Log
import javax.inject.Inject
import dev.dprice.crypto.goldennuggets.blockchain.model.*
import dev.dprice.crypto.goldennuggets.blockchain.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

interface MineUseCase {
    fun mine() : Job
}

class MineUseCaseImpl @Inject constructor(
    private val blockMiner: BlockMiner,
    private val blockHasher: BlockHasher
) : MineUseCase {

    override fun mine() : Job {
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

        return test
    }
}