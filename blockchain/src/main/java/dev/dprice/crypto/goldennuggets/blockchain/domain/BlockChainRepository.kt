package dev.dprice.crypto.goldennuggets.blockchain.domain

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dev.dprice.crypto.goldennuggets.blockchain.database.BlockChainItem
import dev.dprice.crypto.goldennuggets.blockchain.database.BlockChainQueries
import dev.dprice.crypto.goldennuggets.blockchain.model.BlockChain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface BlockChainRepository {
    suspend fun getAllBlockChains() : Flow<List<BlockChainItem>>

    suspend fun getBlockChainCount() : Int

    suspend fun addBlockChain(blockChain: BlockChain)
}

class BlockChainRepositoryImpl @Inject constructor(
    private val blockChainQueries: BlockChainQueries
) : BlockChainRepository {

    override suspend fun getAllBlockChains(): Flow<List<BlockChainItem>> {
        return blockChainQueries.selectAll()
            .asFlow()
            .mapToList()
    }

    override suspend fun getBlockChainCount(): Int {
        return blockChainQueries.selectAll().executeAsList().size
    }

    override suspend fun addBlockChain(blockChain: BlockChain) {
        blockChainQueries.addBlockChain(
            0,
            blockChain.blocks,
            blockChain.transactions
        )
    }
}