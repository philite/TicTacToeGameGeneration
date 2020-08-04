package com.philite.tictactoeGameGeneration

import com.philite.tictactoeGameGeneration.model.ItemData

object ArrayExtension {
    fun ArrayList<ItemData>.isCrossed(boardDimension: Int): Boolean =
        this.isNotEmpty() && this.isAllSame() && this.count() == boardDimension

    private fun ArrayList<ItemData>.isAllSame(): Boolean = this.distinct().count() == 1
}
