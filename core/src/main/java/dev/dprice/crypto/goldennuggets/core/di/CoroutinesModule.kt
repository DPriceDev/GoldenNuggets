package dev.dprice.crypto.goldennuggets.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class UI

@Qualifier
annotation class IO

@Qualifier
annotation class Computation

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @UI
    @Singleton
    @Provides
    fun provideUiDispatcher() : CoroutineDispatcher = Dispatchers.Main

    @IO
    @Singleton
    @Provides
    fun provideIoDispatcher() : CoroutineDispatcher = Dispatchers.IO

    @Computation
    @Singleton
    @Provides
    fun provideComputationDispatcher() : CoroutineDispatcher = Dispatchers.Default

    @UI
    @Singleton
    @Provides
    fun provideUiScope(@UI dispatcher: CoroutineDispatcher) : CoroutineScope = CoroutineScope(dispatcher)

    @UI
    @Singleton
    @Provides
    fun provideIoScope(@IO dispatcher: CoroutineDispatcher) : CoroutineScope = CoroutineScope(dispatcher)

    @UI
    @Singleton
    @Provides
    fun provideComputationScope(@Computation dispatcher: CoroutineDispatcher) : CoroutineScope = CoroutineScope(dispatcher)
}