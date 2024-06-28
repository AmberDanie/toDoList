package pet.project.todolist.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

/* part 2 */

class CustomColors(
    supportSeparator: Color,
    supportOverlay: Color,
    labelPrimary: Color,
    labelSecondary: Color,
    labelTertiary: Color,
    labelDisable: Color,
    red: Color,
    green: Color,
    blue: Color,
    gray: Color,
    grayLight: Color,
    white: Color,
    backPrimary: Color,
    backSecondary: Color,
    backElevated: Color,
    isLight: Boolean,
) {
    var supportSeparator by mutableStateOf(supportSeparator)
        private set

    var supportOverlay by mutableStateOf(supportOverlay)
        private set

    var labelPrimary by mutableStateOf(labelPrimary)
        private set

    var labelSecondary by mutableStateOf(labelSecondary)
        private set

    var labelTertiary by mutableStateOf(labelTertiary)
        private set

    var labelDisable by mutableStateOf(labelDisable)
        private set

    var red by mutableStateOf(red)
        private set

    var green by mutableStateOf(green)
        private set

    var blue by mutableStateOf(blue)
        private set

    var gray by mutableStateOf(gray)
        private set

    var grayLight by mutableStateOf(grayLight)
        private set

    var white by mutableStateOf(white)
        private set

    var backPrimary by mutableStateOf(backPrimary)
        private set

    var backSecondary by mutableStateOf(backSecondary)
        private set

    var backElevated by mutableStateOf(backElevated)
        private set

    var isLight by mutableStateOf(isLight)
        private set

    fun copy(
        supportSeparator: Color = this.supportSeparator,
        supportOverlay: Color = this.supportOverlay,
        labelPrimary: Color = this.labelPrimary,
        labelSecondary: Color = this.labelSecondary,
        labelTertiary: Color = this.labelTertiary,
        labelDisable: Color = this.labelDisable,
        red: Color = this.red,
        green: Color = this.green,
        blue: Color = this.blue,
        gray: Color = this.gray,
        grayLight: Color = this.grayLight,
        white: Color = this.white,
        backPrimary: Color = this.backPrimary,
        backSecondary: Color = this.backSecondary,
        backElevated: Color = this.backElevated,
        isLight: Boolean = this.isLight,
    ) = CustomColors(
        supportSeparator = supportSeparator,
        supportOverlay = supportOverlay,
        labelPrimary = labelPrimary,
        labelSecondary = labelSecondary,
        labelTertiary = labelTertiary,
        labelDisable = labelDisable,
        red = red,
        green = green,
        blue = blue,
        gray = gray,
        grayLight = grayLight,
        white = white,
        backPrimary = backPrimary,
        backSecondary = backSecondary,
        backElevated = backElevated,
        isLight = isLight,
    )
    fun updateColorsFrom(other: CustomColors) {
        supportSeparator = other.supportSeparator
        supportOverlay = other.supportOverlay
        labelPrimary = other.labelPrimary
        labelSecondary = other.labelSecondary
        labelTertiary = other.labelTertiary
        labelDisable = other.labelDisable
        red = other.red
        green = other.green
        blue = other.blue
        gray = other.gray
        grayLight = other.grayLight
        white = other.white
        backPrimary = other.backPrimary
        backSecondary = other.backSecondary
        backElevated = other.backElevated
        isLight = other.isLight
    }
}