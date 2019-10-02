package gui.controllers

import gui.models.PropertyGramm
import javafx.collections.FXCollections
import tornadofx.*

class PropertyGrammController: Controller() {
    val props = FXCollections.observableArrayList<PropertyGramm>()
    init {
        props += PropertyGramm("S","0A|1A|")
        props += PropertyGramm("A", "1B|0B")
        props += PropertyGramm("B", "1S|0S")
    }
}