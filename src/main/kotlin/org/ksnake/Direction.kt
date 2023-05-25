package org.ksnake

enum class Direction {
    UP, RIGHT, DOWN, LEFT;
    companion object {
        fun oppositeDirection(d1: Direction, d2: Direction): Boolean {
            return (
                (d1 == UP && d2 == DOWN) || (d1 == DOWN && d2 == UP) ||
                (d1 == LEFT && d2 == RIGHT) || (d1 == RIGHT && d2 == LEFT)
            )
        }
    }
}
