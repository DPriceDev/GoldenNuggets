package dev.dprice.crypto.goldennuggets.blockchain.usecase

import dev.dprice.crypto.goldennuggets.blockchain.model.BlockChain
import javax.inject.Inject

interface UpdateBlockChainUseCase {
    fun update(blockChain: BlockChain)
}

class UpdateBlockChainUseCaseImpl @Inject constructor(

) : UpdateBlockChainUseCase {

    override fun update(blockChain: BlockChain) {

        // override block chain in repo
    }
}