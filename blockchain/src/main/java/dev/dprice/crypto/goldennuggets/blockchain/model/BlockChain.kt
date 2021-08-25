package dev.dprice.crypto.goldennuggets.blockchain.model

data class BlockChain(
    val blocks: List<Block>,
    val transactions: List<Transaction> = listOf()
)