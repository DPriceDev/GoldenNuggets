package dev.dprice.crypto.goldennuggets.blockchain.di

import dev.dprice.crypto.goldennuggets.blockchain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BlockChainModule {

    @Binds
    abstract fun BlockMinerImpl.bindBlockMinerImpl() : BlockMiner

    @Binds
    abstract fun BlockProoferImpl.bindBlockProoferImpl() : BlockProofer

    @Binds
    abstract fun BlockHasherImpl.bindBlockHasherImpl() : BlockHasher
}