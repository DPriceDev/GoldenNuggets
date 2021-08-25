package dev.dprice.crypto.goldennuggets.blockchain.usecase

import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import dev.dprice.crypto.goldennuggets.blockchain.model.BlockChain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import javax.inject.Inject

interface GetBlockChainUseCase {
    suspend fun getBlockChain(): Flow<BlockChain>
}

class GetBlockChainUseCaseImpl @Inject constructor() : GetBlockChainUseCase {

    override suspend fun getBlockChain(): Flow<BlockChain> {

        // check if blockchain exists in rep

        // if does not exist, create a new one

        // save new blockchain to repo

        // return flow of blockchain from repo

        return flow {
            emit(
                BlockChain(
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
            )
        }
    }
}