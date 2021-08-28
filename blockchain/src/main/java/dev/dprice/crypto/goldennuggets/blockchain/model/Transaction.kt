package dev.dprice.crypto.goldennuggets.blockchain.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val sendersAddress: String,
    val recipientsAddress: String,
    val amount: Int
)
