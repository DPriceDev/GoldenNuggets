package com.dpricedev.crypto.goldennuggets.blockchain.model

data class Transaction(
    val sendersAddress: String,
    val recipientsAddress: String,
    val amount: Int
)
