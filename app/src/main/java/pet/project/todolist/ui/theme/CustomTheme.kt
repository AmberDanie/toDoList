package pet.project.todolist.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

/* part 2 */

object CustomTheme {
    val colors: CustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: CustomTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val spaces: CustomSpaces
        @Composable
        @ReadOnlyComposable
        get() = LocalSpaces.current
}