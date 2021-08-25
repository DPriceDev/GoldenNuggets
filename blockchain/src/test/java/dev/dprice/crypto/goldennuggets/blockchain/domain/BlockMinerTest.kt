package dev.dprice.crypto.goldennuggets.blockchain.domain

import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import kotlin.test.assertEquals

class BlockMinerTest {

    /* Variables */
    private lateinit var sut: BlockMiner

    private val testBlock: Block = Block(
        1,
        0,
        listOf(),
        0,
        ""
    )

    /* Mock Variables */
    private val mockBlockProofer: BlockProofer = mock {
        var count = 0

        on { isValid(any(), any()) } doAnswer {
            count++ == 12
        }
    }

    /* Setup */
    @BeforeEach
    fun setup() {
        sut = BlockMinerImpl(mockBlockProofer)
    }

    /* Tests */
    @Test
    @ExperimentalCoroutinesApi
    fun `test Block miner will mine for 12 cycles before finding and returning the block`() = runBlockingTest {
        val proofedBlock = sut.mineBlock(testBlock, TEST_DIFFICULTY)
        assertEquals(12, proofedBlock.proof)
    }

    companion object {
        private const val TEST_DIFFICULTY = 7
    }
}