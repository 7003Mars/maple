package me.mars.maple.schema

import app.cash.redwood.schema.Children
import app.cash.redwood.schema.Property
import app.cash.redwood.schema.Widget
import arc.graphics.Color
import arc.scene.style.Drawable
import arc.scene.ui.*
import arc.util.Scaling
import me.mars.maple.schema.api.Arrangement

/*
Order for fields defined:
1. Children
2. The primary fields
3. Event listeners
4. Other fields
5. Style
 */
// region Layout

object BoxScope

@Widget(1)
data class BoxP(
    @Children(1) val children: BoxScope.() -> Unit,
    @Property(2) val background: Drawable?
)

object RowScope

@Widget(2)
data class RowP(
    @Children(1) val children: RowScope.() -> Unit,
    @Property(1) val align: Int,
    @Property(2) val arrangement: Arrangement,
    @Property(3) val background: Drawable?,
)

object ColumnScope

@Widget(3)
data class ColumnP(
    @Children(1) val children: ColumnScope.() -> Unit,
    @Property(1) val align: Int,
    @Property(2) val arrangement: Arrangement,
    @Property(3) val background: Drawable?,
)

@Widget(4)
data class ScrollPaneP(
    @Children(1) val children: BoxScope.() -> Unit,
    @Property(1) val xScrollingDisabled: Boolean,
    @Property(2) val yScrollingDisabled: Boolean,
    @Property(3) val scrollbarsOnTop: Boolean,
    @Property(4) val style: ScrollPane.ScrollPaneStyle,
)

@Widget(5)
data class CollapserP(
    @Children(1) val children: BoxScope.() -> Unit,
    @Property(1) val collapsed: Boolean,
    @Property(2) val duration: Float,
    @Property(3) val enforceMinSize: Boolean
)

@Widget(6)
data class ButtonP(
    @Children(1) val children: BoxScope.() -> Unit,
    @Property(1) val onClick: () -> Unit,
    @Property(2) val style: Button.ButtonStyle,
)

// endregion
// region Primitive elements:

@Widget(7)
data class LabelP(
    @Property(1) val text: String,
    @Property(2) val wrap: Boolean,
    @Property(3) val ellipsis: Boolean,
    @Property(4) val labelStyle: Label.LabelStyle
)

@Widget(8)
data class ImageP(
    @Property(1) val drawable: Drawable,
    @Property(2) val align: Int,
    @Property(3) val scaling: Scaling,
)

@Widget(9)
data class ProgressBarP(
    @Property(1) val progress: Float,
    @Property(2) val min: Float,
    @Property(3) val max: Float,
    @Property(4) val stepSize: Float,
    // TODO The vertical field too
    @Property(5) val progressBarStyle: ProgressBar.ProgressBarStyle
)

@Widget(10)
data class BarP(
    @Property(1) val name: String,
    @Property(2) val color: Color,
    @Property(3) val fraction: Float
)

// endregion
// region Input

@Widget(11)
data class TextButtonP(
    @Property(1) val text: String,
    @Property(2) val onClick: () -> Unit,
    @Property(3) val disabled: Boolean,
    @Property(4) val buttonStyle: TextButton.TextButtonStyle,
)

@Widget(12)
data class ImageButtonP(
    @Property(1) val onClick: () -> Unit,
    @Property(2) val disabled: Boolean,
    @Property(3) val buttonStyle: ImageButton.ImageButtonStyle,
)

@Widget(13)
data class CheckBoxP(
    @Property(1) val text: String,
    @Property(2) val checked: Boolean,
    @Property(3) val onClick: () -> Unit,
    @Property(4) val checkboxStyle: CheckBox.CheckBoxStyle
)

@Widget(14)
data class TextFieldP(
    @Property(1) val text: String,
    @Property(2) val onChange: (String) -> Unit,
    @Property(3) val textFieldStyle: TextField.TextFieldStyle,
)

@Widget(15)
data class TextAreaP(
    @Property(1) val text: String,
    @Property(2) val onChange: (String) -> Unit,
    @Property(3) val prefRows: Int,
    @Property(4) val textAreaStyle: TextField.TextFieldStyle
)

@Widget(16)
data class SliderP(
    @Property(1) val value: Float,
    @Property(2) val min: Float,
    @Property(3) val max: Float,
    @Property(4) val stepSize: Float,
    @Property(5) val onValueChanged: (Float) -> Unit,
//    @Property(6) val vertical: Boolean, // TODO
    @Property(6) val sliderStyle: Slider.SliderStyle
)