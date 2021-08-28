package dev.dprice.crypto.goldennuggets.blockchain.model

import kotlinx.serialization.Serializable

@Serializable
data class Block(
    val index: Int,
    val time: Long,
    val transactions: List<Transaction>,
    val proof: Int,
    val previousHash: String,
)
