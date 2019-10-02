package gui

import gui.models.PropertyGrammList
import gui.models.Terminal
import gui.models.TerminalModel
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.StageStyle
import tornadofx.*

class tMainForm: View() {
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

class MainForm: View() {
    override val root = Form()
    val termModel = TerminalModel(Terminal())

    init{
        with(root) {
            hbox {
                vbox {
                    prefWidth = 200.0
                    hboxConstraints { hGrow = Priority.ALWAYS }
                    label("Enter your Grammatical language")
                    fieldset {
                        field("nonterminal") {
                            textfield(termModel.nonterminal).required(message = "Input  VN")
                        }
                        field("terminal") {
                            textfield(termModel.terminal).required(message = "Input VT")
                        }
                    }
                    hbox {
                    }
                }

                this += PropertyGrammList::class
            }
        }
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