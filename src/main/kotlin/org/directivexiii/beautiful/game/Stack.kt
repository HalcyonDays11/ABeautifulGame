package org.directivexiii.beautiful.game

class Stack(private val stones: MutableList<Stone> = mutableListOf()) {

    fun isEmpty(): Boolean =
        stones.isEmpty()

    fun topStone(): Stone =
        stones.last()

    fun bottomStone(): Stone =
        stones.first()

    fun topStones(n : Int): List<Stone> =
        stones.takeLast(n)

    fun bottomStones(n : Int): List<Stone> =
        stones.take(n)

    fun dropStone(): Stone =
        stones.removeFirst()

    fun dropStones(n : Int): Stack {
       if (n > stones.size){
           throw NoSuchElementException ("tried to drop $n stones from a stack of size ${stones.size}")
       }
       val newStack = mutableListOf<Stone>()
       repeat (n){
           newStack.add(dropStone())
       }
       return Stack(newStack)
    }

    fun placeStones(stack : Stack) =
        stones.addAll(stack.stones)

    fun placeStone (stone : Stone) =
        stones.add(stone)

    fun currentState(): List<Stone> =
        stones.toList()

    fun pickUp(size: Int): Stack {
        val newStackStones = stones.takeLast(size)
        repeat (size) {
            stones.removeLast()
        }
        return Stack(newStackStones.toMutableList())
    }

    fun size(): Int = stones.size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Stack

        if (stones != other.stones) return false

        return true
    }

    override fun hashCode(): Int = stones.hashCode()

    override fun toString() : String = stones.toString()



}
