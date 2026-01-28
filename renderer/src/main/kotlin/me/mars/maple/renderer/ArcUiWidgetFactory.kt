package me.mars.maple.renderer

import arc.scene.Element
import arc.scene.ui.*
import me.mars.maple.renderer.elem.Box
import me.mars.maple.renderer.elem.Column
import me.mars.maple.renderer.elem.Row
import me.mars.maple.renderer.widgets.*
import me.mars.maple.schema.widget.*

class ArcUiWidgetFactory : PrimitivesWidgetFactory<Element> {
    override fun BoxP(): BoxP<Element> = BoxWidget(Box())

    override fun RowP(): RowP<Element> = RowWidget(Row())

    override fun ColumnP(): ColumnP<Element> = ColumnWidget(Column())

    override fun ScrollPaneP(): ScrollPaneP<Element> = ScrollPaneWidget()

    override fun CollapserP(): CollapserP<Element> = CollapserWidget()

    override fun ButtonP(): ButtonP<Element> = ButtonWidget()

    override fun LabelP(): LabelP<Element> = LabelWidget(Label(""))

    override fun ImageP(): ImageP<Element> = ImageWidget(Image())

    override fun ProgressBarP(): ProgressBarP<Element> =
        ProgressBarWidget(ProgressBar(0f, 1f, 0.01f, false, ProgressBar.ProgressBarStyle()))

    override fun BarP(): BarP<Element> = BarWidget()

    override fun TextButtonP(): TextButtonP<Element> = TextButtonWidget(TextButton(""))

    override fun ImageButtonP(): ImageButtonP<Element> = ImageButtonWidget(ImageButton())

    override fun CheckBoxP(): CheckBoxP<Element> = CheckBoxWidget(CheckBox(""))

    override fun TextFieldP(): TextFieldP<Element> = TextFieldWidget(TextField())

    override fun TextAreaP(): TextAreaP<Element> = TextAreaWidget(TextArea(""))


}

fun PrimitivesWidgetSystem(): PrimitivesWidgetSystem<Element> {
    return PrimitivesWidgetSystem(
        Primitives = ArcUiWidgetFactory()
    )
}