import gui.MainForm
import javafx.application.Application
import tornadofx.App

class MainApp : App(MainForm::class)

fun main(args: Array<String>) {
    Application.launch(MainApp::class.java, *args)
}

//override fun start(stage: Stage) {
//    stage.minHeight = 200.0
//    stage.minWidth = 400.0
//    super.start(stage)
//}