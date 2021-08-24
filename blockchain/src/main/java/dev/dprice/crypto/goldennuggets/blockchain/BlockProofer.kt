package dev.dprice.crypto.goldennuggets.blockchain

import android.util.Log
import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import javax.inject.Inject

interface BlockProofer {
    fun isValid(
        block: Block,
        difficulty: Int
    ) : Boolean
}

class BlockProoferImpl @Inject constructor(
    private val blockHasher: BlockHasher
) : BlockProofer {

    override fun isValid(
        block: Block,
        difficulty: Int
    ) : Boolean {
        return blockHasher.hashBlock(block)
            .take(difficulty)
            .all { it == '0' }
    }
}