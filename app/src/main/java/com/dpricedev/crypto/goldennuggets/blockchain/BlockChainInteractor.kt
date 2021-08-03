package com.dpricedev.crypto.goldennuggets.blockchain

import com.dpricedev.crypto.goldennuggets.blockchain.model.Block
import com.dpricedev.crypto.goldennuggets.blockchain.model.BlockChain
import javax.inject.Inject

interface BlockChainInteractor {
    fun createBlockChain() : BlockChain
    fun createBlock() : Block

    fun addBlock(blockChain: BlockChain, block: Block) : BlockChain
}

class BlockChainInteractorImpl @Inject constructor() : BlockChainInteractor {

    override fun createBlockChain() = BlockChain(
        listOf(
            createBlock()
        )
    )

    override fun createBlock(): Block {
        TODO("Not yet implemented")
    }

    override fun addBlock(blockChain: BlockChain, block: Block): BlockChain {
        TODO("Not yet implemented")
    }
}

fun test() {
    val interactor: BlockChainInteractor = BlockChainInteractorImpl()

    val chain = interactor.createBlockChain()

    val block = interactor.createBlock()

    interactor.addBlock(chain, block)
}