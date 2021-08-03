package com.dpricedev.crypto.goldennuggets.blockchain

import javax.inject.Inject

interface BlockProofer {
    fun isValid(
        lastProof: Int,
        proposedProof: Int,
        difficulty: Int
    ) : Boolean
}

class BlockProoferImpl @Inject constructor() : BlockProofer {

    override fun isValid(
        lastProof: Int,
        proposedProof: Int,
        difficulty: Int
    ) : Boolean {
        TODO("Not yet implemented")
    }
}