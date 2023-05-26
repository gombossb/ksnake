package org.ksnake

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import kotlin.system.exitProcess


class App : Application() {
    companion object {
        const val MAIN_MENU_WIDTH = 300.0
        const val MAIN_MENU_HEIGHT = 300.0
        const val BLOCK_SIZE = 20
        const val BLOCK_GAP_SIZE = 3
        const val NS_PER_TICK: Long = 500_000_000 // 500ms
    }

    lateinit var mainStage: Stage

    val mainMenu = MainMenu(this)
    val gameGUI = GameGUI(this)

    private var saveScoreScene: Scene? = null
    private var toplistScene: Scene? = null

    val currentlyActiveKeys = mutableSetOf<KeyCode>()
    var lastFrameTime: Long = System.nanoTime()
    var sumDtSinceLastTick: Long = 0
    private var ticks: Long = 0

    lateinit var game: Game


    override fun start(mainStage: Stage) {
        this.mainStage = mainStage
        mainStage.title = "Kotlin Snake"
        mainStage.isResizable = false

        mainMenu.mainMenuScene()

        mainStage.show()
    }

    fun newGame(lives: Int, levelSize: Vec2){
        game = Game(levelSize, lives)
        gameGUI.reset()
        println("new game: $lives $levelSize")
    }

    fun toplist(){
        // TODO
    }

    private fun saveGame(){
        // TODO
    }

    fun gameLoop(currentNanoTime: Long) {
        val dt = currentNanoTime - lastFrameTime
        lastFrameTime = currentNanoTime
        sumDtSinceLastTick += dt

        val rerender = game.logicLoop(this, dt)
        if (rerender){
            gameGUI.renderGameCanvas()
            fpsCounter(gameGUI.canvas!!.graphicsContext2D, dt)
        }
    }

    private fun fpsCounter(gc: GraphicsContext, elapsedNanos: Long){
        val elapsedMs = elapsedNanos / 1_000_000
        if (elapsedMs != 0L) {
            gc.fill = Color.WHITE
            gc.fillText("${1000 / elapsedMs} fps", 10.0, 10.0)
        }
    }

    fun checkMovementKeys() {
        var preferredDir: Direction? = null
        if (currentlyActiveKeys.contains(KeyCode.W) || currentlyActiveKeys.contains(KeyCode.UP)) {
            preferredDir = Direction.UP
        } else if (currentlyActiveKeys.contains(KeyCode.D) || currentlyActiveKeys.contains(KeyCode.RIGHT)) {
            preferredDir = Direction.RIGHT
        } else if (currentlyActiveKeys.contains(KeyCode.S) || currentlyActiveKeys.contains(KeyCode.DOWN)) {
            preferredDir = Direction.DOWN
        } else if (currentlyActiveKeys.contains(KeyCode.A) || currentlyActiveKeys.contains(KeyCode.LEFT)) {
            preferredDir = Direction.LEFT
        }

        if (preferredDir != null && !Direction.oppositeDirection(preferredDir, game.map.snake.headDir)){
            game.map.snake.headDir = preferredDir
        }
    }
}
