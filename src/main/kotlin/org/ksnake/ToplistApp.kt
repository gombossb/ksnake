package org.ksnake

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.stage.Stage

class ToplistApp : Application() {
    companion object {
        private const val WIDTH = 400
        private const val HEIGHT = 600
    }

    private lateinit var mainScene: Scene
    private lateinit var graphicsContext: GraphicsContext

    override fun start(mainStage: Stage) {
        mainStage.title = "Toplist"

        val root = Group()
        mainScene = Scene(root)
        mainStage.scene = mainScene

        val canvas = Canvas(WIDTH.toDouble(), HEIGHT.toDouble())
        root.children.add(canvas)

//        prepareActionHandlers()

        graphicsContext = canvas.graphicsContext2D

//        loadGraphics()
    }

    override fun stop() {
        super.stop()
    }
}