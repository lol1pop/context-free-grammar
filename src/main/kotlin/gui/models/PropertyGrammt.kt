package gui.models

import gui.controllers.PropertyGrammController
import tornadofx.*

class PropertyGramm(key: String, value: String) {
    fun valueProperty() = getProperty(PropertyGramm::value)
    var value by property(value)

    fun keyProperty() = getProperty(PropertyGramm::key)
    var key by property(key)

}


class  PropertyGrammModel: ItemViewModel<PropertyGramm>() {
    val key = bind { item?.keyProperty() }
    val value = bind { item?.valueProperty() }
}

class PropertyGrammList: View() {
    val propsCtrl: PropertyGrammController by inject()
    val model: PropertyGrammModel by inject()

    override val root = tableview(propsCtrl.props) {
        column("VN", PropertyGramm::key).makeEditable()
        column("PROPS", PropertyGramm::value).makeEditable()
        columnResizePolicy = SmartResize.POLICY
        bindSelected(model)
    }
}