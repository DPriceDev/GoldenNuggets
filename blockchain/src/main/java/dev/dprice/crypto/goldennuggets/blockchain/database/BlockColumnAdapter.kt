package dev.dprice.crypto.goldennuggets.blockchain.database

import com.squareup.sqldelight.ColumnAdapter
import dev.dprice.crypto.goldennuggets.blockchain.model.Block
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class BlockColumnAdapter : ColumnAdapter<List<Block>, String> {

    override fun decode(databaseValue: String): List<Block> {
        return Json.decodeFromString(
            ListSerializer(Block.serializer()),
            databaseValue
        )
    }

    override fun encode(value: List<Block>): String {
        return Json.encodeToString(
            ListSerializer(Block.serializer()),
            value
        )
    }
}