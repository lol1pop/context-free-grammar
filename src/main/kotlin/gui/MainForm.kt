package gui

import grammatic.GeneratedSequence
import grammatic.entity.DataGrammatics
import gui.controllers.ComboStor
import gui.controllers.PropertyGrammController
import gui.controllers.ListGeneratedGrammaticController
import gui.models.*
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.layout.Priority
import javafx.stage.StageStyle
import tornadofx.*

class tMainForm : View() {
    val controller: MyController by inject()
    val input = SimpleStringProperty()

    override val root = form {
        button("Press me") {
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

class MainForm : View() {
    override val root = Form()
    val termModel = TerminalModel(Terminal())
    val comboStor: ComboStor by inject()
    val propsModel: PropertyGrammModel by inject()
    val propsStor: PropertyGrammController by inject()
    val listStore: ListGeneratedGrammaticController by inject()
    val maxField = SimpleStringProperty()
    var max: Int = 5
    val minField = SimpleStringProperty()
    var min: Int = 1
    var originSymbol = ' '
    var selectSymbol = ' '

    init {
        with(root) {
            hbox {
                vbox {
                    prefWidth = 270.0
                    hboxConstraints { hGrow = Priority.ALWAYS }
                    label("Enter your Grammatical language")
                    fieldset {
                        field("nonterminal") {
                            textfield(termModel.nonterminal).required(message = "Input  VN")
                            setOnKeyReleased {
                                comboStor.addItems(termModel.nonterminal.value.toCharArray())
                            }
                        }
                        field("terminal") {
                            textfield(termModel.terminal).required(message = "Input VT")
                        }

                        field("Origin Symbol") {
                            combobox<Char> {
                                items = comboStor.nonterm
                                setOnAction {
                                    selectedItem?.let {  originSymbol = it }
                                }
                            }
                        }
                    }
                    paddingAll = 6
                    hbox {
                        combobox<Char> {
                            items = comboStor.nonterm
                            setOnAction {
                                selectedItem?.let {  selectSymbol = it }
                            }
                        }
                        textfield(propsModel.value)
                        button("+") {
                            setOnAction {
                                if(selectSymbol != ' ') {
                                    propsModel.key.value = selectSymbol.toString()
                                    if(propsModel.value.value.isEmpty() || propsModel.value.value == " " )
                                        propsModel.value.value = ""
                                    propsModel.commit()
                                    propsStor.props.add(PropertyGramm(key = propsModel.key.value,
                                                                      value = propsModel.value.value))
                                }
                            }
                        }
                        button("-") {
                            setOnAction {
                                propsStor.removeProps(
                                    PropertyGramm(
                                        key = propsModel.key.value,
                                        value = propsModel.value.value
                                    )
                                )
                            }
                        }

                    }
                    scrollpane {
                        this += PropertyGrammTable::class
                    }
                    hbox {
                        label("min")
                        minField.value = min.toString()
                        val txt = textfield(minField)
                        txt.isEditable = false
                        txt.prefWidth = 30.0
                        button("+") {
                            action {
                                min++
                                minField.value = min.toString()
                            }
                        }
                        button("-") {
                            action {
                                if (min != 0) min--
                                minField.value = min.toString()
                            }
                        }
                    }
                    hbox {
                        label("max")
                        maxField.value = max.toString()
                        val txt = textfield(maxField)
                        txt.isEditable = false
                        txt.prefWidth = 30.0
                        button("+") {
                            action {
                                max++
                                maxField.value = max.toString()
                            }
                        }
                        button("-") {
                            action {
                                if (max != 0) max--
                                maxField.value = max.toString()
                            }
                        }
                    }
                    button("start") {
                        action {
                            val listGenStrGram = start(termModel.nonterminal.value,
                                  termModel.terminal.value,
                                  originSymbol.toString(),
                                  propsStor.props,
                                  min, max)
                            val set: Set<String>  = listGenStrGram.toHashSet()
                            listStore.list.setAll(set.toList())
                        }

                    }
                }
                scrollpane {
                listview(listStore.list) {

                }}
                //this += PropertyGrammList::class
            }
        }
    }
}


class MyController : Controller() {
    fun writeToDb(inputValue: String) {
        println("Writing $inputValue to database!")
    }

    val values = FXCollections.observableArrayList("Alpha", "Beta", "Gamma", "Delta")
}

class MyFragment : Fragment() {
    override val root = label("This is a popup")
}


fun start(nonterminal: String,
          terminal: String,
          origin: String,
          list: List<PropertyGramm>,
          min: Int,
          max: Int): List<String> {
    val data = DataGrammatics(
        nonterminalSymbols = nonterminal,
        terminalSymbols = terminal,
        originSymbol = origin,
        rulesSymbol = convertListRuleToMap(list)
    )
    val gramm = GeneratedSequence(data)
    val listChain = gramm.start(min, max)
    return listChain
}

fun convertListRuleToMap(list: List<PropertyGramm>): Map<Char,List<String>> {
    val map = list.map { it.key.toCharArray().first() to convertPropsStrToList(it.value) }.toMap()
    return map
}

fun convertPropsStrToList(props: String): List<String> {
    val str = props.replace("\\s".toRegex(), "")
    val propsList = str.split("|")
    if(propsList.last() == "|") propsList + ""
    return propsList
}