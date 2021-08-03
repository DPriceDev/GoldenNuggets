package com.dpricedev.crypto.goldennuggets.blockchain

import com.dpricedev.crypto.goldennuggets.blockchain.model.Block
import com.dpricedev.crypto.goldennuggets.blockchain.model.BlockChain
import com.dpricedev.crypto.goldennuggets.blockchain.model.Transaction
import javax.inject.Inject

interface BlockChainInteractor {
    fun createBlockChain() : BlockChain

    fun createBlock(
        blockChain: BlockChain,
        proof: Int
    ) : Block

    fun addBlock(
        blockChain: BlockChain,
        block: Block
    ) : BlockChain

    fun addTransactionToChain(
        blockChain: BlockChain,
        transaction: Transaction
    ) : BlockChain
}

class BlockChainInteractorImpl @Inject constructor(
    private val blockHasher: BlockHasher
) : BlockChainInteractor {

    override fun createBlockChain() = BlockChain(
        listOf(
            Block(
                1,
                0,  // todo: Time now
                listOf(),
                0,
                ""
            )
        )
    )

    override fun createBlock(
        blockChain: BlockChain,
        proof: Int
    ): Block {
        val time: Long = 0 // todo: Add time
        val proof: Int = 0 // todo: Compute proof
        val lastHash: String = blockChain.blocks
            .lastOrNull()
            ?.let { blockHasher.hashBlock(it) } ?: ""

        return Block(
            blockChain.blocks.size + 1,
            time,
            blockChain.transactions,
            proof,
            lastHash
        )
    }

    override fun addBlock(
        blockChain: BlockChain,
        block: Block
    ): BlockChain {
        return blockChain.copy(
            blocks = blockChain.blocks.plus(block)
        )
    }

    override fun addTransactionToChain(
        blockChain: BlockChain,
        transaction: Transaction
    ): BlockChain {
        return blockChain.copy(
            transactions = blockChain.transactions.plus(transaction)
        )
    }
}