package me.mars.maple.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import app.cash.redwood.Modifier
import arc.graphics.Color
import arc.scene.style.Drawable
import arc.scene.ui.*
import arc.util.Align
import arc.util.Scaling
import me.mars.maple.schema.api.Arrangement
import me.mars.maple.schema.api.SliderRange
import me.mars.maple.schema.compose.*
import mindustry.ui.Styles

@Composable
fun Box(background: Drawable? = null, modifier: Modifier = Modifier, children: @Composable BoxScope.() -> Unit) {
    BoxP(children, background, modifier)
}

@Composable
fun Row(align: Int = Align.center, arrangement: Arrangement = Arrangement.Start, background: Drawable? = null, modifier: Modifier = Modifier, children: @Composable RowScope.() -> Unit) {
    RowP(children, align, arrangement, background, modifier)
}

@Composable
fun Column(align: Int = Align.center, arrangement: Arrangement = Arrangement.Start, background: Drawable? = null, modifier: Modifier = Modifier, children: @Composable ColumnScope.() -> Unit) {
    ColumnP(children, align, arrangement, background, modifier)
}

@Composable
fun ScrollPane(xScrollingDisabled: Boolean = true, yScrollingDisabled: Boolean = false, scrollbarsOnTop: Boolean = false, style: ScrollPane.ScrollPaneStyle = Styles.defaultPane, modifier: Modifier = Modifier, children: @Composable BoxScope.() -> Unit) {
    ScrollPaneP(children, xScrollingDisabled, yScrollingDisabled, scrollbarsOnTop, style, modifier)
}

@Composable
fun Collapser(collapsed: Boolean, duration: Float = 0.4f, enforceMinSize: Boolean = true/*TODO It seems setting this to false breaks layout?*/, modifier: Modifier = Modifier, children: @Composable BoxScope.() -> Unit) {
    CollapserP(children, collapsed, duration, enforceMinSize, modifier)
}

@Composable
fun Button(onClick: () -> Unit, style: Button.ButtonStyle = Styles.defaultb, modifier: Modifier = Modifier, children: @Composable BoxScope.() -> Unit) {
    ButtonP(children, onClick, style, modifier)
}

@Composable
fun Label(text: String, labelStyle: Label.LabelStyle = Styles.defaultLabel, wrap: Boolean = false, ellipsis: Boolean = false, modifier: Modifier = Modifier) {
    LabelP(text, wrap, ellipsis, labelStyle, modifier)
}

@Composable
fun Image(drawable: Drawable, align: Int = Align.center, scaling: Scaling = Scaling.fit, modifier: Modifier = Modifier) {
    ImageP(drawable, align, scaling, modifier)
}

// TODO Mindustry Doesn't actually use ProgressBar and instead uses its own Bar element.
//@Composable
//fun ProgressBar(progress: Float, min: Float = 0f, max: Float = 1f, stepSize: Float = 0.01f, progressBarStyle: ProgressBar.ProgressBarStyle,  modifier: Modifier = Modifier) {
//    ProgressBarP(progress, min, max, stepSize, progressBarStyle, modifier)
//}

@Composable
fun Bar(name: String, color: Color, fraction: Float, modifier: Modifier = Modifier) {
    BarP(name, color, fraction, modifier)
}

@Composable
fun TextButton(text: String, onClick: () -> Unit, buttonStyle: TextButton.TextButtonStyle = Styles.defaultt, disabled: Boolean = false, modifier: Modifier = Modifier) {
    TextButtonP(text, onClick, disabled, buttonStyle, modifier)
}

@Composable
fun ImageButton(drawable: Drawable, onClick: () -> Unit, align: Int = Align.center, scaling: Scaling = Scaling.fit, style: ImageButton.ImageButtonStyle = Styles.defaulti, disabled: Boolean = false, modifier: Modifier = Modifier) {
    val style = remember(drawable, style) { ImageButton.ImageButtonStyle(style).apply { imageUp = drawable } }
    ImageButtonP(onClick, disabled, align, scaling, style, modifier)
}

@Composable
fun CheckBox(text: String, checked: Boolean, onClick: () -> Unit, checkboxStyle: CheckBox.CheckBoxStyle = Styles.defaultCheck, modifier: Modifier = Modifier) {
    CheckBoxP(text, checked, onClick, checkboxStyle, modifier)
}

@Composable
fun TextField(text: String, onChange: (String) -> Unit, textFieldStyle: TextField.TextFieldStyle = Styles.defaultField, modifier: Modifier = Modifier) {
    TextFieldP(text, onChange, textFieldStyle, modifier)
}

@Composable
fun TextArea(text: String, onChange: (String) -> Unit, prefRows: Int = 3, textAreaStyle: TextField.TextFieldStyle = Styles.defaultField, modifier: Modifier = Modifier) {
    TextAreaP(text, onChange, prefRows, textAreaStyle, modifier)
}

@Composable
fun Slider(value: Float, range: SliderRange = SliderRange(), stepSize: Float = 0.01f, onValueChanged: (Float) -> Unit, sliderStyle: Slider.SliderStyle = Styles.defaultSlider, modifier: Modifier = Modifier
) {
    SliderP(value, range, stepSize, onValueChanged, sliderStyle, modifier)
}

@Composable
fun Slider(value: Float, min: Float, max: Float, stepSize: Float = 0.01f, onValueChanged: (Float) -> Unit, sliderStyle: Slider.SliderStyle = Styles.defaultSlider, modifier: Modifier = Modifier
) {
    SliderP(value, SliderRange(min, max), stepSize, onValueChanged, sliderStyle, modifier)
}