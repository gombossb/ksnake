package org.ksnake

import javafx.beans.property.SimpleStringProperty
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.io.File
import kotlin.system.exitProcess


class Toplist(val app: App) {
    fun toplistScene(){
        val scores = loadScores()
        val tv = TableView<Score>()
        tv.items.addAll(scores)

        val nameColumn = TableColumn<Score, String>("Name")
        nameColumn.setCellValueFactory { SimpleStringProperty(it.value.name) }
        nameColumn.prefWidth = 100.0

        val scoreColumn = TableColumn<Score, String>("Score")
        scoreColumn.setCellValueFactory { SimpleStringProperty(it.value.score.toString()) }

        tv.columns.addAll(nameColumn, scoreColumn)

        val scrollPane = ScrollPane()
        scrollPane.setContent(tv)
        scrollPane.setFitToWidth(true)
        scrollPane.setFitToHeight(true)

        val paneBox = VBox(scrollPane)

        val hb = HBox()
        hb.spacing = 5.0

        if (app.game != null && app.game!!.currentLives > 0){
            val backToGameButton = Button("Back to Game")
            backToGameButton.setOnAction{
                app.game!!.paused = !app.game!!.paused
                app.gameGUI.gameScene()
            }
            hb.children.add(backToGameButton)
        }

        val toplistButton = Button("Main Menu")
        toplistButton.setOnAction{ app.mainMenu.mainMenuScene() }
        hb.children.add(toplistButton)

        val quitButton = Button("Quit")
        quitButton.setOnAction{ exitProcess(0) }
        hb.children.add(quitButton)

        val vb = VBox()
        vb.children.add(paneBox)
        vb.children.add(hb)

        app.mainStage.scene = Scene(vb, 400.0, 300.0)
        app.mainStage.sizeToScene()
        app.mainStage.hide()
        app.mainStage.show()

    }
    private fun loadScores(): MutableList<Score> {
        val scores = mutableMapOf<String, Int>()
        File("scores.txt").forEachLine {
            if (it.isNotEmpty()) {
                val arr = it.split("\t")
                if ((scores.containsKey(arr[0]) && scores[arr[0]]!! < arr[1].toInt())
                 || !scores.containsKey(arr[0]))
                    scores[arr[0]] = arr[1].toInt()
            }
        }

        val scoresList = mutableListOf<Score>()
        scores.keys.forEach {
            k -> scoresList.add(Score(k, scores[k]!!))
        }

        return scoresList
    }
}
