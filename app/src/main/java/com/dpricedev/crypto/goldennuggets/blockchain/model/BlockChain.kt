package com.dpricedev.crypto.goldennuggets.blockchain.model

data class BlockChain(
    val blocks: List<Block>,
    val transactions: List<Transaction> = listOf()
)