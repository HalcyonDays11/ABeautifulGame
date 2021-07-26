package org.directivexiii.beautiful

import org.directivexiii.beautiful.game.Board
import org.directivexiii.beautiful.game.Game
import org.directivexiii.beautiful.game.Player
import org.directivexiii.beautiful.game.Stone
import org.directivexiii.beautiful.game.TileColor
import org.directivexiii.beautiful.game.TileState
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GameTest {

    @Test
    fun testGame(){
        val player1 = Player("player1")
        val player2 = Player("player2")

        val board = Board()
        val game = Game(board, player1, player2)

        val checkLegalPlace = game.checkLegalPlace(0, 0, TileState.FLAT)

        assertTrue(checkLegalPlace)

        game.placeStone(0, 0, TileState.FLAT) //white

        assertNull(game.winner)

        game.placeStone(1, 0, TileState.FLAT) //black
        game.placeStone(0, 1, TileState.FLAT) //white
        game.placeStone(1, 1, TileState.FLAT) //black
        game.placeStone(0, 2, TileState.FLAT) //white
        game.placeStone(1, 2, TileState.FLAT) //black
        game.placeStone(0, 3, TileState.FLAT) //white, winning move

        board.printBoard()

        assertNotNull(game.winner)
        assertEquals(game.winner, player1)
    }

    @Test
    fun testLongRoadFinding(){
        val player1 = Player("player1")
        val player2 = Player("player2")

        val board = Board()
        val game = Game(board, player1, player2)

        board.placeStone(0, 0, Stone(false, TileState.FLAT, TileColor.WHITE))
        board.placeStone(1, 0, Stone(false, TileState.FLAT, TileColor.WHITE))
        board.placeStone(2, 0, Stone(false, TileState.FLAT, TileColor.WHITE))
        board.placeStone(2, 1, Stone(false, TileState.FLAT, TileColor.WHITE))
        board.placeStone(2, 2, Stone(false, TileState.FLAT, TileColor.WHITE))
        board.placeStone(1, 2, Stone(false, TileState.FLAT, TileColor.WHITE))
        board.placeStone(0, 2, Stone(false, TileState.FLAT, TileColor.WHITE))

        game.placeStone(0, 3, TileState.FLAT) // White's "first" move

        board.printBoard()
        assertNotNull(game.winner)
        assertEquals(game.winner, player1)
    }
}
