package org.directivexiii.beautiful.game

data class Player(val name: String)

class PlayerState(val player: Player, val stoneColor: TileColor, initialHandSize: Int){
    var remainingPieces = initialHandSize
}
