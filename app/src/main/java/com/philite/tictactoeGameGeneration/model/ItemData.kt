package com.philite.tictactoeGameGeneration.model

data class ItemData(
    var clicked: Boolean = false,
    var state: TicTacToeState = TicTacToeState.NONE
)
