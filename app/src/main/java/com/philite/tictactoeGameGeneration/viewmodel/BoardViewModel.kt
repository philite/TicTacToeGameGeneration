package com.philite.tictactoeGameGeneration.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.philite.tictactoeGameGeneration.ArrayExtension.isCrossed
import com.philite.tictactoeGameGeneration.model.*
import timber.log.Timber

class BoardViewModel(private val boardDimension: Int) : ViewModel() {
    val gameUiLiveData = MutableLiveData<GameUi>()
    val gameResultLiveData = MutableLiveData<String>()
    val boardItemList = arrayListOf<ItemData>()

    private val boardSize = boardDimension * boardDimension
    private var turn: Int = 1
    private var player: Int = 0
    private val leastTurnsToWin: Int = boardDimension * 2 - 1
    private val drawTurns: Int = boardSize - 1

    init {
        createBoard()
        gameUiUpdate()
    }

    private fun createBoard() {
        if (boardDimension > 1) {
            for (i in 0 until boardSize) {
                boardItemList.add(ItemData())
            }
        }
    }

    private fun gameUiUpdate() {
        gameUiLiveData.postValue(createUiModel(getCurrentPlayer().text, turn.toString()))
    }

    private fun createUiModel(
        playerText: String, turnText: String): GameUi {
        return GameUi(
            player = "Player Turn: $playerText",
            turn = "Turn: $turnText")
    }

    fun nextTurn() {
        turn += 1
        player += 1
        gameUiUpdate()
    }

    fun getCurrentPlayer(): Player {
        return if (player % 2 == 0) {
            Player.ONE
        } else {
            Player.TWO
        }
    }

    fun checkResult() {
        if (turn >= leastTurnsToWin) {
            verticalCheck()
            horizontalCheck()
            diagonalCheck()
        }
        if (turn >= drawTurns) {
            emitResult(GameResult.DRAW)
        }
    }

    private fun verticalCheck() {
        val itemInColumn = arrayListOf<ItemData>()
        for (column in 0 until boardDimension) {
            for (item in 0 until boardDimension) {
                if (boardItemList[item * boardDimension + column].state != TicTacToeState.NONE) {
                    itemInColumn.add(boardItemList[item * boardDimension + column])
                }
            }
            if (itemInColumn.isCrossed(boardDimension)) {
                Timber.d("Player " + getCurrentPlayer().text + " win!")
                emitResult(GameResult.WIN)
                break
            } else {
                itemInColumn.clear()
            }
        }
    }

    private fun emitResult(result: GameResult) {
        gameResultLiveData.postValue("Result: " + getCurrentPlayer().text + " " + result.text)
    }

    private fun horizontalCheck() {
        val itemInRow = arrayListOf<ItemData>()
        for (row in 0 until boardDimension) {
            for (item in 0 until boardDimension) {
                if (boardItemList[row * boardDimension + item].state != TicTacToeState.NONE) {
                    itemInRow.add(boardItemList[row * boardDimension + item])
                }
            }
            if (itemInRow.isCrossed(boardDimension)) {
                Timber.d("Player " + getCurrentPlayer().text + " win!")
                emitResult(GameResult.WIN)
                break
            } else {
                itemInRow.clear()
            }
        }
    }

    private fun diagonalCheck() {
        val leftToRight = arrayListOf<ItemData>()
        val rightToLeft = arrayListOf<ItemData>()
        for (item in 0 until boardDimension) {
            leftToRight.add(boardItemList[item * boardDimension + item])
            rightToLeft.add(boardItemList[item * boardDimension + (boardDimension - item - 1)])
        }
        if (leftToRight.isCrossed(boardDimension)) {
            Timber.d("Player " + getCurrentPlayer().text + " win!")
            emitResult(GameResult.WIN)
        }

        if (rightToLeft.isCrossed(boardDimension)) {
            Timber.d("Player " + getCurrentPlayer().text + " win!")
            emitResult(GameResult.WIN)
        }
    }
}

class ViewModelFactory(private val boardDimension: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BoardViewModel::class.java)) {
            return BoardViewModel(boardDimension) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
