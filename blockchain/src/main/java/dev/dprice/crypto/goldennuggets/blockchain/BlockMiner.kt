package dev.dprice.crypto.goldennuggets.blockchain

import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

interface BlockMiner {
    suspend fun mineBlock(block: Block, difficulty: Int) : Block
}

class BlockMinerImpl @Inject constructor(
    private val blockProofer: BlockProofer
) : BlockMiner {

    override suspend fun mineBlock(
        block: Block,
        difficulty: Int
    ) : Block {
        var currentProof = 0
        var workingBlock = block
        while(!blockProofer.isValid(workingBlock, difficulty)) {
            ++currentProof
            workingBlock = block.copy(proof = currentProof)
            coroutineContext.ensureActive()
        }
        return workingBlock
    }
}