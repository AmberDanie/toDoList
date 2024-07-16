package pet.project.todolist.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pet.project.todolist.ui.MainScreen
import pet.project.todolist.ui.TaskScreen
import pet.project.todolist.ui.viewmodels.MainScreenViewModel
import pet.project.todolist.ui.viewmodels.TaskScreenViewModel

@Composable
fun TodoListNavHost(
    navController: NavHostController,
    mainScreenViewModelFactory: MainScreenViewModel.Factory,
    taskScreenViewModelFactory: TaskScreenViewModel.Factory
) {
    NavHost(
        navController = navController,
        startDestination = TodoListNavGraph.Main.name,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        composable(route = TodoListNavGraph.Main.name) { backStackEntry ->
            val mainScreenViewModel = viewModel {
                mainScreenViewModelFactory.create(backStackEntry.savedStateHandle)
            }
            val msState by mainScreenViewModel.msState.collectAsState()
            MainScreen(
                msState = msState,
                showOrHideTasks = { mainScreenViewModel.showOrHideCompletedTasks() },
                checkBoxClick = { mainScreenViewModel.changeMadeStatus(it.id) },
                moveToTaskScreen = { navController.navigate(TodoListNavGraph.Task.name + "/$it") },
                updateList = { mainScreenViewModel.retryToGetData() },
                hideSnackbar = { mainScreenViewModel.hideSnackbar() }
            )
        }
        composable(route = TodoListNavGraph.Task.name + "/{itemId}") { backStackEntry ->
            val taskScreenViewModel = viewModel {
                taskScreenViewModelFactory.create(
                    backStackEntry.savedStateHandle.apply {
                        val itemId = backStackEntry.arguments?.getString("itemId")
                        this["itemId"] = itemId
                    }
                )
            }
            TaskScreen(
                taskScreenViewModel,
                moveBack = { navController.popBackStack() }
            )
        }
    }
}
