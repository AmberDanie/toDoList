package pet.project.todolist.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pet.project.todolist.NavGraph
import pet.project.todolist.R
import pet.project.todolist.data.TaskImportance
import pet.project.todolist.data.TodoItem
import pet.project.todolist.ui.theme.AppTheme
import pet.project.todolist.ui.theme.CustomTheme
import pet.project.todolist.viewmodels.MainScreenViewModel
import java.time.LocalDate
import java.util.Date

/* part 2 */

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel,
    navController: NavController) {
    val msState by mainScreenViewModel.msState.collectAsState()
    val items = msState.itemsList
    val showCompleted = msState.showCompleted
    Box {
        MainScreenTitle(
            toDoItems = items,
            showOrHideTasks = {
                mainScreenViewModel.showOrHideCompletedTasks()
            },
            onCheckedChange = {
                mainScreenViewModel.checkboxClick(it)
            },
            onInfoClick = {
                mainScreenViewModel.updateCurrentItem(it)
                navController.navigate(NavGraph.Task.name)
            },
            showCompleted = showCompleted
        )
        FloatingActionButton(
            onClick = {
                navController.navigate(NavGraph.Task.name)
            },
            modifier = Modifier
                .padding(end = 16.dp, bottom = 24.dp)
                .align(Alignment.BottomEnd),
            shape = CircleShape,
            containerColor = CustomTheme.colors.blue,
            contentColor = CustomTheme.colors.white
        ) {
           Icon(Icons.Filled.Add, "Action button")
        }
    }
}

@Composable
private fun MainScreenTitle(
    toDoItems: List<TodoItem>,
    showOrHideTasks: () -> Unit,
    onCheckedChange: (TodoItem) -> Unit,
    onInfoClick: (TodoItem) -> Unit,
    showCompleted: Boolean) {
    val tasksDone = toDoItems.count { it.isMade }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.main_title),
            style = CustomTheme.typography.largeTitle,
            color = CustomTheme.colors.labelPrimary,
            modifier = Modifier.padding(start = 60.dp, top = 82.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, end = 8.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.tasks_done,
                    tasksDone, toDoItems.size
                ),
                style = CustomTheme.typography.body,
                color = CustomTheme.colors.labelSecondary
            )
            IconButton(onClick = {
                showOrHideTasks()
            }) {
                Icon(
                    painter = if (!showCompleted) painterResource(R.drawable.baseline_visibility_24)
                    else painterResource(R.drawable.baseline_visibility_off_24),
                    contentDescription = "Visibility on",
                    tint = CustomTheme.colors.blue
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = CustomTheme.colors.backSecondary,
                contentColor = CustomTheme.colors.labelSecondary
            ),
            elevation = CardDefaults.elevatedCardElevation(
                2.dp
            ),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.animateContentSize(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessHigh
                    )
                )
            ) {
                items(items = toDoItems, key = {it.id}) {
                    AnimatedVisibility(
                        visible = showCompleted || !it.isMade,
                        enter = expandVertically(animationSpec = tween(200)),
                        exit = shrinkVertically(animationSpec = tween(200))
                    ) {
                        ListItem(item = it,
                            onCheckedChange = onCheckedChange,
                            onInfoClick = onInfoClick)
                    }
                }
                item(1) {
                    Spacer(Modifier.height(64.dp))
                }
            }
        }
    }
}

@Composable
fun ListItem(
    item: TodoItem,
    onCheckedChange: (TodoItem) -> Unit,
    onInfoClick: (TodoItem) -> Unit,
    modifier: Modifier = Modifier) {
    Row {
        Checkbox(checked = item.isMade,
            onCheckedChange = {
                onCheckedChange(item)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = CustomTheme.colors.green,
                checkmarkColor = CustomTheme.colors.backSecondary,
                uncheckedColor = when (item.importance) {
                    TaskImportance.HIGH -> CustomTheme.colors.red
                    else -> CustomTheme.colors.gray
                }
            ),
            modifier = modifier
        )
        when (item.importance) {
            TaskImportance.LOW ->
                Icon(painter = painterResource(id = R.drawable.baseline_arrow_downward_24),
                    contentDescription = null,
                    tint = if (!item.isMade) CustomTheme.colors.gray else CustomTheme.colors.green,
                    modifier = modifier.padding(top = 12.dp))
            TaskImportance.HIGH ->
                Icon(painter = painterResource(id = R.drawable.baseline_priority_high_24),
                    contentDescription = null,
                    tint = if (!item.isMade) CustomTheme.colors.red else CustomTheme.colors.green,
                    modifier = modifier.padding(top = 12.dp))
            else -> {}
        }
        Column(
            modifier = Modifier.weight(0.66f)
        ) {
            Text(
                item.text,
                style = CustomTheme.typography.body,
                textDecoration = if (item.isMade) TextDecoration.LineThrough
                                             else TextDecoration.None,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.padding(top = 12.dp)
            )
            if (item.deadline != null) {
                Text(item.deadline.toString(),
                    style = CustomTheme.typography.subhead,
                    textDecoration = if (item.isMade) TextDecoration.LineThrough
                                                 else TextDecoration.None,
                    color = CustomTheme.colors.labelTertiary,
                    modifier = modifier)
            }
        }
        IconButton(onClick = {
            onInfoClick(item)
        }) {
            Icon(painter = painterResource(
                id = R.drawable.baseline_info_outline_24),
                contentDescription = null,
                modifier = modifier)
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun MainScreenLightPreview() {
    AppTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
            MainScreen(MainScreenViewModel(), rememberNavController())
        }
    }
}

@Composable
@Preview
private fun TodoItemLightPreview() {
    AppTheme(darkTheme = false) {
        val item = TodoItem(
            id = "0",
            text = "Сделать превью для итема",
            importance = TaskImportance.HIGH,
            deadline = LocalDate.parse("2024-06-29"),
            isMade = true,
            creationDate = Date()
        )
        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = CustomTheme.colors.backSecondary,
                contentColor = CustomTheme.colors.labelSecondary
            ),
            elevation = CardDefaults.elevatedCardElevation(
                2.dp
            )
        ) {
            ListItem(item = item,
                onCheckedChange = {},
                onInfoClick = {})
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun MainScreenDarkPreview() {
    AppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CustomTheme.colors.backPrimary
        ) {
            MainScreen(MainScreenViewModel(), rememberNavController())
        }
    }
}

@Composable
@Preview
private fun TodoItemDarkPreview() {
    AppTheme(darkTheme = true) {
        val item = TodoItem(
            id = "0",
            text = "Сделать превью для итема",
            importance = TaskImportance.HIGH,
            deadline = LocalDate.parse("2024-06-29"),
            isMade = true,
            creationDate = Date()
        )
        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = CustomTheme.colors.backSecondary,
                contentColor = CustomTheme.colors.labelSecondary
            ),
            elevation = CardDefaults.elevatedCardElevation(
                2.dp
            ),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            ListItem(item = item,
                onCheckedChange = {},
                onInfoClick = {})
        }
    }
}