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

    // TODO this needs some tests
    fun placeStack(incomingStack: Stack) {
        if (topStone()?.capstone == true){
            throw IllegalArgumentException("can't place on top of a capstone")
        }
        // A standing stone can only be flattened by the capstone by itself, not by a taller stack with the capstone on top.
        if (topStone()?.state == TileState.STANDING  && incomingStack.size() != 1 && !incomingStack.topStone().capstone){
            throw IllegalArgumentException("only the capstone by itself can flatten")
        }
        topStone()?.state = TileState.FLAT;  // usually already flat, but might be getting flattened by the capstone
        stones.placeStones(incomingStack)

    }
    // TODO  this needs some tests
    fun placeStone(stone: Stone) {
        if (topStone()?.capstone == true){
            throw IllegalArgumentException("can't place on top of a capstone")
        }
        if (topStone()?.state == TileState.STANDING  && !stone.capstone){
            throw IllegalArgumentException("can't place a normal stone on top of a standing stone")
        }
        topStone()?.state = TileState.FLAT;
        stones.placeStone(stone)

    }

    fun getStack(): Stack = stones

    fun pickUpStack(size: Int): Stack {
        assert(size <= stones.size())
        return stones.pickUp(size)
    }
}
