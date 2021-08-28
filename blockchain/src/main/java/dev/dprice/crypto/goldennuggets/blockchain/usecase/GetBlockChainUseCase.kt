package dev.dprice.crypto.goldennuggets.blockchain.usecase

import dev.dprice.crypto.goldennuggets.blockchain.repository.BlockChainRepository
import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import dev.dprice.crypto.goldennuggets.blockchain.model.BlockChain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import javax.inject.Inject

interface GetBlockChainUseCase {
    suspend fun getBlockChain(): Flow<BlockChain>
}

class GetBlockChainUseCaseImpl @Inject constructor(
    private val blockChainRepository: BlockChainRepository
) : GetBlockChainUseCase {

    override suspend fun getBlockChain(): Flow<BlockChain> {

        // create genesis block and chain if none exist
        if(blockChainRepository.getBlockChainCount() == 0) {
            blockChainRepository.addBlockChain(
                createGenesisBlockChain()
            )
        }

        // return flow of blockchain from repo
        return blockChainRepository.getAllBlockChains().map {
            BlockChain(
                it.first().blocks,
                it.first().transactions
            )
        }
    }

    private fun createGenesisBlockChain() = BlockChain(
        listOf(
            Block(
                0,
                Clock.System.now().toEpochMilliseconds(),
                listOf(),
                0,
                ""
            )
        ),
        listOf()
    )
}