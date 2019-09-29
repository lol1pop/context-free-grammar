package gui

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.stage.StageStyle
import tornadofx.*

class MainForm: View() {
    val controller: MyController by inject()
    val input = SimpleStringProperty()

    override val root = form {
        button("Press me"){
            action {
                find<MyFragment>().openModal(stageStyle = StageStyle.UTILITY)
            }
        }
        label("Waiting")
        fieldset {
            field("Input") {
                textfield(input)
            }

            button("Commit") {
                action {
                    controller.writeToDb(input.value)
                    input.value = ""
                }
            }
        }
        label("My items")
        val list = controller.values
        list.addAll("lol")
        val item = "kek"
        list.addAll(item)
        listview(list)
    }

}

class MyController: Controller() {
    fun writeToDb(inputValue: String) {
        println("Writing $inputValue to database!")
    }

    val values = FXCollections.observableArrayList("Alpha","Beta","Gamma","Delta")
}

class MyFragment: Fragment() {
    override val root = label("This is a popup")
}