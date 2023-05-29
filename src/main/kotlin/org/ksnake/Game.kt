package org.ksnake

class Game(val mapSize: Vec2, val playerLives: Int) {
    var map = SnakeMap(mapSize)
    var currentLives = playerLives
    var paused = false
    var score = 0

    // return whether a rerender is required
    fun logicLoop(app: App, deltaTime: Long): Boolean {
        if (paused || currentLives == 0)
            return false

        app.checkMovementKeys()

        if (app.sumDtSinceLastTick >= App.NS_PER_TICK){
            app.sumDtSinceLastTick %= App.NS_PER_TICK

            map.stepSnake()
            if (map.pickups.contains(map.snake.segments[0])){
                map.pickups.remove(map.snake.segments[0])
                score++
            }

            if (map.snake.snakeHitItself()){
                currentLives--
                if (currentLives > 0){
                    map = SnakeMap(mapSize)
                    app.resetCooldown()
                } else {
                    app.saveScore.saveScoreScene()
                }
            }

            // no more free space
            if (map.snake.segments.size == map.mapSize.x * map.mapSize.y)
                app.saveScore.saveScoreScene()

            if ((0..10).random() == 0)
                map.spawnPickup()

            return true
        } else {
            return false
        }
    }
}
