package pet.project.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pet.project.todolist.ui.MainScreen
import pet.project.todolist.ui.TaskScreen
import pet.project.todolist.ui.theme.AppTheme
import pet.project.todolist.ui.theme.CustomTheme
import pet.project.todolist.viewmodels.MainScreenViewModel

/* part 2 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val mainScreenViewModel = MainScreenViewModel()

        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CustomTheme.colors.backPrimary
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavGraph.Main.name,
                        enterTransition = { fadeIn() },
                        exitTransition = { fadeOut() },
                        popEnterTransition = { fadeIn() },
                        popExitTransition = { fadeOut() }
                    ) {
                        composable(route = NavGraph.Main.name) {
                            MainScreen(mainScreenViewModel, navController)
                        }
                        composable(route = NavGraph.Task.name) {
                            TaskScreen(mainScreenViewModel, navController)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppDarkPreview() {
    AppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
            MainScreen(MainScreenViewModel(), rememberNavController())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppLightPreview() {
    AppTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
            MainScreen(MainScreenViewModel(), rememberNavController())
        }
    }
}