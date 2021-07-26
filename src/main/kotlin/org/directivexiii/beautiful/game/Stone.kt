package org.directivexiii.beautiful.game

class Stone(val capstone: Boolean = false, var state: TileState, val color: TileColor) {
    init {
        if(capstone){
            state = TileState.STANDING
        }
    }
}

data class StoneState(val stone: Stone, val x: Int, val y: Int)

enum class TileState {
    STANDING, FLAT
}

enum class TileColor {
    WHITE, BLACK
}
