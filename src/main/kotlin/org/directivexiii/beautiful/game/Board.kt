package org.directivexiii.beautiful.game

class Board(val gridSize: Int = 4) {
    private val boardGrid: Array<Array<Square>> = Array(gridSize) {
        Array(gridSize) {
            Square()
        }
    }

    fun placeStone(x: Int, y: Int, stone: Stone){
        val square = boardGrid[x][y]
        if(!square.isControlled()){
            square.placeStone(stone)
        }
    }

    fun moveStack(fromX: Int, fromY: Int, toX: Int, toY: Int, depth: Int){
        val squares = squaresBetween(fromX, fromY, toX, toY).toMutableList()
        val firstSquare = squares.removeFirst()
        val stack = firstSquare.pickUpStack(depth)
        while(squares.isNotEmpty()){
            squares.removeLast().placeStone(stack.dropStone())
        }
    }

    fun squaresBetween(fromX: Int, fromY: Int, toX: Int, toY: Int): List<Square>{
        val squareList = mutableListOf<Square>()
        if(fromX == toX){
            val row = boardGrid[fromX]
            for(column in fromY .. toY ){
                squareList.add(row[column])
            }
        }else if (fromY == toY){
            for(row in fromX .. toX){
                squareList.add(boardGrid[row][fromY])
            }
        }
        return squareList.toList()
    }

    fun printBoard(){
        for(row in 0 until gridSize){
            for(col in 0 until gridSize){
                val topStone = boardGrid[row][col].topStone()
                when {
                    topStone == null -> {
                        print("[   ]")
                    }
                    topStone.color == TileColor.WHITE -> {
                        print("[ W ]")
                    }
                    else -> {
                        print("[ B ]")
                    }
                }
            }
            println()
        }
    }

    fun checkStoneAtSquare(x: Int, y: Int): Stone? =
        boardGrid[x][y].topStone()

    fun seeStonesAtSquare(x: Int, y: Int): List<Stone> =
        boardGrid[x][y].stones.currentState()

    fun pickUpStackAtSquare(x: Int, y: Int, depth: Int) {
        boardGrid[x][y].pickUpStack(depth)
    }
}
