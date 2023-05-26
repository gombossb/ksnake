package org.ksnake

class SnakeMap (val mapSize: Vec2) {
    val snake: Snake = Snake()
    val pickups = mutableSetOf<Vec2>()

    companion object {
        val levelSizes = linkedMapOf(
            "Small" to Vec2(15, 15),
            "Medium" to Vec2(20, 20),
            "Large" to Vec2(30, 30),
            "Very Big" to Vec2(42, 42),
        )
        fun calcGameCanvasSize(mapSize: Vec2): Vec2 {
            return Vec2(
                (mapSize.x * App.BLOCK_SIZE + mapSize.x * App.BLOCK_GAP_SIZE).toShort(),
                (mapSize.y * App.BLOCK_SIZE + mapSize.y * App.BLOCK_GAP_SIZE).toShort()
            )
        }
    }

    // add initial snake segments positioned at the center of the map
    init {
        // head
        snake.segments.add(Vec2((mapSize.x/2+1).toShort(), (mapSize.y/2).toShort()))

        // additional segments from right to left
        for (i in 0 downTo -3)
            snake.segments.add(Vec2((mapSize.x/2+i).toShort(), (mapSize.y/2).toShort()))
    }

    fun locationFree(loc: Vec2): Boolean {
        if (snake.segments.contains(loc))
            return false

        for (p in pickups)
            if (p == loc)
                return false

        return true
    }

    fun spawnPickup(){
        val randomLoc = Vec2((0 until mapSize.x).random().toShort(), (0 until mapSize.y).random().toShort())
        if (!locationFree(randomLoc) || pickups.size > 0)
            return

        pickups.add(randomLoc)
    }

    fun stepSnake(){
        var prev = snake.segments[0]
        stepSnakeHead()

        var lastSegment: Vec2? = null
        if (pickups.contains(snake.segments[0])) {
            lastSegment = snake.segments.last()
        }

        for (i in 1 until snake.segments.size){
            val curr = snake.segments[i]
            snake.segments[i] = prev
            prev = curr
        }
        if (lastSegment != null)
            snake.segments.add(lastSegment)
    }

    private fun stepSnakeHead(){
        when (snake.headDir){
            Direction.UP -> {
                if (snake.segments[0].y == 0.toShort())
                    snake.segments[0] = Vec2(snake.segments[0].x, (mapSize.y - 1).toShort())
                else
                    snake.segments[0] = Vec2(snake.segments[0].x, (snake.segments[0].y - 1).toShort())
            }
            Direction.RIGHT -> {
                if ((snake.segments[0].x + 1).toShort() == mapSize.x)
                    snake.segments[0] = Vec2(0, snake.segments[0].y)
                else
                    snake.segments[0] = Vec2((snake.segments[0].x + 1).toShort(), snake.segments[0].y)
            }
            Direction.DOWN -> {
                if ((snake.segments[0].y + 1).toShort() == mapSize.y)
                    snake.segments[0] = Vec2(snake.segments[0].x, 0)
                else
                    snake.segments[0] = Vec2(snake.segments[0].x, (snake.segments[0].y + 1).toShort())
            }
            Direction.LEFT -> {
                if (snake.segments[0].x == 0.toShort())
                    snake.segments[0] = Vec2((mapSize.x - 1).toShort(), snake.segments[0].y)
                else
                    snake.segments[0] = Vec2((snake.segments[0].x - 1).toShort(), snake.segments[0].y)
            }
        }
    }
}
