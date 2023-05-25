package org.ksnake

class Game(mapSize: Vec2, val playerLives: Int) {
    val map = SnakeMap(mapSize)
    var currentLives = playerLives
    var paused = false
    var score = 0
    var playerName = ""
}
