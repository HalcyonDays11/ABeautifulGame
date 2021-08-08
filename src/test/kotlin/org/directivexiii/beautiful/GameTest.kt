package org.directivexiii.beautiful

import org.directivexiii.beautiful.game.*
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

        board.placeStone(0, 0, Stone(TileColor.WHITE))
        board.placeStone(1, 0, Stone(TileColor.WHITE))
        board.placeStone(2, 0, Stone(TileColor.WHITE))
        board.placeStone(2, 1, Stone(TileColor.WHITE))
        board.placeStone(2, 2, Stone(TileColor.WHITE))
        board.placeStone(1, 2, Stone(TileColor.WHITE))
        board.placeStone(0, 2, Stone(TileColor.WHITE))

        game.placeStone(0, 3, TileState.FLAT) // White's "first" move

        board.printBoard()
        assertNotNull(game.winner)
        assertEquals(game.winner, player1)
    }

    @Test
    fun testStack(){
        val stack = Stack(mutableListOf(Stone(TileColor.WHITE), Stone(TileColor.WHITE), Stone(TileColor.BLACK), Stone(TileColor.BLACK)))

        assertEquals(listOf(Stone(TileColor.WHITE), Stone(TileColor.WHITE), Stone(TileColor.BLACK), Stone(TileColor.BLACK)), stack.currentState())

        assertEquals(Stone(TileColor.WHITE), stack.bottomStone())
        assertEquals(Stone(TileColor.BLACK), stack.topStone())

        assertEquals(Stone(TileColor.WHITE), stack.dropStone())

        assertEquals(listOf(Stone(TileColor.WHITE), Stone(TileColor.BLACK), Stone(TileColor.BLACK)), stack.currentState())

        stack.placeStone(Stone(TileColor.BLACK))

        assertEquals(listOf(Stone(TileColor.WHITE), Stone(TileColor.BLACK), Stone(TileColor.BLACK), Stone(TileColor.BLACK)), stack.currentState())

        assertEquals(Stack(mutableListOf(Stone(TileColor.BLACK), Stone(TileColor.BLACK))),stack.pickUp(2))

        assertEquals(listOf(Stone(TileColor.WHITE), Stone(TileColor.BLACK)), stack.currentState())

        assertEquals(Stone(TileColor.WHITE), stack.dropStone())

        assertEquals(listOf(Stone(TileColor.BLACK)), stack.currentState())

        stack.dropStone()

        stack.placeStone(Stone(TileColor.WHITE))
        stack.placeStone(Stone(TileColor.BLACK))
        stack.placeStones(Stack(mutableListOf(Stone(TileColor.WHITE), Stone(TileColor.BLACK))))
        stack.placeStone(Stone(TileColor.BLACK))

        assertEquals(listOf(Stone(TileColor.WHITE), Stone(TileColor.BLACK), Stone(TileColor.WHITE),Stone(TileColor.BLACK),Stone(TileColor.BLACK)), stack.currentState())

        assertEquals(Stack(mutableListOf(Stone(TileColor.WHITE), Stone(TileColor.BLACK), Stone(TileColor.WHITE))), stack.dropStones(3))

        assertEquals(listOf(Stone(TileColor.BLACK), Stone(TileColor.BLACK)), stack.currentState())

    }
}
