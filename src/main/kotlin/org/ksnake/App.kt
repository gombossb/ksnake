package org.ksnake

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.stage.Stage

class App : Application() {
    companion object {
        private var WIDTH = 600
        private var HEIGHT = 600
    }

    private lateinit var mainStage: Stage
    private lateinit var mainScene: Scene
    private lateinit var canvas: Canvas
    private lateinit var gc: GraphicsContext

//    private lateinit var space: Image
//    private lateinit var sun: Image

    private var lastFrameTime: Long = System.nanoTime()
    private val currentlyActiveKeys = mutableSetOf<KeyCode>()
    private lateinit var timer: AnimationTimer

    override fun start(mainStage: Stage) {
        this.mainStage = mainStage
        mainStage.title = "Kotlin Snake"

        val root = Group()
        mainScene = Scene(root)
        mainStage.scene = mainScene

        canvas = Canvas(WIDTH.toDouble(), HEIGHT.toDouble())
        root.children.add(canvas)

        prepareActionHandlers()

        gc = canvas.graphicsContext2D

        mainStage.show()
    }

    private fun prepareActionHandlers() {
        mainScene.onKeyPressed = EventHandler { event ->
            currentlyActiveKeys.add(event.code)
        }
        mainScene.onKeyReleased = EventHandler { event ->
            currentlyActiveKeys.remove(event.code)
        }
    }

//    private fun loadGraphics() {
//        // prefixed with / to indicate that the files are
//        // in the root of the "resources" folder
//        space = Image(getResource("/space.png"))
//        sun = Image(getResource("/sun.png"))
//    }

    private fun mainMenu(){

    }

    private fun game(){
        timer = object : AnimationTimer() {
            override fun handle(currentNanoTime: Long) {
                gameLoop(currentNanoTime)
            }
        }
        timer.start()
    }

    private fun gameLoop(currentNanoTime: Long) {
        // the time elapsed since the last frame, in nanoseconds
        // can be used for physics calculation, etc
        val elapsedNanos = currentNanoTime - lastFrameTime
        lastFrameTime = currentNanoTime

        // clear canvas
        gc.clearRect(0.0, 0.0, WIDTH.toDouble(), HEIGHT.toDouble())

        // draw background
//        graphicsContext.drawImage(space, 0.0, 0.0)
        gc.fill = Color.BLACK
        gc.fillRect(0.0, 0.0, WIDTH.toDouble(), HEIGHT.toDouble())

        // perform world updates
//        updateSunPosition()

        // draw sun
//        graphicsContext.drawImage(sun, sunX.toDouble(), sunY.toDouble())

        // fps counter
        val elapsedMs = elapsedNanos / 1_000_000
        if (elapsedMs != 0L) {
            gc.fill = Color.WHITE
            gc.fillText("${1000 / elapsedMs} fps", 10.0, 10.0)
        }
    }

//    private fun updateSunPosition() {
//        if (currentlyActiveKeys.contains(KeyCode.LEFT)) {
//            sunX--
//        }
//        if (currentlyActiveKeys.contains(KeyCode.RIGHT)) {
//            sunX++
//        }
//        if (currentlyActiveKeys.contains(KeyCode.UP)) {
//            sunY--
//        }
//        if (currentlyActiveKeys.contains(KeyCode.DOWN)) {
//            sunY++
//        }
//    }

}
