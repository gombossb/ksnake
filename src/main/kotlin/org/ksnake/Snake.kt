package org.ksnake

class Snake () {
    var headDir = Direction.RIGHT

    // first is head
    val segments = mutableListOf<Vec2>()

    fun snakeHitItself(): Boolean {
        return segments.toMutableSet().size != segments.size
    }
}
