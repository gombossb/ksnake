package org.ksnake

import javafx.animation.AnimationTimer
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color

class GameGUI(val app: App) {
    var gameScene: Scene? = null
    var rootPane: Pane? = null
    var canvas: Canvas? = null
    var canvasSize: Vec2? = null
    var livesLabel = Label("Lives: X")
    var scoreLabel = Label("Score: XYZ")
    val mainMenuButton = Button("Main Menu")
    val restartButton = Button("Restart")
    val pauseButton = Button("Pause")
    val toplistButton = Button("Toplist")
    var timer: AnimationTimer? = null

    init {
        mainMenuButton.setOnAction {
            app.game = null
            app.mainMenu.mainMenuScene()
        }
        restartButton.setOnAction {
            timer?.stop()
            app.newGame(app.game!!.playerLives, app.game!!.map.mapSize)
            gameScene()
        }
        pauseButton.setOnAction {
            app.game!!.paused = !app.game!!.paused
            pauseButton.text = if (app.game!!.paused) "Resume" else "Pause"
        }
        toplistButton.setOnAction {
            timer?.stop()
            app.game!!.paused = !app.game!!.paused
            app.toplist.toplistScene()
        }
    }

    fun reset(){
        gameScene = null
        rootPane = null
        canvas = null
        canvasSize = null
        timer?.stop()
        timer = null
    }

    private fun updateLabels(lives: Int, score: Int){
        livesLabel.text = "Lives: $lives"
        scoreLabel.text = "Score: $score"
    }

    fun gameScene(){
        if (gameScene == null){
            canvasSize = SnakeMap.calcGameCanvasSize(app.game!!.map.mapSize)

            val vb = VBox()
            val hb = HBox()
            hb.spacing = 10.0
            hb.padding = Insets(10.0, 10.0, 10.0, 10.0)
            hb.children.add(livesLabel)
            hb.children.add(mainMenuButton)
            hb.children.add(restartButton)
            hb.children.add(pauseButton)
            hb.children.add(toplistButton)
            hb.children.add(scoreLabel)
            vb.children.add(hb)

            canvas = Canvas(canvasSize!!.x.toDouble(), canvasSize!!.y.toDouble())
            vb.children.add(canvas)

            gameScene = Scene(vb)

            gameScene!!.onKeyPressed = EventHandler { event ->
                app.currentlyActiveKeys.add(event.code)
            }
            gameScene!!.onKeyReleased = EventHandler { event ->
                app.currentlyActiveKeys.remove(event.code)
            }

            app.mainStage.scene = gameScene
            app.mainStage.sizeToScene()
            app.mainStage.hide()
            app.mainStage.show()

        } else {
            app.mainStage.scene = gameScene
        }

        timer = object : AnimationTimer() {
            override fun handle(currentNanoTime: Long) {
                app.gameLoop(currentNanoTime)
            }
        }
        timer!!.start()
    }

    fun renderGameCanvas(){
        updateLabels(app.game!!.currentLives, app.game!!.score)

        val gc = canvas!!.graphicsContext2D
        gc.fill = Color.BLACK
        gc!!.fillRect(0.0, 0.0, canvasSize!!.x.toDouble(), canvasSize!!.y.toDouble())

        for (p in app.game!!.map.pickups){
            gc.fill = Color.PURPLE
            gc.fillRect(
                (p.x * App.BLOCK_SIZE + p.x * App.BLOCK_GAP_SIZE).toDouble(),
                (p.y * App.BLOCK_SIZE + p.y * App.BLOCK_GAP_SIZE).toDouble(),
                App.BLOCK_SIZE.toDouble(), App.BLOCK_SIZE.toDouble()
            )
        }

        for (i in app.game!!.map.snake.segments.size-1 downTo 0){
            val segment = app.game!!.map.snake.segments[i]

            if (i == 0)
                gc.fill = Color.WHITE
            else
                gc.fill = if (app.game!!.currentLives == 0) Color.RED else Color.YELLOW

            gc.fillRect(
                (segment.x * App.BLOCK_SIZE + segment.x * App.BLOCK_GAP_SIZE).toDouble(),
                (segment.y * App.BLOCK_SIZE + segment.y * App.BLOCK_GAP_SIZE).toDouble(),
                App.BLOCK_SIZE.toDouble(), App.BLOCK_SIZE.toDouble()
            )
        }
    }
}
