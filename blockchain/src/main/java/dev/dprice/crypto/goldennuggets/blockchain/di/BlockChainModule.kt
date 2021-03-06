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
import dev.dprice.crypto.goldennuggets.blockchain.repository.BlockChainRepository
import dev.dprice.crypto.goldennuggets.blockchain.repository.BlockChainRepositoryImpl
import dev.dprice.crypto.goldennuggets.blockchain.usecase.*

@Module
@InstallIn(SingletonComponent::class)
abstract class BlockChainModule {

    @Binds
    abstract fun BlockChainRepositoryImpl.bindBlockChainRepositoryImpl() : BlockChainRepository

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

    @Binds
    abstract fun GetBlockChainUseCaseImpl.bindGetBlockChainUseCaseImpl() : GetBlockChainUseCase

    @Binds
    abstract fun SaveBlockUseCaseImpl.bindSaveBlockUseCaseImpl() : SaveBlockUseCase

    @Binds
    abstract fun UpdateBlockChainUseCaseImpl.bindUpdateBlockChainUseCaseImpl() : UpdateBlockChainUseCase

}