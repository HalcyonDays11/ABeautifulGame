package org.directivexiii.beautiful.game

class Square {
    val stones = Stack()

    fun isControlled(): Boolean =
        !stones.isEmpty()

    fun topStone(): Stone? {
        return if (stones.isEmpty()) {
            null
        } else {
            stones.topStone()
        }
    }

    fun placeStone(stone: Stone) =
        stones.placeStone(stone)

    fun getStack(): Stack = stones

    fun pickUpStack(size: Int): Stack{
        assert(size <= stones.size())
        return stones.pickUp(size)
    }
}
