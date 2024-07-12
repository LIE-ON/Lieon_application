package com.example.lieon.result.model

import com.example.lieon.record.db.RecordHistoryEntity
import java.util.ArrayList
import java.util.Date
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
class RecordResults {
    val ITEMS: MutableList<RecordHistoryEntity> = ArrayList()

    val ITEM_MAP: MutableMap<Int, RecordHistoryEntity> = HashMap()


    init {
        // Add some sample items.
//        for (i in 1..COUNT) {
//            addItem(createPlaceholderItem(i))
//        }
    }

    fun replaceItem(data : List<RecordHistoryEntity>){
        data.forEach {
            ITEMS.add(it)
            ITEM_MAP.put(it.id, it)
        }
    }

    fun getItems() = ITEMS

    fun getItem(position: Int) : RecordHistoryEntity
        = ITEM_MAP.get(position)!!

    fun deleteItem(position: Int){
        ITEM_MAP.remove(position)
        ITEMS.removeAt(position)
    }

    fun addItem(item: RecordHistoryEntity) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

//    private fun createPlaceholderItem(position: Int): RecordHistoryEntity {
////        return RecordHistoryEntity(position, "Item " + position, Date(System.currentTimeMillis()), "70%","/test")
//    }

}