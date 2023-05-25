package org.ksnake

class Snake (initialLength: Int) {
    var headDir = Direction.RIGHT

    // first is head
    val segments = mutableListOf<Vec2>()
}
