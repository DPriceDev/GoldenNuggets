package com.dpricedev.crypto.goldennuggets.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpricedev.crypto.goldennuggets.blockchain.BlockHasher
import com.dpricedev.crypto.goldennuggets.blockchain.BlockMiner
import com.dpricedev.crypto.goldennuggets.blockchain.model.Block
import com.dpricedev.crypto.goldennuggets.blockchain.model.BlockChain
import com.dpricedev.crypto.goldennuggets.blockchain.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MainScreenViewModel {

}

@HiltViewModel
class MainScreenViewModelImpl @Inject constructor(
    private val blockMiner: BlockMiner,
    private val blockHasher: BlockHasher
) : ViewModel(), MainScreenViewModel {

    init {
        val transaction = Transaction(
            "",
            "",
            25
        )

        var block = Block(
            1,
            0, // todo
            listOf(
                transaction
            ),
            0,
            ""
        )

        val exceptionHandler = CoroutineExceptionHandler { context, ex ->
            Log.e("Davids Log", "Cancelled!")
        }

        val test = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val flow = flow {
                while (true) {
                    val minedBlock = blockMiner.mineBlock(block, 2)

                    block = minedBlock.copy(
                        index = block.index+1,
                        previousHash = blockHasher.hashBlock(minedBlock)
                    )

                    this.emit(minedBlock.proof)

                    ensureActive()
                }
            }

            flow.collect {
                Log.e("David Log", "proof = $it")
            }
        }

        viewModelScope.launch {
            delay(1000)
//            test.cancel()
        }
    }
}