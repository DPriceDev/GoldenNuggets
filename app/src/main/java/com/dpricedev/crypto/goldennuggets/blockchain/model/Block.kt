package com.dpricedev.crypto.goldennuggets.blockchain.model

data class Block(
    val index: Int,
    val time: Long,
    val transactions: List<Transaction>,
    val proof: Int,
    val previousHash: String,
)
