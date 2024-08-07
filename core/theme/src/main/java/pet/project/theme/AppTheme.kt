package pet.project.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pet.project.domain.ThemeSetting

/* part 2 */

fun lightColors() = CustomColors(
    supportSeparator = SupportLightSeparator,
    supportOverlay = SupportLightOverlay,
    labelPrimary = LabelLightPrimary,
    labelSecondary = LabelLightSecondary,
    labelTertiary = LabelLightTertiary,
    labelDisable = LabelLightDisable,
    red = LightRed,
    green = LightGreen,
    yellow = LightYellow,
    gray = LightGray,
    grayLight = LightGrayLight,
    white = LightWhite,
    backPrimary = BackLightPrimary,
    backSecondary = BackLightSecondary,
    backElevated = BackLightElevated,
    isLight = true,
)

fun darkColors() = CustomColors(
    supportSeparator = SupportDarkSeparator,
    supportOverlay = SupportDarkOverlay,
    labelPrimary = LabelDarkPrimary,
    labelSecondary = LabelDarkSecondary,
    labelTertiary = LabelDarkTertiary,
    labelDisable = LabelDarkDisable,
    red = DarkRed,
    green = DarkGreen,
    yellow = DarkYellow,
    gray = DarkGray,
    grayLight = DarkGrayLight,
    white = DarkWhite,
    backPrimary = BackDarkPrimary,
    backSecondary = BackDarkSecondary,
    backElevated = BackDarkElevated,
    isLight = false,
)

@Composable
fun AppTheme(
    spaces: CustomSpaces = CustomTheme.spaces,
    typography: CustomTypography = CustomTheme.typography,
    colors: CustomColors = CustomTheme.colors,
    darkColors: CustomColors = darkColors(),
    systemColors: Boolean = isSystemInDarkTheme(),
    themeSetting: ThemeSetting = ThemeSetting.AUTO,
    content: @Composable () -> Unit,
) {
    val currentColor = when (themeSetting) {
            ThemeSetting.DARK -> darkColors
            ThemeSetting.LIGHT -> colors
            else -> when (systemColors) {
                true -> darkColors
                else -> colors
            }
        }
    val rememberedColors = remember { currentColor.copy() }.apply { updateColorsFrom(currentColor) }
    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalSpaces provides spaces,
        LocalTypography provides typography,
    ) {
        ProvideTextStyle(typography.body, content = content)
    }
}

@Preview
@Composable
private fun TextStyles() {
    AppTheme() {
        Column(
            modifier = Modifier.background(CustomTheme.colors.white)
        ) {
            Text(
                text = "Large title - 32/38",
                style = CustomTheme.typography.largeTitle
            )
            Text(
                text = "Title - 20/32",
                style = CustomTheme.typography.title
            )
            Text(
                text = "BUTTON - 14/24",
                style = CustomTheme.typography.button
            )
            Text(
                text = "Body - 16/20",
                style = CustomTheme.typography.body
            )
            Text(
                text = "Subhead - 14/20",
                style = CustomTheme.typography.subhead
            )
        }
    }
}

@Preview
@Composable
private fun LightColors() {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(lightThemeColorList) {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .background(it)
            )
        }
    }
}

@Preview
@Composable
private fun DarkColors() {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(darkThemeColorList) {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .background(it)
            )
        }
    }
}
