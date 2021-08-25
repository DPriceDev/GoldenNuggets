package dev.dprice.crypto.goldennuggets.blockchain.usecase

import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import javax.inject.Inject

interface SaveBlockUseCase {
    fun saveBlock(block: Block)
}

class SaveBlockUseCaseImpl @Inject constructor() : SaveBlockUseCase {

    override fun saveBlock(block: Block) {
        // Save the block to current block repo
    }
}