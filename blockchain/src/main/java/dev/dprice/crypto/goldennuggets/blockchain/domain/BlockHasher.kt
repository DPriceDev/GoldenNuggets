package dev.dprice.crypto.goldennuggets.blockchain.domain

import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import java.security.MessageDigest
import javax.inject.Inject

interface BlockHasher {
    fun hashBlock(block: Block) : String
}

class BlockHasherImpl @Inject constructor() : BlockHasher {

    override fun hashBlock(block: Block): String = MessageDigest
        .getInstance(SHA_256_ALGORITHM)
        .digest(block.toString().toByteArray())
        .fold("") { current, it ->
            current + "%02x".format(it)
        }


    companion object {
        private const val SHA_256_ALGORITHM = "SHA-256"
    }
}