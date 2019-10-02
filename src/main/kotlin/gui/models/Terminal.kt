package gui.models

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class Terminal {
    val nonterminalProperty = SimpleStringProperty()
    val terminalProperty = SimpleStringProperty()
}

class TerminalModel(terminal: Terminal) : ViewModel() {
    val nonterminal = bind {terminal.nonterminalProperty}
    val terminal = bind {terminal.terminalProperty}
}