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
        const val GAME_MENU_HEIGHT = 100
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

    private val gameGUI = object {
        var rootPane: Pane? = null
        var canvas: Canvas? = null
        var canvasSize: Vec2? = null
        var livesLabel = Label()
        var scoreLabel = Label()
        val mainMenuButton = Button("Main Menu")
        val restartButton = Button("Restart")
        val pauseButton = Button("Pause")
        val toplistButton = Button("Toplist")
        var timer: AnimationTimer? = null
        init {
            mainMenuButton.setOnAction { e -> mainMenu() }
            restartButton.setOnAction {
                e -> timer?.stop()
                newGame(game.playerLives, game.map.mapSize)
                game()
            }
            toplistButton.setOnAction { e -> game.map.stepSnake() }
        }
        fun reset(){
            rootPane = null
            canvas = null
            canvasSize = null
            timer = null
        }
    }

    override fun start(mainStage: Stage) {
        this.mainStage = mainStage
        mainStage.title = "Kotlin Snake"
        mainStage.isResizable = false

        mainMenu()

        mainStage.show()
    }

    private fun mainMenu(){
        if (mainMenuScene == null){
            val mainMenuLayout = VBox()
            mainMenuLayout.spacing = 5.0
            mainMenuScene = Scene(mainMenuLayout, MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT)

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
        gameScene = null
        gameGUI.reset()
        println("new game: $lives $levelSize")
    }

    private fun game(){
        if (gameScene == null){
//            gameGUI.rootPane = Pane()
            gameGUI.canvasSize = SnakeMap.calcGameCanvasSize(game.map.mapSize)

            val vb = VBox()
            val hb = HBox()
            hb.spacing = 10.0
            hb.padding = Insets(10.0, 10.0, 10.0, 10.0)
            hb.children.add(gameGUI.livesLabel)
            hb.children.add(gameGUI.mainMenuButton)
            hb.children.add(gameGUI.restartButton)
            hb.children.add(gameGUI.pauseButton)
            hb.children.add(gameGUI.toplistButton)
            hb.children.add(gameGUI.scoreLabel)
            vb.children.add(hb)

            gameGUI.canvas = Canvas(gameGUI.canvasSize!!.x.toDouble(), gameGUI.canvasSize!!.y.toDouble())
            vb.children.add(gameGUI.canvas)

            gameScene = Scene(vb)

            gameScene!!.onKeyPressed = EventHandler { event ->
                currentlyActiveKeys.add(event.code)
            }
            gameScene!!.onKeyReleased = EventHandler { event ->
                currentlyActiveKeys.remove(event.code)
            }

            mainStage.scene = gameScene
            mainStage.sizeToScene()

        } else {
            mainStage.scene = gameScene
        }
//        val gc = gameGUI.canvas!!.graphicsContext2D



        gameGUI.timer = object : AnimationTimer() {
            override fun handle(currentNanoTime: Long) {
                gameLoop(currentNanoTime)
            }
        }
        gameGUI.timer!!.start()
    }

    private fun toplist(){
        // TODO
    }

    private fun saveGame(){
        // TODO
    }

    private fun gameLoop(currentNanoTime: Long) {
        val elapsedNanos = currentNanoTime - lastFrameTime
        lastFrameTime = currentNanoTime

        checkMovementKeys()

        val gc = gameGUI.canvas!!.graphicsContext2D
        gc.fill = Color.BLACK
        gc!!.fillRect(0.0, 0.0, gameGUI.canvasSize!!.x.toDouble(), gameGUI.canvasSize!!.y.toDouble())

        for (i in game.map.snake.segments.indices){
            val segment = game.map.snake.segments[i]
            gc.fill = if (i == 0) Color.YELLOW else Color.WHITE
            gc.fillRect(
                (segment.x * BLOCK_SIZE + segment.x * BLOCK_GAP_SIZE).toDouble(),
                (segment.y * BLOCK_SIZE + segment.y * BLOCK_GAP_SIZE).toDouble(),
                BLOCK_SIZE.toDouble(), BLOCK_SIZE.toDouble()
            )
        }

        fpsCounter(gc, elapsedNanos)
    }

    private fun fpsCounter(gc: GraphicsContext, elapsedNanos: Long){
        val elapsedMs = elapsedNanos / 1_000_000
        if (elapsedMs != 0L) {
            gc.fill = Color.WHITE
            gc.fillText("${1000 / elapsedMs} fps", 10.0, 10.0)
        }
    }

    private fun checkMovementKeys() {
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
