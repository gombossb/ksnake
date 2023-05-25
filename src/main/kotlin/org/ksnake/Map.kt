package org.ksnake

class Map (val size: Vec2) {
    val snake: Snake = Snake(4)
    val pickups = mutableSetOf<Pickup>()

    // add initial snake segments positioned at the center of the map
    init {
        // head
        snake.segments.add(Vec2((size.x/2+1).toByte(), (size.y/2).toByte()))

        // additional segments from right to left
        for (i in 0 downTo -3)
            snake.segments.add(Vec2((size.x/2+i).toByte(), (size.y/2).toByte()))
    }

    fun locationFree(loc: Vec2): Boolean {
        if (snake.segments.contains(loc))
            return false

        for (p in pickups)
            if (p.position == loc)
                return false

        return true
    }
}
