package org.directivexiii.beautiful.game

class Stone(val color: TileColor, val capstone: Boolean = false, var state: TileState = TileState.FLAT) {
    init {
        if(capstone){
            state = TileState.STANDING
        }
    }

    override fun toString() : String =
        "$color ${if(capstone) "CAPSTONE" else if (state == TileState.STANDING) "STANDING" else ""}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Stone

        if (color != other.color) return false
        if (capstone != other.capstone) return false
        if (state != other.state) return false

        return true
    }

    override fun hashCode(): Int {
        var result = color.hashCode()
        result = 31 * result + capstone.hashCode()
        result = 31 * result + state.hashCode()
        return result
    }


}



data class StoneState(val stone: Stone, val x: Int, val y: Int)

enum class TileState {
    STANDING, FLAT
}

enum class TileColor {
    WHITE, BLACK
}
