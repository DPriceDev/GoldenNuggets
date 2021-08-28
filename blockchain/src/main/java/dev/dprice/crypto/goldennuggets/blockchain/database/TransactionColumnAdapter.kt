package dev.dprice.crypto.goldennuggets.blockchain.database

import com.squareup.sqldelight.ColumnAdapter
import dev.dprice.crypto.goldennuggets.blockchain.model.Transaction
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class TransactionColumnAdapter : ColumnAdapter<List<Transaction>, String> {

    override fun decode(databaseValue: String): List<Transaction> {
        return Json.decodeFromString(
            ListSerializer(Transaction.serializer()),
            databaseValue
        )
    }

    override fun encode(value: List<Transaction>): String {
        return Json.encodeToString(
            ListSerializer(Transaction.serializer()),
            value
        )
    }
}