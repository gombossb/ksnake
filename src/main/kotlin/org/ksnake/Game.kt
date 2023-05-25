package org.ksnake

class Game(mapSize: Vec2, val playerLives: Int) {
    val map = Map(mapSize)
    var currentLives = playerLives
    var paused = false
    var score = 0
    var playerName = ""
}
