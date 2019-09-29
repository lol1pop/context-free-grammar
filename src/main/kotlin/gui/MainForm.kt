package gui

import tornadofx.*

class MainForm: View() {
    override val root = form {
        button("Press me")
        label("Waiting")
    }

}
