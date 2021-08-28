package dev.dprice.crypto.goldennuggets.blockchain.di

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.dprice.crypto.goldennuggets.blockchain.Database
import dev.dprice.crypto.goldennuggets.blockchain.database.BlockChainItem
import dev.dprice.crypto.goldennuggets.blockchain.database.BlockColumnAdapter
import dev.dprice.crypto.goldennuggets.blockchain.database.TransactionColumnAdapter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BlockChainDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseDriver(@ApplicationContext context: Context) : SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "BlockChainDatabase.db")
    }

    @Provides
    @Singleton
    fun provideDatabase(driver: SqlDriver) : Database = Database(
        driver,
        BlockChainItem.Adapter(
            blocksAdapter = BlockColumnAdapter(),
            transactionsAdapter = TransactionColumnAdapter()
        )
    )

    @Provides
    @Singleton
    fun provideDatabaseQueries(database: Database) = database.blockChainQueries
}