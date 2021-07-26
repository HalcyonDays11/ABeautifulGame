package org.directivexiii.beautiful.game

class Stack(private val stones: MutableList<Stone> = mutableListOf()) {

    fun isEmpty(): Boolean =
        stones.isEmpty()

    fun topStone(): Stone =
        stones.last()

    fun bottomStone(): Stone =
        stones.first()

    fun dropStone(): Stone =
        stones.removeLast()

    fun placeStone(stone: Stone) =
        stones.add(stone)

    fun currentState(): List<Stone> =
        stones.toList()

    fun pickUp(size: Int): Stack {
        val newStackStones = stones.take(size)
        stones.removeAll(newStackStones)
        return Stack(newStackStones.toMutableList())
    }

    fun size(): Int = stones.size
}
