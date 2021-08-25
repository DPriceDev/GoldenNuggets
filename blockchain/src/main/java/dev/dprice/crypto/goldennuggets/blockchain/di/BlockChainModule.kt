package dev.dprice.crypto.goldennuggets.blockchain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dprice.crypto.goldennuggets.blockchain.BlockChainServer
import dev.dprice.crypto.goldennuggets.blockchain.BlockChainServerImpl
import dev.dprice.crypto.goldennuggets.blockchain.BlockChainMiner
import dev.dprice.crypto.goldennuggets.blockchain.BlockChainMinerImpl
import dev.dprice.crypto.goldennuggets.blockchain.domain.*

@Module
@InstallIn(SingletonComponent::class)
abstract class BlockChainModule {

    @Binds
    abstract fun BlockMinerImpl.bindBlockMinerImpl() : BlockMiner

    @Binds
    abstract fun BlockProoferImpl.bindBlockProoferImpl() : BlockProofer

    @Binds
    abstract fun BlockHasherImpl.bindBlockHasherImpl() : BlockHasher

    @Binds
    abstract fun BlockChainServerImpl.bindBlockChainServerImpl() : BlockChainServer

    @Binds
    abstract fun BlockChainMinerImpl.bindMineUseCaseImpl() : BlockChainMiner
}