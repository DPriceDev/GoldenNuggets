package com.dpricedev.crypto.goldennuggets.blockchain

import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import dev.dprice.crypto.goldennuggets.blockchain.model.Transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BlockHasherTest {

    /* Variables */
    private lateinit var sut: BlockHasher
    private val testTransaction = Transaction(
        "sdfsadfsdf",
        "hgfhbfgbdfv",
        1
    )

    private val testBlock = Block(
        2,
        43234231,
        listOf(
            testTransaction,
            testTransaction
        ),
        5,
        TEST_HASH
    )

    /* Setup */
    @BeforeEach
    fun setup() {
        sut = BlockHasherImpl()
    }

    /* Tests */
    @Test
    fun `test Block is hashed correctly`() {
        val expected = "f0f2813594f12f2402c696243c822ce88835db4a4f0159d52aac27c4bd9c559a"
        val outputHash = sut.hashBlock(testBlock)
        assertEquals(outputHash, expected)
    }

    companion object {
        private const val TEST_HASH = "95a9cab5b19a0728fbf6827ad89ef23bc7a2e20eb44d057c9b0f895dc4cd60b1"
    }
}