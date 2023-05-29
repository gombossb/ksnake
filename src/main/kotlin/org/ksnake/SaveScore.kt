package org.ksnake

import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import java.io.File

class SaveScore(val app: App) {
    fun saveScoreScene(){
        val layout = VBox()
        layout.spacing = 10.0

        layout.children.add(Label("Name: "))

        val tf = TextField()
        layout.children.add(tf)

        val saveButton = Button("Save Score")
        saveButton.setOnAction { saveScore(tf.text, app.game!!.score); app.game = null }
        layout.children.add(saveButton)

        val mainMenuButton = Button("Main Menu")
        mainMenuButton.setOnAction { app.mainMenu.mainMenuScene() }
        layout.children.add(mainMenuButton)

        app.mainStage.scene = Scene(layout)
        app.mainStage.sizeToScene()
        app.mainStage.hide()
        app.mainStage.show()
    }
    private fun saveScore(name: String, score: Int){
        println("$name $score")
        File("scores.txt").appendText("$name\t$score\n")
        app.toplist.toplistScene()
    }
}
