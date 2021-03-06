package com.dpricedev.crypto.goldennuggets.main.di

import android.app.NotificationManager
import android.content.Context
import com.dpricedev.crypto.goldennuggets.util.ForegroundNotificationHelper
import com.dpricedev.crypto.goldennuggets.util.ForegroundNotificationHelperImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BlockChainServiceModule {

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context) : NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BlockChainServiceBindModule {

    @Binds
    abstract fun ForegroundNotificationHelperImpl.bindForegroundNotificationHelper() : ForegroundNotificationHelper
}