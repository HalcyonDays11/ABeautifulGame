package org.directivexiii.beautiful.game

import kotlin.system.measureTimeMillis

class Game(val board: Board, val whitePlayer: Player, val blackPlayer: Player) {
    val initialHandSize = calculateHandSize()

    var nextPlayer = TileColor.WHITE
    var winner: Player? = null

    var playerStateMap = mapOf(
            Pair(TileColor.WHITE, PlayerState(whitePlayer, TileColor.WHITE, initialHandSize)),
            Pair(TileColor.BLACK, PlayerState(blackPlayer, TileColor.BLACK, initialHandSize))
    )

    fun placeStone(x: Int, y: Int, state: TileState){
        playerStateMap[nextPlayer]!!.remainingPieces--
        val stone = Stone(nextPlayer, false, state)
        board.placeStone(x, y, stone)
        if(!checkVictory()){
            swapTurns()
        }
    }

    fun checkLegalPlace(x: Int, y: Int, state: TileState): Boolean {
        if(x >= board.gridSize || y >= board.gridSize){
            return false
        }
        val currentStone = board.checkStoneAtSquare(x, y)
        return (currentStone?.state ?: TileState.FLAT) != TileState.STANDING
    }

    fun checkLegalMove(fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        return true
    }

    private fun checkVictory(): Boolean {
        val measureTimeMillis = measureTimeMillis {
            checkForRoads()
        }
        println("Took ${measureTimeMillis}ms to look for roads.")
        if(winner == null){
            if(playerStateMap[nextPlayer]!!.remainingPieces <= 0){
                swapTurns()
                winner = playerStateMap[nextPlayer]!!.player
                return true
            }
            return false
        }
        return true
    }

    private fun checkForRoads() {
        for(col in 0 until board.gridSize){
            val stone = board.checkStoneAtSquare(0, col)
            if(stone != null && stone.state == TileState.FLAT){
                val path = PotentialPath()
                path.push(StoneState(stone, 0, col))
                while(!path.isEmpty()){
                    val currentStone = path.peek()
                    if(currentStone.x == (board.gridSize-1)){
                        winner = playerStateMap[currentStone.stone.color]?.player
                        return
                    }
                    checkAroundStone(currentStone, path)
                    if(path.peek() == currentStone){
                        path.pop()
                    }
                }
            }
        }
        for(row in 0 until board.gridSize){
            val stone = board.checkStoneAtSquare(row, 0)
            if(stone != null && stone.state == TileState.FLAT){
                val path = PotentialPath()
                path.push(StoneState(stone, row, 0))
                while(!path.isEmpty()){
                    val currentStone = path.peek()
                    if(currentStone.y == (board.gridSize - 1)){
                        winner = playerStateMap[currentStone.stone.color]?.player
                        return
                    }
                    checkAroundStone(currentStone, path)
                    if(path.peek() == currentStone){
                        path.pop()
                    }
                }
            }
        }
    }

    private fun checkAroundStone(currentStone: StoneState, path: PotentialPath){
        var nextStone: Stone?
        if(currentStone.x > 0) {
            //check left
            nextStone = board.checkStoneAtSquare(currentStone.x - 1, currentStone.y)
            if (partOfPath(nextStone, currentStone.stone.color)) {
                if(path.offer(StoneState(nextStone!!, currentStone.x - 1, currentStone.y))){
                    return
                }
            }
        }
        if(currentStone.x < (board.gridSize-1)){
            //check right
            nextStone = board.checkStoneAtSquare(currentStone.x + 1, currentStone.y)
            if (partOfPath(nextStone, currentStone.stone.color)) {
                if(path.offer(StoneState(nextStone!!, currentStone.x + 1, currentStone.y))) {
                    return
                }
            }
        }
        if(currentStone.y > 0){
            //check up
            nextStone = board.checkStoneAtSquare(currentStone.x, currentStone.y - 1)
            if (partOfPath(nextStone, currentStone.stone.color)) {
                if(path.offer(StoneState(nextStone!!, currentStone.x, currentStone.y - 1))){
                    return
                }

            }
        }
        if(currentStone.y < (board.gridSize-1)){
            //check down
            nextStone = board.checkStoneAtSquare(currentStone.x, currentStone.y + 1)
            if (partOfPath(nextStone, currentStone.stone.color)) {
                if(path.offer(StoneState(nextStone!!, currentStone.x, currentStone.y + 1))){
                    return
                }
            }
        }
    }

    private fun swapTurns(){
        nextPlayer = if(nextPlayer == TileColor.WHITE){
            TileColor.BLACK
        }else{
            TileColor.WHITE
        }
    }

    private fun calculateHandSize(): Int {
        return when(board.gridSize) {
            3 -> 10
            4 -> 15
            5 -> 21
            6 -> 30
            7 -> 40
            8 -> 50
            else -> 0
        }
    }

    private fun partOfPath(nextStone: Stone?, color: TileColor): Boolean{
        return nextStone != null && nextStone.color == color && nextStone.state == TileState.FLAT
    }
 }
