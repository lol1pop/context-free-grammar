package gui.controllers

import gui.models.PropertyGrammItem
import javafx.collections.FXCollections
import tornadofx.*

class Store : Controller(){
    val props = FXCollections.observableArrayList<PropertyGrammItem>()

    fun addProps(value: String) = props.add(PropertyGrammItem(value))
    fun removedProps(property: PropertyGrammItem) = props.remove(property)

}

class ComboStor: Controller() {
    val nonterm = FXCollections.observableArrayList<Char>(' ')
    fun addItems(charArr: CharArray) {
        nonterm.clear()
        nonterm.addAll(charArr.asList())
    }

}