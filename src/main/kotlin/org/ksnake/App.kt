package org.ksnake

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlin.system.exitProcess


class App : Application() {
    companion object {
        const val MAINMENU_WIDTH = 400.0
        const val MAINMENU_HEIGHT = 400.0
        const val BLOCK_SIZE = 20
        private var WIDTH = 600
        private var HEIGHT = 600
    }

    private lateinit var mainStage: Stage

    private var mainMenuScene: Scene? = null
    private var gameScene: Scene? = null
    private var saveScoreScene: Scene? = null
    private var toplistScene: Scene? = null

    private var lastFrameTime: Long = System.nanoTime()
    private val currentlyActiveKeys = mutableSetOf<KeyCode>()
    private lateinit var timer: AnimationTimer

    private lateinit var game: Game

    override fun start(mainStage: Stage) {
        this.mainStage = mainStage
        mainStage.title = "Kotlin Snake"

        mainMenu()

        mainStage.show()
    }

    private fun mainMenu(){
        if (mainMenuScene == null){
            val mainMenuLayout = VBox()
            mainMenuLayout.spacing = 5.0
            mainMenuScene = Scene(mainMenuLayout, MAINMENU_WIDTH, MAINMENU_HEIGHT)

            val titleLabel = Label("Kotlin Snake")
            mainMenuLayout.children.add(titleLabel)

            val gp = GridPane()
            gp.hgap = 5.0
            gp.vgap = 5.0
            val livesLabel = Label("Lives: ")
            gp.add(livesLabel, 0, 0)

            val levelSizeLabel = Label("Level size: ")
            gp.add(levelSizeLabel, 0, 1)

            val livesSelector = ChoiceBox<Int>()
            livesSelector.items.add(1)
            livesSelector.items.add(2)
            livesSelector.items.add(3)
            livesSelector.value = 1
            gp.add(livesSelector, 1, 0)

            val levelSizeSelector = ChoiceBox<String>()
            for (x in SnakeMap.levelSizes)
                levelSizeSelector.items.add(x.key)
            levelSizeSelector.value = "Medium"
            gp.add(levelSizeSelector, 1, 1)

            mainMenuLayout.children.add(gp)

            val hb = HBox()
            hb.spacing = 5.0
            val startButton = Button("Start")
            startButton.setOnAction{
                newGame(livesSelector.value, SnakeMap.levelSizes.get(levelSizeSelector.value)!!)
                game()
            }
            hb.children.add(startButton)

            val toplistButton = Button("Toplist")
            toplistButton.setOnAction{ toplist() }
            hb.children.add(toplistButton)

            val quitButton = Button("Quit")
            quitButton.setOnAction{ exitProcess(0) }
            hb.children.add(quitButton)

            mainMenuLayout.children.add(hb)
        }
        mainStage.scene = mainMenuScene
    }

    private fun newGame(lives: Int, levelSize: Vec2){
        game = Game(levelSize, lives)
        println("new game: $lives $levelSize")
    }

    private fun game(){
        if (gameScene == null){
            val root = Group()



            val canvas = Canvas(WIDTH.toDouble(), HEIGHT.toDouble())
            root.children.add(canvas)

        }
//        val gc = canvas.graphicsContext2D

//        gc.clearRect(0.0, 0.0, WIDTH.toDouble(), HEIGHT.toDouble())

        println("todo")
        timer = object : AnimationTimer() {
            override fun handle(currentNanoTime: Long) {
                gameLoop(currentNanoTime)
            }
        }
        timer.start()
    }

    private fun toplist(){
        // TODO
    }

    private fun saveGame(){
        // TODO
    }

    private fun gameLoop(currentNanoTime: Long) {
        // the time elapsed since the last frame, in nanoseconds
        // can be used for physics calculation, etc
        val elapsedNanos = currentNanoTime - lastFrameTime
        lastFrameTime = currentNanoTime

        // clear canvas
//        gc.clearRect(0.0, 0.0, WIDTH.toDouble(), HEIGHT.toDouble())
//
//        // draw background
////        graphicsContext.drawImage(space, 0.0, 0.0)
//        gc.fill = Color.BLACK
//        gc.fillRect(0.0, 0.0, WIDTH.toDouble(), HEIGHT.toDouble())
//
//        // perform world updates
////        updateSunPosition()
//
//        // draw sun
////        graphicsContext.drawImage(sun, sunX.toDouble(), sunY.toDouble())
//
//        // fps counter
//        val elapsedMs = elapsedNanos / 1_000_000
//        if (elapsedMs != 0L) {
//            gc.fill = Color.WHITE
//            gc.fillText("${1000 / elapsedMs} fps", 10.0, 10.0)
//        }
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

//    private fun loadGraphics() {
//        // prefixed with / to indicate that the files are
//        // in the root of the "resources" folder
//        space = Image(getResource("/space.png"))
//        sun = Image(getResource("/sun.png"))
//    }

//    private fun prepareActionHandlers() {
//        mainScene.onKeyPressed = EventHandler { event ->
//            currentlyActiveKeys.add(event.code)
//        }
//        mainScene.onKeyReleased = EventHandler { event ->
//            currentlyActiveKeys.remove(event.code)
//        }
//    }
}
