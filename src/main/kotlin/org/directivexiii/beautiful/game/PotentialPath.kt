package org.directivexiii.beautiful.game

class PotentialPath {
    private val path = mutableListOf<StoneState>()
    private val history = mutableListOf<StoneState>()

    fun push(stone: StoneState){
        path.add(stone)
        history.add(stone)
    }

    fun offer(stone: StoneState): Boolean{
        return if(!hasSeen(stone)){
            push(stone)
            true
        }else
            false
    }

    fun isEmpty(): Boolean = path.isEmpty()

    fun pop(): StoneState{
        return path.removeLast()
    }

    fun peek(): StoneState{
        return path.last()
    }

    fun hasSeen(stone: StoneState): Boolean {
        return history.contains(stone)
    }
}
