package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import arc.scene.Element
import arc.scene.ui.CheckBox
import me.mars.maple.schema.widget.CheckBoxP

class CheckBoxWidget(override val value: CheckBox) : CheckBoxP<Element> {
    override var modifier: Modifier = Modifier

    override fun text(text: String) {
        value.setText(text)
    }

    override fun checked(checked: Boolean) {
        value.isChecked = checked
    }

    override fun onClick(onClick: () -> Unit) {
        value.clicked(onClick)
    }

    override fun checkboxStyle(checkboxStyle: CheckBox.CheckBoxStyle) {
        value.style = checkboxStyle
    }


}