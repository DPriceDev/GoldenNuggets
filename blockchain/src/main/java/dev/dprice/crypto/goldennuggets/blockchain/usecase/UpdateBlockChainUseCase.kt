package dev.dprice.crypto.goldennuggets.blockchain.usecase

import dev.dprice.crypto.goldennuggets.blockchain.model.BlockChain

interface UpdateBlockChainUseCase {
    fun update(blockChain: BlockChain)
}