package io.inscrib.inscribio

import java.util.ArrayList
import java.util.HashMap

object PlayerList {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<Player> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
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

    /**
     * A dummy item representing a piece of content.
     */
    data class Player(val id: Int, var score: Int) {
        override fun toString(): String = score.toString()
    }
}
