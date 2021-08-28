package dev.dprice.crypto.goldennuggets.blockchain.usecase

import dev.dprice.crypto.goldennuggets.blockchain.repository.BlockChainRepository
import dev.dprice.crypto.goldennuggets.blockchain.model.BlockChain
import javax.inject.Inject

interface UpdateBlockChainUseCase {
    suspend fun update(blockChain: BlockChain)
}

class UpdateBlockChainUseCaseImpl @Inject constructor(
    private val blockChainRepository: BlockChainRepository
) : UpdateBlockChainUseCase {

    override suspend fun update(blockChain: BlockChain) {
        blockChainRepository.addBlockChain(blockChain)
    }
}