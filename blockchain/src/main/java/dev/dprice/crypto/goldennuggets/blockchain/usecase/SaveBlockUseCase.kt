package dev.dprice.crypto.goldennuggets.blockchain.usecase

import dev.dprice.crypto.goldennuggets.blockchain.model.Block

interface SaveBlockUseCase {
    fun saveBlock(block: Block)
}