package org.ksnake

import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import kotlin.system.exitProcess

class MainMenu(val app: App) {
    var mainMenuScene: Scene? = null
    fun mainMenuScene(){
        if (mainMenuScene == null){
            val mainMenuLayout = VBox()
            mainMenuLayout.spacing = 5.0
            mainMenuScene = Scene(mainMenuLayout, App.MAIN_MENU_WIDTH, App.MAIN_MENU_HEIGHT)

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
                app.newGame(livesSelector.value, SnakeMap.levelSizes.get(levelSizeSelector.value)!!)
                app.gameGUI.gameScene()
            }
            hb.children.add(startButton)

            val toplistButton = Button("Toplist")
            toplistButton.setOnAction{ app.toplist.toplistScene() }
            hb.children.add(toplistButton)

            val quitButton = Button("Quit")
            quitButton.setOnAction{ exitProcess(0) }
            hb.children.add(quitButton)

            mainMenuLayout.children.add(hb)
        }
        app.mainStage.scene = mainMenuScene
        app.mainStage.sizeToScene()
        app.mainStage.hide()
        app.mainStage.show()
    }
}
