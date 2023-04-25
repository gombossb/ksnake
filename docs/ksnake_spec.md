# Kotlin Snake Specification
Gombos Sándor Bence

## Game description
Snake is a simple graphical game consisting of a level divided into blocks (on which pickups appear) and a "snake", whose body is made of adjacent blocks and has a head.
The snake's head can move in four directions (up, left, down, right) and the snake's body follows its movement.
The snake can eat randomly appearing pickups with its head, which increases its length and score by one.
If the snake's head hits a block of its body, it loses one life.
If the lives hit zero, the snake dies and the player can enter its name to be stored in the toplist.

## Interface
The game requires a computer with mouse and keyboard.
It launches in a desktop window.
It can be paused during gameplay and has a toplist feature.
The amount of lives and level dimensions should be able to be configured before starting a new game.

Controls:
- W: up
- A: left
- S: down
- D: right

## Planned Development Environment
Kotlin, JDK 17+, JavaFX
