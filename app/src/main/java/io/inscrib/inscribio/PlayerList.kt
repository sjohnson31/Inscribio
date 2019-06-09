package io.inscrib.inscribio

import java.util.ArrayList
import java.util.HashMap

object PlayerList {

    val ITEMS: MutableList<Player> = ArrayList()
    private val ITEM_MAP: MutableMap<Int, Player> = HashMap()

    private const val COUNT = 5

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: Player) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createDummyItem(position: Int): Player {
        return Player(position, 0)
    }

    data class Player(val id: Int, var score: Int)
}
