package dev.dprice.crypto.goldennuggets.blockchain.domain

import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import kotlin.test.assertEquals

class BlockProoferTest {

    /* Variables */
    private lateinit var sut: BlockProofer

    private val testBlock: Block = Block(
        1,
        0,
        listOf(),
        0,
        ""
    )

    /* Mock Variables */
    private val mockBlockHasher: BlockHasher = mock {
        on { hashBlock(any()) } doReturn TEST_HASH
    }

    /* Setup */
    @BeforeEach
    fun setup() {
        sut = BlockProoferImpl(mockBlockHasher)
    }

    /* Tests */
    @ParameterizedTest
    @MethodSource("difficulty")
    fun `test Block Proof works correctly for all diff`(difficulty: Int, expected: Boolean) {
        val isValid = sut.isValid(testBlock, difficulty)
        assertEquals(expected, isValid)
    }

    companion object {
        private const val TEST_HASH = "00000005b19a0728fbf6827ad89ef23bc7a2e20eb44d057c9b0f895dc4cd60b1"

        @JvmStatic
        fun difficulty() = (0..256).toList().map {
            Arguments.of(it, (it <= 7))
        }
    }
}